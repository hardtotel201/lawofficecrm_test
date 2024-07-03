package lj.service.survey;

import java.util.List;

import lj.model.survey.QuestionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;

import lj.mapper.survey.QuestionItemMapper;
import lj.model.survey.QuestionItem;
import lj.service.base.BaseService;
import lj.util.StringUtils;

@Service
public class QuestionItemService extends BaseService {

    @Autowired
	private QuestionItemMapper questionItemDao=null;
	@Autowired
	private QuestionInfoService questionInfoService;
	public QuestionItem[] findAll()
    {
    	QuestionItem[] objs=questionItemDao.findAll();
    	return objs;
    }
    
    public List<QuestionItem> findAllByRequestObj(QuestionItem reqModel)
    {
    	List<QuestionItem> objs=questionItemDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public QuestionItem find(Long id)
	{
		QuestionItem obj=questionItemDao.findById(id);
		return obj;
	}
    

    public Page<QuestionItem> findAllPagedQuestionItem(QuestionItem reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("questionItemId");
    		reqModel.setSortType("asc");
    	}
		Page<QuestionItem> page=questionItemDao.findAllPaged(reqModel);
		return page;
	}
	
	public Long insertQuestionItem(QuestionItem obj)
	{
		long ret=questionItemDao.insert(obj);
		if(ret>0)
			return obj.getQuestionItemId();
		else
			return -1l;
	}
	
	public String updateQuestionItem(QuestionItem obj)
	{
		int ret=questionItemDao.update(obj);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}
	
	public String deleteQuestionItem(Long id)
	{
		int ret=questionItemDao.deleteById(id);
		if(ret>0)
			return "";
		else
			return "删除失败!";
	}
	public String deleteByQuestionId(Long id)
	{
		int ret=questionItemDao.deleteByQuestionId(id);
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long questionItemId) {
		QuestionItem[] oldObjs = this.questionItemDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (QuestionItem obj : oldObjs)
			if (obj.getQuestionItemId().equals(questionItemId) == true)
				return false;
		return true;
	}

	public List<QuestionItem> findByQuestionId(Long questionId) {
		return questionItemDao.findByQuestionId(questionId);
	}


}