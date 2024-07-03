package lj.service.lawcase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lj.model.lawcase.CaseInfoDownload;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import lj.mapper.lawcase.CaseInfoMapper;
import lj.mapper.survey.SurveyQuestionnaireMapper;
import lj.model.lawcase.CaseInfo;
import lj.model.survey.SurveyQuestionnaire;
import lj.service.base.BaseService;
import lj.service.base.BaseServiceConst;
import lj.service.survey.SurveyQuestionnaireService;
import lj.util.StringUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * 案件信息服务
 * @author samlvThinkpad
 *
 */
@Service
public class CaseInfoService extends BaseService {

/*	@Value("${caseInfo.download.save-address}")
	private String downloadAddress;

	@Value("${caseInfo.download.save-name}")
	private String downloadName;*/



	public final static String CASE_STATE_NEW="新建";
	public final static String CASE_STATE_ENSURE="确定";
	
	public final static String CASE_TYPE_LAWSUIT="诉讼案件";
	public final static String CASE_TYPE_NONELAWSUIT="非诉讼案件";
	
	/**
	 * 返回案件状态数组
	 * @return
	 */
	public static String[] getCaseStates() {
		return new String[] {CASE_STATE_NEW,CASE_STATE_ENSURE};
	}
	
	public static String[] getCaseTypes() {
		return new String[] {CASE_TYPE_LAWSUIT,CASE_TYPE_NONELAWSUIT};
	}
	

    @Autowired
	private CaseInfoMapper caseInfoDao=null;

	@Autowired
	private SurveyQuestionnaireMapper surveyQuestionnaireMapper;

	public CaseInfo[] findAll()
    {
    	CaseInfo[] objs=caseInfoDao.findAll();
    	return objs;
    }
    
    public List<CaseInfo> findAllByRequestObj(CaseInfo reqModel)
    {
    	List<CaseInfo> objs=caseInfoDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public CaseInfo find(Long id)
	{
		CaseInfo obj=caseInfoDao.findById(id);
		return obj;
	}
    

    public Page<CaseInfo> findAllPagedCaseInfo(CaseInfo reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("caseId");
    		reqModel.setSortType("asc");
    	}
		Page<CaseInfo> page=caseInfoDao.findAllPaged(reqModel);
		return page;
	}

    /**
     * 新增案件信息
     * @param newCaseInfo
     * @return
     */
	public String insertCaseInfo(CaseInfo newCaseInfo)
	{
		//1-判断是否重复
		if(this.isRepeat("caseCode", newCaseInfo.getCaseCode(), null))
			return "案件编号不能重复";
		Date nowTime=this.caseInfoDao.getNowTime();
		newCaseInfo.setOpTime(nowTime);
		int rows = this.caseInfoDao.insert(newCaseInfo);
		if (rows<=0)
			return BaseServiceConst.MSG_INSERT_FAIL;
		return StringUtils.STR_EMPTY;
	}

	/**
	 * 更新案件信息
	 * @param obj
	 * @return
	 */
	public String updateCaseInfo(CaseInfo obj)
	{
		//1-判断是否重复
		if(this.isRepeat("caseCode", obj.getCaseCode(), obj.getCaseId()))
			return "案件编号不能重复";
		//2-设置后端属性
		Date nowTime=this.caseInfoDao.getNowTime();
		obj.setOpTime(nowTime);
		//3-更新
		if (caseInfoDao.update(obj) <= 0)
			return "更新案件信息失败!";
		int ret=caseInfoDao.update(obj);

		
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}

	public String deleteCaseInfo(Long id)
	{
		int ret=caseInfoDao.deleteById(id);
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long caseId) {
		CaseInfo[] oldObjs = this.caseInfoDao.findAllByField(fieldName, fieldValue);
		System.out.println("CaseInfoService.isRepeat() find:"+lj.util.JsonUtils.objectToJson(oldObjs));
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (CaseInfo obj : oldObjs)
			if (obj.getCaseId().equals(caseId) ==false)
				return true;
		return false;
	}

	/*public String caseInfoDownload(List<CaseInfo> caseInfoLists){
		List<CaseInfoDownload> downloadList = new ArrayList<>();
		//1.前端提交caseinfo信息,根据id查询关联取得需要下载的对象信息
		for(CaseInfo ci : caseInfoLists){
			CaseInfo caseInfo=this.find(ci.getCaseId());
			CaseInfoDownload caseInfoDownload = new CaseInfoDownload();
			BeanUtils.copyProperties(caseInfo,caseInfoDownload);
			downloadList.add(caseInfoDownload);
		}
		//2.下载caseinfo表格信息
		String fileName =  downloadAddress + downloadName +".xlsx";
		EasyExcel.write(fileName, CaseInfoDownload.class)
				.sheet("收案登记")
				.doWrite(() -> {
					// 分页查询数据
					return downloadList;
				});
		return "200";
	}
*/
	
}