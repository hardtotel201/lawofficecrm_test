package lj.service.survey;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;

import lj.mapper.survey.SurveyQuestionnaireMapper;
import lj.model.survey.SurveyQuestion;
import lj.model.survey.SurveyQuestionnaire;
import lj.service.base.BaseService;
import lj.util.StringUtils;

@Service
public class SurveyQuestionnaireService extends BaseService {
	
	public final static String QUESTIONNAIRESTATE_NEW     ="创建";
	public final static String QUESTIONNAIRESTATE_ANSWERED="已回答";

    @Autowired
	private SurveyQuestionnaireMapper surveyQuestionnaireDao=null;
	
	public SurveyQuestionnaire[] findAll()
    {
    	SurveyQuestionnaire[] objs=surveyQuestionnaireDao.findAll();
    	return objs;
    }
    
	
	
	
    public List<SurveyQuestionnaire> findAllByRequestObj(SurveyQuestionnaire reqModel)
    {
    	List<SurveyQuestionnaire> objs=surveyQuestionnaireDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public SurveyQuestionnaire find(Long id)
	{
		SurveyQuestionnaire obj=surveyQuestionnaireDao.findById(id);
		return obj;
	}
    

    public Page<SurveyQuestionnaire> findAllPagedSurveyQuestionnaire(SurveyQuestionnaire reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("surveyQuestionnaireId");
    		reqModel.setSortType("asc");
    	}
		Page<SurveyQuestionnaire> page=surveyQuestionnaireDao.findAllPaged(reqModel);
		return page;
	}
	
	public Long insertSurveyQuestionnaire(SurveyQuestionnaire obj)
	{
		long ret=surveyQuestionnaireDao.insert(obj);
		if(ret>0)
		   return obj.getSurveyQuestionnaireId();
		else
		   return -1l;
	}
	
	public String updateSurveyQuestionnaire(SurveyQuestionnaire obj)
	{
		int ret=surveyQuestionnaireDao.update(obj);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}
	
	public String deleteSurveyQuestionnaire(Long id)
	{
		int ret=surveyQuestionnaireDao.deleteById(id);
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long surveyQuestionnaireId) {
		SurveyQuestionnaire[] oldObjs = this.surveyQuestionnaireDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (SurveyQuestionnaire obj : oldObjs)
			if (obj.getSurveyQuestionnaireId().equals(surveyQuestionnaireId) == true)
				return false;
		return true;
	}
	
}