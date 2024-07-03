package lj.service.lawcase;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;

import lj.mapper.lawcase.CaseEvaluateMapper;
import lj.model.lawcase.CaseEvaluate;
import lj.service.base.BaseService;
import lj.util.StringUtils;

@Service
public class CaseEvaluateService extends BaseService {
	
	public final static String EVALUATE_CONTENT_BEST="很满意";
	public final static String EVALUATE_CONTENT_GOOD="满意";
	public final static String EVALUATE_CONTENT_BAD="不满意";
	
    public static String[] getEvaluateContents() {
    	return new String[] {EVALUATE_CONTENT_BEST,EVALUATE_CONTENT_GOOD,EVALUATE_CONTENT_BAD};
    }
	
    @Autowired
	private CaseEvaluateMapper caseEvaluateDao=null;
	
	public CaseEvaluate[] findAll()
    {
    	CaseEvaluate[] objs=caseEvaluateDao.findAll();
    	return objs;
    }
    
    public List<CaseEvaluate> findAllByRequestObj(CaseEvaluate reqModel)
    {
    	List<CaseEvaluate> objs=caseEvaluateDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public CaseEvaluate find(Long id)
	{
		CaseEvaluate obj=caseEvaluateDao.findById(id);
		return obj;
	}
    

    public Page<CaseEvaluate> findAllPagedCaseEvaluate(CaseEvaluate reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("caseEvaluateId");
    		reqModel.setSortType("asc");
    	}
		Page<CaseEvaluate> page=caseEvaluateDao.findAllPaged(reqModel);
		return page;
	}
    
    public Page<CaseEvaluate> findAllLaywerPaged(CaseEvaluate reqModel)
 	{
 	    String orderFieldName=reqModel.getSortField();
 	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
     		reqModel.setSortField("caseEvaluateId");
     		reqModel.setSortType("asc");
     	}
 		Page<CaseEvaluate> page=caseEvaluateDao.findAllLaywerPaged(reqModel);
 		return page;
 	}
    
	
	public String insertCaseEvaluate(CaseEvaluate obj)
	{
		long retId=caseEvaluateDao.insert(obj);
		if(retId>0)
			return "";
		else
			return "新增失败";
	}
	
	public String updateCaseEvaluate(CaseEvaluate obj)
	{
		int ret=caseEvaluateDao.update(obj);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}
	
	public String deleteCaseEvaluate(Long id)
	{
		int ret=caseEvaluateDao.deleteById(id);
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long caseEvaluateId) {
		CaseEvaluate[] oldObjs = this.caseEvaluateDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (CaseEvaluate obj : oldObjs)
			if (obj.getCaseEvaluateId().equals(caseEvaluateId) == true)
				return false;
		return true;
	}
	
}