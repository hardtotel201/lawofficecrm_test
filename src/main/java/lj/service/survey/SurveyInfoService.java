package lj.service.survey;

import java.util.Date;
import java.util.List;

import lj.model.lawcase.CaseInfo;
import lj.service.base.BaseServiceConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;

import lj.mapper.survey.SurveyInfoMapper;
import lj.model.survey.SurveyInfo;
import lj.service.base.BaseService;
import lj.util.StringUtils;

@Service
public class SurveyInfoService extends BaseService {

    @Autowired
	private SurveyInfoMapper surveyInfoDao=null;
	
	public SurveyInfo[] findAll()
    {
    	SurveyInfo[] objs=surveyInfoDao.findAll();
    	return objs;
    }




    public List<SurveyInfo> findAllByRequestObj(SurveyInfo reqModel)
    {
    	List<SurveyInfo> objs=surveyInfoDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public SurveyInfo find(Long id)
	{
		SurveyInfo obj=surveyInfoDao.findById(id);
		return obj;
	}
    

    public Page<SurveyInfo> findAllPagedSurveyInfo(SurveyInfo reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("surveyId");
    		reqModel.setSortType("asc");//升序排序
    	}
		Page<SurveyInfo> page=surveyInfoDao.findAllPaged(reqModel);
		return page;
	}
	
	/*public Long insertSurveyInfo(SurveyInfo obj)
	{
		long ret=surveyInfoDao.insert(obj);
		if(ret>0)
		   return obj.getSurveyId();
		else
		   return -1l;
	}
	
	public String updateSurveyInfo(SurveyInfo obj)
	{
		int ret=surveyInfoDao.update(obj);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}*/

	public String insertSurveyInfo(SurveyInfo newSurveyInfo)
	{

		Date nowTime=this.surveyInfoDao.getNowTime();
		newSurveyInfo.setOpTime(nowTime);


		int rows = this.surveyInfoDao.insert(newSurveyInfo);
		if (rows<=0)
			return BaseServiceConst.MSG_INSERT_FAIL;
		return StringUtils.STR_EMPTY;
	}

	public String updateSurveyInfo(SurveyInfo obj)
	{
		Date nowTime=this.surveyInfoDao.getNowTime();
		obj.setOpTime(nowTime);
		if (surveyInfoDao.update(obj) <= 0)
			return "更新案件信息失败!";
		int ret=surveyInfoDao.update(obj);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}
	
	public String deleteSurveyInfo(Long id)
	{
		int ret=surveyInfoDao.deleteById(id);
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long surveyId) {
		SurveyInfo[] oldObjs = this.surveyInfoDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (SurveyInfo obj : oldObjs)
			if (obj.getSurveyId().equals(surveyId) == true)
				return false;
		return true;
	}
	
}