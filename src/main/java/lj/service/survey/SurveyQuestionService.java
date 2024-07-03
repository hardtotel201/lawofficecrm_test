package lj.service.survey;

import java.util.List;

import lj.model.survey.QuestionInfo;
import lj.model.survey.SurveyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;

import lj.mapper.survey.SurveyQuestionMapper;
import lj.model.survey.SurveyQuestion;
import lj.service.base.BaseService;
import lj.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * 调研问题服务
 * @author samlvThinkpad
 *
 */
@Transactional
@Service
public class SurveyQuestionService extends BaseService {

    @Autowired
	private SurveyQuestionMapper surveyQuestionDao=null;
	@Autowired
	private QuestionInfoService questionInfoService;
	@Autowired
	private SurveyInfoService surveyInfoService;
	public SurveyQuestion[] findAll()
    {
    	SurveyQuestion[] objs=surveyQuestionDao.findAll();
    	return objs;
    }
	

	public List<SurveyQuestion> findAllByQuestionId(Long questionId){
		List<SurveyQuestion> objs=this.surveyQuestionDao.findAllByQuestionId(questionId);
		return objs;
	}
    
    public List<SurveyQuestion> findAllByRequestObj(SurveyQuestion reqModel)
    {
    	List<SurveyQuestion> objs=surveyQuestionDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public SurveyQuestion find(Long id)
	{
		SurveyQuestion obj=surveyQuestionDao.findById(id);
		return obj;
	}
    

    public Page<SurveyQuestion> findAllPagedSurveyQuestion(SurveyQuestion reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("surveyQuestionId");
    		reqModel.setSortType("asc");
    	}
		Page<SurveyQuestion> page=surveyQuestionDao.findAllPaged(reqModel);
		return page;
	}
	
    /**
     * 新增调研问题
     * @param obj
     * @return
     */
	public String insertSurveyQuestion(SurveyQuestion obj)
	{
		List<SurveyQuestion> objs=findAllBySurveyId(obj.getSurveyId());
		for(SurveyQuestion temp:objs)
			if(temp.getQuestionId().equals(obj.getQuestionId()))
				return "调研问题不能重复";
		int ret=this.surveyQuestionDao.insert(obj);
		if(ret>0)
			return "";
		else
			return "新增失败!";
	}
	
	public String updateSurveyQuestion(SurveyQuestion obj)
	{
		List<SurveyQuestion> objs=findAllBySurveyId(obj.getSurveyId());
		for(SurveyQuestion temp:objs)
			if(temp.getSurveyQuestionId().equals(obj.getSurveyQuestionId())==false && 
			   temp.getQuestionId().equals(obj.getQuestionId()))
				return "调研问题不能重复";
        int ret=surveyQuestionDao.update(obj);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}
	
	public String deleteSurveyQuestion(Long id)
	{
		int ret=surveyQuestionDao.deleteById(id);
		if(ret>0)
			return "";
		else
			return "删除失败!";
	}

	public String deleteBySurveyId(Long id)
	{
		int ret=surveyQuestionDao.deleteBySurveyId(id);
		if(ret>0)
			return "";
		else
			return "删除失败!";
	}

	public String deleteByQuestionId(Long id)
	{
		int ret=surveyQuestionDao.deleteByQuestionId(id);
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long surveyQuestionId) {
		SurveyQuestion[] oldObjs = this.surveyQuestionDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (SurveyQuestion obj : oldObjs)
			if (obj.getSurveyQuestionId().equals(surveyQuestionId) == true)
				return false;
		return true;
	}

	public List<SurveyQuestion> findAllBySurveyId(Long surveyId) {
		return this.surveyQuestionDao.findAllBySurveyId(surveyId);
	}
	
	
	
}