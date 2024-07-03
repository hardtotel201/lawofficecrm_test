package lj.controller.lawcase;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lj.model.lawcase.FileTemplate;
import lj.service.lawcase.FileTemplateService;
import lj.util.DatatablesReturnObject;
import lj.util.StringUtils;
import lj.controller.BaseController;

/**
 * 文档模板控制器
 * @author samlvThinkpad
 *
 */
@Controller
@RequestMapping("lawcase")
public class FileTemplateController extends BaseController {
	@Autowired
	private FileTemplateService fileTemplateService;

	/**
	 * 进入文档模板页面
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/fileTemplatePage" }, method = RequestMethod.GET)
	public String fileTemplatePage(Model model,HttpServletRequest req) {
		String[] fileCategories=FileTemplateService.getFileCategories();
		model.addAttribute("fileCategories",fileCategories);
		return "lawcase/fileTemplatePage";
	}
	
	/**
	 * 分页查询
	 * @param requestModel
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/fileTemplate" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<FileTemplate> findAllPaged(FileTemplate requestModel,HttpServletRequest req) {
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<FileTemplate> page = fileTemplateService.findAllPagedFileTemplate(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<FileTemplate> obj = new DatatablesReturnObject<FileTemplate>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	/**
	 * 新增
	 * @param obj
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/fileTemplate" }, method = RequestMethod.POST)
	public @ResponseBody String insertFileTemplate(@RequestBody FileTemplate obj,HttpServletRequest req)
	{
		long loginUserId=this.getLoginUserId(req);
		obj.setOpUserId(loginUserId);
		obj.setOpTime(this.fileTemplateService.getNowTime());
		Long ret=fileTemplateService.insertFileTemplate(obj);
		if(ret>0)
			return "";
		else
			return "新增失败";
	}
	
	/**
	 * 更新
	 * @param obj
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/fileTemplate" }, method = RequestMethod.PUT)
	public @ResponseBody String updateFileTemplate(@RequestBody FileTemplate obj,HttpServletRequest req)
	{
		//1-设置后台属性
		long loginUserId=this.getLoginUserId(req);
		obj.setOpUserId(loginUserId);
		obj.setOpTime(this.fileTemplateService.getNowTime());
		FileTemplate oldObj=this.fileTemplateService.find(obj.getFileTemplateId());
		if(StringUtils.isNullOrEmpty(obj.getFileName())==true)
			obj.setFileName(oldObj.getFileName());
		//2-更新文档模板
		String msg=fileTemplateService.updateFileTemplate(obj);
		return msg;
	}
	
	@RequestMapping(value = { "/fileTemplate/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteFileTemplate(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=fileTemplateService.deleteFileTemplate(id);
		return msg;
	}
	
}
