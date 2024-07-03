package lj.controller.user;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import lj.model.user.RoleInfo;
import lj.model.user.RolePriv;
import lj.service.base.BaseServiceConst;
import lj.service.sys.ModuleInfoService;
import lj.service.user.RoleInfoService;
import lj.util.DatatablesReturnObject;
import lj.util.JsonUtils;
import lj.util.LogUtils;
import lj.util.StringUtils;

/**
 * 角色信息控制器
 * @author samlvThinkpad
 *127.0.0.1:8081/user/roleInfoPage
 */
@Controller
@RequestMapping("user")
public class RoleInfoController  extends BaseController {

	@Autowired
	private ModuleInfoService moduleInfoService;
	
	@Autowired
	private RoleInfoService roleInfoService;

	
	/**
	 * 进入角色信息页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/roleInfoPage" })
	public String roleInfo(Model model,HttpServletRequest req) {
		ModuleInfo[] parentModules = moduleInfoService.findAllViParentModules();
		model.addAttribute("parentModules", parentModules);
		return "user/roleInfoPage";
	}

	/**
	 * 分页查询角色信息
	 * @param req
	 * @param requestModel
	 * @return
	 */
	@RequestMapping(value = { "/roleInfo" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<RoleInfo> findAllPagedRoleInfos(RoleInfo requestModel,HttpServletRequest req) {
		// 1-获得查询数据
		System.out.println("findAllPagedRoleInfos() request:" + JsonUtils.objectToJson(requestModel));
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		Page<RoleInfo> pager = roleInfoService.findAllPagedRoleInfos(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<RoleInfo> obj = new DatatablesReturnObject<RoleInfo>(requestModel.getDraw(),
				pager.getTotal(),pager);
		return obj;
	}
	
	/**
	 * 更新角色信息
	 * @param obj
	 * @return
	 */
	@RequestMapping(value = { "/roleInfo" },method = RequestMethod.PUT)
	public @ResponseBody String postroleInfo(@RequestBody RoleInfo obj,HttpServletRequest req) {
		System.out.println("postroleInfo() request:" + lj.util.JsonUtils.objectToJson(obj));
		String msg = roleInfoService.updateRoleInfo(obj);
		return msg;
	}


	@RequestMapping(value = { "/rolePrivs/getAllByRoleId" })
	public @ResponseBody RolePriv[] getAllByRoleId(String roleId,HttpServletRequest req) {
		Long id = null;
		try {
			id = Long.parseLong(roleId);
		} catch (Exception e) {
			id = null;
		}
		RolePriv[] objs = roleInfoService.findAllByRoleId(id);
		Gson gson = new GsonBuilder().setDateFormat(StringUtils.DATETIME_FORMAT_JSON).create();
		String str = gson.toJson(objs, RolePriv[].class);
		System.out.println("/rolePrivs/getAllByRoleId reponse:" + str);
		return objs;
	}

	/**
	 * 更新角色权限
	 * @param roleId
	 * @param moduleIds
	 * @return
	 */
	@RequestMapping(value = { "/rolePrivs/postRolePrivs" })
	public @ResponseBody String postRolePrivs(String roleId, String[] moduleIds,HttpServletRequest req) {
		String str = StringUtils.STR_EMPTY;
		if(moduleIds==null)
			moduleIds=new String[0];
		for (String moduleId : moduleIds)
			str = str + moduleId + ",";
		System.out.println("/rolePrivs/postRolePrivs moduleIds:" + str);
		String msg = StringUtils.STR_EMPTY;
		try {
			Long lRoleId = Long.parseLong(roleId);
			Set<Long> moduleIdList = new HashSet<Long>();
			for (String temp : moduleIds) {
				long moduleId = Long.parseLong(temp);
				moduleIdList.add(moduleId);
			}
			msg = roleInfoService.updateRolePrivs(lRoleId, moduleIdList.toArray(new Long[0]));
		} catch (Exception e) {
			msg = BaseServiceConst.MSG_UPDATE_FAIL;
			e.printStackTrace();
			LogUtils.logError("/rolePrivs/postRolePrivs:", e);
		}
		System.out.println("/rolePrivs/postRolePrivs response:" + msg);
		return msg;
	}
}
