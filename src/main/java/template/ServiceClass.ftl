package ${serviceClassPath};

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;

import ${mapperInterfacePath}.${modelClassName}Mapper;
import ${modelClassPath}.${modelClassName};
import lj.service.base.BaseService;
import lj.util.StringUtils;

@Service
public class ${modelClassName}Service extends BaseService {

    @Autowired
	private ${modelClassName}Mapper ${modelClassNameFS}Dao=null;
	
	public ${modelClassName}[] findAll()
    {
    	${modelClassName}[] objs=${modelClassNameFS}Dao.findAll();
    	return objs;
    }
    
    public List<${modelClassName}> findAllByRequestObj(${modelClassName} reqModel)
    {
    	List<${modelClassName}> objs=${modelClassNameFS}Dao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public ${modelClassName} find(Long id)
	{
		${modelClassName} obj=${modelClassNameFS}Dao.findById(id);
		return obj;
	}
    

    public Page<${modelClassName}> findAllPaged${modelClassName}(${modelClassName} reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("${indexName}");
    		reqModel.setSortType("asc");
    	}
		Page<${modelClassName}> page=${modelClassNameFS}Dao.findAllPaged(reqModel);
		return page;
	}
	
	public String insert${modelClassName}(${modelClassName} obj)
	{
		int affectRows=${modelClassNameFS}Dao.insert(obj);
		if (affectRows>0)
			return "";
		else
			return "新增失败";
	}
	
	public String update${modelClassName}(${modelClassName} obj)
	{
		int affectRows=${modelClassNameFS}Dao.update(obj);
		if(affectRows>0)
			return "";
		else
			return "修改失败!";
	}
	
	public String delete${modelClassName}(Long id)
	{
		int affectRows=${modelClassNameFS}Dao.deleteById(id);
		if(affectRows>0)
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long ${indexName}) {
		${modelClassName}[] oldObjs = this.${modelClassNameFS}Dao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (${modelClassName} obj : oldObjs)
			if (obj.${indexNameGetter}().equals(${indexName}) == true)
				return false;
		return true;
	}
	
}