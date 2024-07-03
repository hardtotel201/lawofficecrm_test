package lj.service.lawcase;

import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;

import lj.global.AppVar;
import lj.mapper.lawcase.FileTemplateMapper;
import lj.model.lawcase.FileTemplate;
import lj.service.base.BaseService;
import lj.util.StringUtils;

/**
 * 文档模板服务类
 * @author samlvThinkpad
 *
 */
@Service
public class FileTemplateService extends BaseService {
	
	public final static String FILE_CATEGORY_MS="民事";
	public final static String FILE_CATEGORY_XS="刑事";
	public final static String FILE_CATEGORY_GW="顾问";
	
	public static String[] getFileCategories() {
		return new String[] {FILE_CATEGORY_MS,FILE_CATEGORY_XS,FILE_CATEGORY_GW};
	}

    @Autowired
	private FileTemplateMapper fileTemplateDao=null;
	
	public FileTemplate[] findAll()
    {
    	FileTemplate[] objs=fileTemplateDao.findAll();
    	return objs;
    }
    
    public List<FileTemplate> findAllByRequestObj(FileTemplate reqModel)
    {
    	List<FileTemplate> objs=fileTemplateDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public FileTemplate find(Long id)
	{
		FileTemplate obj=fileTemplateDao.findById(id);
		return obj;
	}
    
    /**
     * 分页查询
     * @param reqModel
     * @return
     */
    public Page<FileTemplate> findAllPagedFileTemplate(FileTemplate reqModel)
	{
    	//1-设置排序
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("fileTemplateId");
    		reqModel.setSortType("asc");
    	}
	    //2-分页查询
		Page<FileTemplate> page=fileTemplateDao.findAllPaged(reqModel);
		//3-设置视图字段

		return page;
	}
	
	public Long insertFileTemplate(FileTemplate obj)
	{
		long ret=fileTemplateDao.insert(obj);
		if(ret>0)
		   return obj.getFileTemplateId();
		else
		   return -1l;
	}
	
	public String updateFileTemplate(FileTemplate obj)
	{
		int ret=fileTemplateDao.update(obj);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}
	
	public String deleteFileTemplate(Long id)
	{
		int ret=fileTemplateDao.deleteById(id);
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long fileTemplateId) {
		FileTemplate[] oldObjs = this.fileTemplateDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (FileTemplate obj : oldObjs)
			if (obj.getFileTemplateId().equals(fileTemplateId) == true)
				return false;
		return true;
	}
	
}