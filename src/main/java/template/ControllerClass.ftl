package ${controllerClassPath};

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

import ${modelClassPath}.${modelClassName};
import ${serviceClassPath}.${modelClassName}Service;
import lj.util.DatatablesReturnObject;
import lj.controller.BaseController;


@Controller
@RequestMapping("${parentContextUrl}")
public class ${modelClassName}Controller extends BaseController {
	@Autowired
	private ${modelClassName}Service ${modelClassNameFS}Service;

    /**
	 * 进入页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/${modelClassNameFS}Page" }, method = RequestMethod.GET)
	public String ${modelClassNameFS}Page(Model model,HttpServletRequest req) {
		return "${parentContextUrl}/${modelClassNameFS}Page";
	}
	
	/**
	 * 分页查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/${modelClassNameFS}" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<${modelClassName}> findAllPaged(${modelClassName} requestModel,HttpServletRequest req) {
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<${modelClassName}> page = ${modelClassNameFS}Service.findAllPaged${modelClassName}(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<${modelClassName}> obj = new DatatablesReturnObject<${modelClassName}>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	/**
	 * 新增
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/${modelClassNameFS}" }, method = RequestMethod.POST)
	public @ResponseBody String insert${modelClassName}(@RequestBody ${modelClassName} obj,HttpServletRequest req)
	{
		String msg=${modelClassNameFS}Service.insert${modelClassName}(obj);
		return msg;
	}
	
	/**
	 * 修改
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/${modelClassNameFS}" }, method = RequestMethod.PUT)
	public @ResponseBody String update${modelClassName}(@RequestBody ${modelClassName} obj,HttpServletRequest req)
	{
		String msg=${modelClassNameFS}Service.update${modelClassName}(obj);
		return msg;
	}
	
	/**
	 * 删除
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/${modelClassNameFS}/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String delete${modelClassName}(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=${modelClassNameFS}Service.delete${modelClassName}(id);
		return msg;
	}
	
}
