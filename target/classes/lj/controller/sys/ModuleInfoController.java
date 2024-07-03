package lj.controller.sys;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lj.controller.BaseController;
import lj.model.sys.ModuleInfo;
import lj.service.base.BaseServiceConst;
import lj.service.sys.ModuleInfoService;
import lj.util.DatatablesReturnObject;
import lj.util.JsonUtils;
import lj.util.LogUtils;
import lj.util.StringUtils;

/**
 * 模块信息控制器
 * @author samlv
 *
 */
@Controller
@RequestMapping("sys")
public class ModuleInfoController extends BaseController{
	
	@Autowired
	private ModuleInfoService moduleInfoService=null;
	
	/**
	 * 进入模块信息页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/moduleInfoPage" })
	public String moduleInfos(Model model,HttpServletRequest req) {
		ModuleInfo[] objs = moduleInfoService.findAllParentModules();
		model.addAttribute("parentModules", objs);
		return "sys/moduleInfoPage";
	}

	/**
	 * 查询功能目录对象数组
	 * @return
	 */
	@RequestMapping(value = { "/moduleInfos/parentModuleInfos" })
	public @ResponseBody ModuleInfo[] parentModules(HttpServletRequest req) {
		ModuleInfo[] objs = moduleInfoService.findAllParentModules();
		Gson gson = new GsonBuilder().setDateFormat(StringUtils.DATETIME_FORMAT_JSON).create();
		String str = gson.toJson(objs, ModuleInfo[].class);
		System.out.println("/moduleInfos/parentModuleInfos reponse:" + str);
		return objs;
	}

	/**
	 * 分页查询
	 * @param request
	 * @param requestModel
	 * @return
	 */
	@RequestMapping(value = { "/moduleInfo" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<ModuleInfo> findAllPageModuleInfos(
			ModuleInfo requestModel,HttpServletRequest req) {
		// 1-解析请求
		System.out.println("findAllPageModuleInfos:" + JsonUtils.objectToJson(requestModel));
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		
		Page<ModuleInfo> page=moduleInfoService.findAllPagedModuleInfos(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<ModuleInfo> obj = new DatatablesReturnObject<ModuleInfo>(
				requestModel.getDraw(),page.getTotal(), page);
		return obj;
	}

	/**
	 * 新增/删除模块信息
	 * @param req
	 * @param obj
	 * @return
	 */
	@RequestMapping(value = { "/moduleInfo" }, method = RequestMethod.POST)
	public @ResponseBody String postModuleInfo(@RequestBody ModuleInfo obj,HttpServletRequest req) {
		System.out.println("postModuleInfo() request:"+lj.util.JsonUtils.objectToJson(obj));
		String msg = StringUtils.STR_EMPTY;
		long newId = moduleInfoService.insertModuleInfo(obj);
		if (newId <= 0)
			msg = BaseServiceConst.MSG_INSERT_FAIL;
		System.out.println("postModuleInfo() response:" + msg);
		return msg;
	}
	
	/**
	 * 修改模块信息
	 * @param obj
	 * @return
	 */
	@RequestMapping(value = { "/moduleInfo" }, method = RequestMethod.PUT)
	public @ResponseBody String putModuleInfo(@RequestBody ModuleInfo obj,HttpServletRequest req) {
		System.out.println("putModuleInfo() request:"+lj.util.JsonUtils.objectToJson(obj));
		String msg =msg = moduleInfoService.updateModuleInfo(obj);
		System.out.println("putModuleInfo() response:" + msg);
		return msg;
	}

	/**
	 * 删除模块信息
	 * @param moduleId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/moduleInfo/{moduleId}"}, method = RequestMethod.DELETE)
	public @ResponseBody String deleteModuleInfo(@PathVariable Long moduleId,HttpServletRequest req) {
		System.out.println("deleteModuleInfo moduleId:" + moduleId);
		String msg = StringUtils.STR_EMPTY;
		try {
			msg = moduleInfoService.deleteModuleInfo(moduleId);
		} catch (Exception e) {
			msg = BaseServiceConst.MSG_DELETE_FAIL;
			e.printStackTrace();
			LogUtils.logError("deleteModuleInfo:", e);
		}
		System.out.println("deleteModuleInfo response:" + msg);
		return msg;
	}

}
