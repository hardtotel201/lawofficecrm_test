package lj.service.sys;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import lj.global.AppVar;
import lj.mapper.sys.ParamInfoMapper;
import lj.model.sys.ParamInfo;
import lj.service.base.BaseService;
import lj.util.LogUtils;
import lj.util.StringUtils;

/**
 * 系统参数服务
 * @param id
 * @return
 */
@Service
public class ParamInfoService extends BaseService {
	private final static String PARAMNAME_SYSTEM_NAME ="系统名称";
	private final static String PARAMNAME_COMPANY_NAME="公司名称";
	private final static String PARAMNAME_DEFAULT_PASSWORD="默认密码";
	private final static String PARAMNAME_SURVERY_ID="最新调研标识";
	public final static String PARAMNAME_SURVEY_LOCATIONS="调研地址";
	public final static String PARAMNAME_SERVICE_ITEM="成交客户服务项";
	public final static String PARAMNAME_CONTRACT_OPERATOR="客户跟进供应商";
	public final static String PARAMNAME_FOLLOW_SALES_PERSON="客户跟进商务";


    @Autowired
	private ParamInfoMapper paramInfoDao=null;
	
	public ParamInfo[] findAll()
    {
    	ParamInfo[] objs=paramInfoDao.findAll();
    	return objs;
    }
    
    public ParamInfo find(Long id)
	{
		ParamInfo obj=paramInfoDao.findById(id);
		return obj;
	}
    
    public ParamInfo findByParamName(String paramName) {
    	List<ParamInfo> list=this.paramInfoDao.findAllByParamName(paramName);
    	if(list==null || list.size()<=0)
    		return null;
    	return list.get(0);
    }
    

    public Page<ParamInfo> findAllPagedParamInfo(ParamInfo reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("paramId");
    		reqModel.setSortType("asc");
    	}
		Page<ParamInfo> page=paramInfoDao.findAllPaged(reqModel);
		return page;
	}
	
	public Long insertParamInfo(ParamInfo obj)
	{
		long ret=paramInfoDao.insert(obj);
		if(ret>0)
		   return obj.getParamId();
		else
		   return -1l;
	}
	
	public String updateParamInfo(ParamInfo obj)
	{
		String paramName=obj.getParamName();
		if(StringUtils.isNullOrEmpty(paramName)==true)
			return "参数名称不能为空";
		int ret=paramInfoDao.update(obj);
		if(ret<=0)
			return "修改失败!";
		//2.1-早餐时间
		if(paramName.equals(PARAMNAME_SYSTEM_NAME)==true)
			AppVar.systemName=this.findStringParam(PARAMNAME_SYSTEM_NAME);
		if(paramName.equals(PARAMNAME_COMPANY_NAME)==true)
			AppVar.companyName=this.findStringParam(PARAMNAME_COMPANY_NAME);
		if(paramName.equals(PARAMNAME_DEFAULT_PASSWORD)==true)
			AppVar.defaultPassword=this.findStringParam(PARAMNAME_DEFAULT_PASSWORD);
		if(paramName.equals(PARAMNAME_SURVERY_ID)==true)
			AppVar.latestSurveryId=this.findLongParam(PARAMNAME_SURVERY_ID);
		return "";
	}
	
	public String deleteParamInfo(Long id)
	{
		int ret=paramInfoDao.deleteById(id);
		if(ret>0)
			return "";
		else
			return "删除失败!";
	}
	
	/**
	 * 某个字段值是否重复
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @param id
	 * @return
	 */
	public boolean isRepeat(String fieldName, Object fieldValue, Long paramId) {
		ParamInfo[] oldObjs = this.paramInfoDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (ParamInfo obj : oldObjs)
			if (obj.getParamId().equals(paramId) == true)
				return false;
		return true;
	}
	
	/**
	 * 加载系统参数
	 * @return
	 */
    public int reloadParamInfos() {
    	AppVar.systemName=this.findStringParam(PARAMNAME_SYSTEM_NAME);
		if(AppVar.systemName==null)
			return -1;
		AppVar.companyName=this.findStringParam(PARAMNAME_COMPANY_NAME);
		if(AppVar.companyName==null)
			return -1;
		AppVar.defaultPassword=this.findStringParam(PARAMNAME_DEFAULT_PASSWORD);
		if(AppVar.defaultPassword==null)
			return -1;
		AppVar.latestSurveryId=this.findLongParam(PARAMNAME_SURVERY_ID);
		if(AppVar.latestSurveryId<=0)
			return -1;
		return 1;
    }
    
    /**
     * 读取时间参数
     * @param paramName
     * @return
     */
    private Date findTimeParam(String paramName) {
    	ParamInfo param=this.findByParamName(paramName);
		if(param==null) {
			LogUtils.logErrorAndConsole("系统参数'"+paramName+"'加载失败");
			return null;
		}
		Date paramValue=StringUtils.strToDate(param.getParamValue(),StringUtils.TIME_FORMAT);
		if(paramValue==null) {
			LogUtils.logErrorAndConsole("系统参数'"+paramName+"'格式错误，应该为HH:mm:ss");
			return null;
		}
		LogUtils.logInfoAndConsole("系统参数'"+paramName+"'读取成功:"+StringUtils.dateToStr(paramValue, StringUtils.TIME_FORMAT));
		return paramValue;
    }
    
    /**
     * 读取Long参数
     * @param paramName
     * @return
     */
    private Long findLongParam(String paramName) {
    	ParamInfo param=this.findByParamName(paramName);
		if(param==null) {
			LogUtils.logErrorAndConsole("系统参数'"+paramName+"'加载失败");
			return null;
		}
		try {
			long d=Long.parseLong(param.getParamValue());
			LogUtils.logInfoAndConsole("系统参数'"+paramName+"'读取成功:"+d);
			return d;
		}catch(Exception e) {
			LogUtils.logErrorAndConsole("系统参数'"+paramName+"'格式错误，应该为double");
			return null;
		}
    }
    
    /**
     * 读取Double参数
     * @param paramName
     * @return
     */
    private Double findDoubleParam(String paramName) {
    	ParamInfo param=this.findByParamName(paramName);
		if(param==null) {
			LogUtils.logErrorAndConsole("系统参数'"+paramName+"'加载失败");
			return null;
		}
		try {
			double d=Double.parseDouble(param.getParamValue());
			LogUtils.logInfoAndConsole("系统参数'"+paramName+"'读取成功:"+d);
			return d;
		}catch(Exception e) {
			LogUtils.logErrorAndConsole("系统参数'"+paramName+"'格式错误，应该为double");
			return null;
		}
    }
    
    /**
     * 查询字符串类型
     * @param paramName
     * @return
     */
    private String findStringParam(String paramName) {
    	ParamInfo param=this.findByParamName(paramName);
		if(param==null) {
			LogUtils.logErrorAndConsole("系统参数'"+paramName+"'加载失败");
			return null;
		}
		return param.getParamValue();
    }
	
}