package lj.service.lawyer;

import java.util.Date;
import java.util.List;

import lj.model.user.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import lj.mapper.lawyer.LawyerInfoMapper;
import lj.model.lawyer.LawyerInfo;
import lj.service.base.BaseService;
import lj.service.base.BaseServiceConst;
import lj.service.user.UserInfoServiceConst;
import lj.util.StringUtils;

@Service
public class LawyerInfoService extends BaseService {
	
	public final static String LAWYER_STATE_NORMAL="正常";
	public final static String LAWYER_STATE_WRITEOFF="注销";
	

    @Autowired
	private LawyerInfoMapper lawyerInfoDao=null;
    
    /**
     * 返回所有律师状态集合
     * @return
     */
    public static String[] getLawyerStates() {
    	return new String[] {LAWYER_STATE_NORMAL,LAWYER_STATE_WRITEOFF};
    }
	
	public LawyerInfo[] findAll()
    {
    	LawyerInfo[] objs=lawyerInfoDao.findAll();
    	return objs;
    }
    
    public List<LawyerInfo> findAllByRequestObj(LawyerInfo reqModel)
    {
    	List<LawyerInfo> objs=lawyerInfoDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public LawyerInfo find(Long id)
	{
		LawyerInfo obj=lawyerInfoDao.findById(id);
		return obj;
	}
    

    public Page<LawyerInfo> findAllPagedLawyerInfo(LawyerInfo reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("lawyerId");
    		reqModel.setSortType("asc");
    	}
		Page<LawyerInfo> page=lawyerInfoDao.findAllPaged(reqModel);
		return page;
	}
	
    public String insertLawyerInfo(LawyerInfo newLawyerInfo)
	{
		Date nowTime=this.lawyerInfoDao.getNowTime();
		newLawyerInfo.setOpTime(nowTime);
		newLawyerInfo.setRegisterTime(nowTime);
		newLawyerInfo.setLawyerState(LawyerInfoServiceConst.LAWYER_STATE_NORMAL);
		//新增用户信息
		int rows = this.lawyerInfoDao.insert(newLawyerInfo);
		if (rows<=0)
			return BaseServiceConst.MSG_INSERT_FAIL;
		return StringUtils.STR_EMPTY;
//		long ret=lawyerInfoDao.insert(obj);
//		if(ret>0)
//		   return obj.getLawyerId();
//		else
//		   return -1l;
		
	}
	
	public Long insertLawyer(LawyerInfo obj)
	{
		Date nowTime=this.lawyerInfoDao.getNowTime();
		obj.setOpTime(nowTime);
		obj.setRegisterTime(nowTime);
		obj.setLawyerState(LawyerInfoServiceConst.LAWYER_STATE_NORMAL);
	
		long ret=lawyerInfoDao.insert(obj);
		if(ret>0)
		   return obj.getLawyerId();
		else
		   return -1l;
		
	}
	
	public String updateLawyerInfo(LawyerInfo obj)
	{
		int ret=lawyerInfoDao.update(obj);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}
	
	public String deleteLawyerInfo(Long id)
	{
		int ret=lawyerInfoDao.deleteById(id);
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long lawyerId) {
		LawyerInfo[] oldObjs = this.lawyerInfoDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (LawyerInfo obj : oldObjs)
			if (obj.getLawyerId().equals(lawyerId) == true)
				return false;
		return true;
	}

	public LawyerInfo[] findAllLawyers() {
		LawyerInfo[] objs = lawyerInfoDao.findAllLawyers();
		return objs;
	}
}