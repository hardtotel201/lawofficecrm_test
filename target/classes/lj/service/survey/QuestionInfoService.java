package lj.service.survey;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import lj.mapper.survey.QuestionInfoMapper;
import lj.mapper.survey.QuestionItemMapper;
import lj.mapper.survey.SurveyQuestionMapper;
import lj.model.survey.QuestionInfo;
import lj.model.survey.SurveyQuestion;
import lj.service.base.BaseService;
import lj.service.base.BaseServiceConst;
import lj.util.StringUtils;

/**
 * 问题信息服务
 * @author samlvThinkpad
 *
 */
@Service
public class QuestionInfoService extends BaseService {

    @Autowired
	private QuestionInfoMapper questionInfoDao=null;

	@Autowired
	private QuestionItemMapper questionItemDao=null;
	
	@Autowired
	private SurveyQuestionMapper surveyQuestionDao=null;
	
	public QuestionInfo[] findAll()
    {
    	QuestionInfo[] objs=questionInfoDao.findAll();
    	return objs;
    }

    
    public List<QuestionInfo> findAllByRequestObj(QuestionInfo reqModel)
    {
    	List<QuestionInfo> objs=questionInfoDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public QuestionInfo find(Long id)
	{
		QuestionInfo obj=questionInfoDao.findById(id);
		return obj;
	}
    

    public Page<QuestionInfo> findAllPagedQuestionInfo(QuestionInfo reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("questionId");
    		reqModel.setSortType("asc");
    	}
		Page<QuestionInfo> page=questionInfoDao.findAllPaged(reqModel);
		return page;
	}
	
    /**
     * 新增
     * @param newQuestionInfo
     * @return
     */
	public String insertQuestionInfo(QuestionInfo newQuestionInfo)
	{
		//1-问题标题是否重复
		if(this.isRepeat("questionTitle", newQuestionInfo.getQuestionTitle(), -1l)) {
			return "问题标题已经存在!";
		}
		//2-新增
		Date nowTime=this.questionInfoDao.getNowTime();
		newQuestionInfo.setOpTime(nowTime);
		int rows = this.questionInfoDao.insert(newQuestionInfo);


		//int rows2 = this.questionItemDao.insert(newQuestionIetm);
		if (rows<=0)
			return BaseServiceConst.MSG_INSERT_FAIL;
		return StringUtils.STR_EMPTY;
		/*long ret=questionInfoDao.insert(obj);
		if(ret>0)
		   return obj.getQuestionId();
		else
		   return -1l;*/
	}

	public QuestionInfo findByQuestionTitle(String questionTitle){
		QuestionInfo oneByQuestionName = this.questionInfoDao.findOneByQuestionTitle(questionTitle);
		return oneByQuestionName;
	}
	
	public String updateQuestionInfo(QuestionInfo obj)
	{
		//1-问题标题是否重复
		if(this.isRepeat("questionTitle", obj.getQuestionTitle(), obj.getQuestionId())) {
			return "问题标题已经存在!";
		}
		Date nowTime=this.questionInfoDao.getNowTime();
		obj.setOpTime(nowTime);
		int ret=questionInfoDao.update(obj);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}
	
	public String deleteQuestionInfo(Long id)
	{
		boolean isUse=this.isInUse(id);
		if(isUse==true)
			return "问题已经使用，不允许使用!";
		int ret=questionInfoDao.deleteById(id);
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long questionId) {
		QuestionInfo[] oldObjs = this.questionInfoDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (QuestionInfo obj : oldObjs)
			if (obj.getQuestionId().equals(questionId) == true)
				return false;
		return true;
	}
	
	/**
	 * 问题是否已经使用
	 * @param questionId
	 * @return
	 */
	public boolean isInUse(long questionId) {
		List<SurveyQuestion> objs=this.surveyQuestionDao.findAllByQuestionId(questionId);
		return objs!=null && objs.size()>0;
	}
	
}