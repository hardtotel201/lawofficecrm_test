package lj.controller.user;

import java.util.ArrayList;
import java.util.List;

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

import lj.controller.BaseController;
import lj.model.user.RoleInfo;
import lj.model.user.UserInfo;
import lj.model.user.UserRole;
import lj.service.base.BaseServiceConst;
import lj.service.user.RoleInfoService;
import lj.service.user.UserInfoService;
import lj.service.user.UserRoleService;
import lj.util.DatatablesReturnObject;
import lj.util.JsonUtils;
import lj.util.LogUtils;
import lj.util.StringUtils;

/**
 * 用户管理
 * @author samlv
 *
 */
@Controller
@RequestMapping("user")
public class UserInfoController extends BaseController {

	@Autowired
	private UserInfoService userInfoService = null;
	
	@Autowired
	private RoleInfoService roleInfoService=null;
	
    @Autowired
    private UserRoleService userRoleService=null;
    
	/**
	 * 进入用户管理页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/userInfoPage" })
	public String userInfo(Model model,HttpServletRequest req) {
		UserInfo loginUser=BaseController.getLoginUser(req);
		RoleInfo[] roleInfos = roleInfoService.findAllRoles();
		model.addAttribute("roleInfos", roleInfos);
		return "user/userInfoPage";
	}
	

	
	/**
	 * 分页查询用户信息
	 * @param req
	 * @param requestModel
	 * @return
	 */
	@RequestMapping(value = { "/userInfo" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<UserInfo> findAllPaged(UserInfo requestModel,HttpServletRequest req) {
		System.out.println("UserInfoController.findAllPaged() request:" + JsonUtils.objectToJson(requestModel));
		//1-查询用户信息
		int pageNum = requestModel.getStart() / requestModel.getLength() + 1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		Page<UserInfo> pager = userInfoService.findAllPaged(requestModel);
		//2-遍历用户，生成用户角色信息
		for(UserInfo user:pager.getResult())
		{
			//2.1-查询用户角色
			UserRole[] userRoles = this.userRoleService.findAllByUserId(user.getUserId());
			user.setUserRoles(userRoles);
			//2.2-设置用户角色字符串
			List<String> strs = new ArrayList<String>();
			for (UserRole ur : userRoles)
					strs.add(ur.getRoleName());
			user.setUserRoleNames(String.join(",", strs));
			//2.2-设置用户角色标识字符串
			strs = new ArrayList<String>();
			for (UserRole ur : userRoles)
					strs.add(""+ur.getRoleId());
			user.setRoleIds(String.join(",", strs));
		}
		// 9-生成返回格式
		DatatablesReturnObject<UserInfo> obj = new DatatablesReturnObject<UserInfo>(requestModel.getDraw(),
				pager.getTotal(), pager);
		return obj;
	}

	/**
	 * 新增用户信息
	 * @param req
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value = { "/userInfo","/userInfo/post" }, method = RequestMethod.POST)
	public @ResponseBody String postUserInfo(@RequestBody UserInfo userInfo,HttpServletRequest req) {
		System.out.println("UserInfoController.postUserInfo() request:" + JsonUtils.objectToJson(userInfo));
		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		userInfo.setOpUserId(loginUserId);
		//2-新增用户
		String msg = userInfoService.insertUserInfo(userInfo);
		System.out.println("UserInfoController.postUserInfo() response:" + msg);
		return msg;
	}

	
	/**
	 * 修改用户信息
	 * @param req
	 * @param obj
	 * @return
	 */
	@RequestMapping(value = { "/userInfo" }, method = RequestMethod.PUT)
	public @ResponseBody String put(@RequestBody UserInfo obj,HttpServletRequest req) {
		System.out.println("UserInfoController.postUserInfo() request:" + JsonUtils.objectToJson(obj));
		//1-查询老的用户信息
		UserInfo oldUser=this.userInfoService.find(obj.getUserId());
		if(oldUser==null)
			return "用户信息不存在!";
		//2-设置用户没有输入的老的字段
		obj.setUserPassword(oldUser.getUserPassword());
		obj.setRegisterTime(oldUser.getRegisterTime());
		obj.setLastLoginTime(oldUser.getLastLoginTime());
		obj.setUserState(oldUser.getUserState());
		if(StringUtils.isNullOrEmpty(obj.getRoleIds())==true)
			obj.setRoleIds(oldUser.getRoleIds());
		//3-设置后端管理的相关字段
		long opUserId = BaseController.getLoginUser(req).getUserId();
		obj.setOpUserId(opUserId);
		//4-修改用户信息
		String msg = userInfoService.updateUserInfo(obj, opUserId);
		return msg;
	}
	
	/**
	 * 删除
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/userInfo/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteUserInfo(@PathVariable Long id,HttpServletRequest req) {
		System.out.println("UserInfoController.deleteUserInfo() request:" +id);
		String msg = userInfoService.deleteUserInfo(id);
		System.out.println("UserInfoController.deleteUserInfo() response:" + msg);
		return msg;
	}
	
	/**
	 * 根据用户编号查询用户信息
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = { "/userInfo/findByUserCode" })
	public @ResponseBody UserInfo findByUserCode(String userCode,HttpServletRequest req) {
		UserInfo user = this.userInfoService.findUserInfoByUserCode(userCode);
		return user;
	}


	/**
	 * 注销
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/userInfo/writeoff" })
	public @ResponseBody String writeoffUserInfo(long userId,HttpServletRequest req) {
		System.out.println("/userInfo/writeoff userId:" + userId);
		String msg = StringUtils.STR_EMPTY;
		try {
			msg = userInfoService.writeoffUserInfo(userId, this.getLoginUser(req).getUserId());
		} catch (Exception e) {
			msg = "注销失败!";
			e.printStackTrace();
			LogUtils.logError("/userInfo/writeoff:", e);
		}
		System.out.println("/userInfo/writeoff response:" + msg);
		return msg;
	}

	/**
	 * 更改密码
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/userInfo/changeUserPassword" })
	public @ResponseBody String changeUserPassword(String userPassword,HttpServletRequest req) {
		System.out.println("/userInfo/changeUserPassword userPassword:" + userPassword);
		String msg = StringUtils.STR_EMPTY;
		try {
			long loginUserId = BaseController.getLoginUser(req).getUserId();
			msg = userInfoService.changeUserPassword(loginUserId, userPassword, loginUserId);
		} catch (Exception e) {
			msg = BaseServiceConst.MSG_UPDATE_FAIL;
			e.printStackTrace();
			LogUtils.logError("/userInfo/changeUserPassword:", e);
		}
		System.out.println("/userInfo/changeUserPassword response:" + msg);
		return msg;
	}
	
	/**
	 * 查询用户的所有角色
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = { "/userRoles/getAllByUserId" })
	public @ResponseBody UserRole[] getAllByUserId(long userId,HttpServletRequest req) {
		UserRole[] objs = userRoleService.findAllByUserId(userId);
		System.out.println("/userRoles/getAllByUserId reponse:" + lj.util.JsonUtils.objectToJson(objs));
		return objs;
	}
	

	/**
	 * 提交用户角色
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = { "/userRoles/postUserRoles" }, method = RequestMethod.POST)
	public @ResponseBody String postUserRoles(long userId, long[] roleIds,HttpServletRequest req) {
		System.out.println("/userRoles/postUserRoles userId:" + userId+" roleIds:"+JsonUtils.objectToJson(roleIds));
		String msg = StringUtils.STR_EMPTY;
		try {
			msg = this.userRoleService.updateUserRoles(userId, roleIds);
		} catch (Exception e) {
			msg = BaseServiceConst.MSG_UPDATE_FAIL;
			e.printStackTrace();
			LogUtils.logError("/userRoles/postUserRoles:", e);
		}
		System.out.println("/userRoles/postUserRoles response:" + msg);
		return msg;
	}
	
	/**
	 * 用户注册
	 * @param reqModel
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/register"}, method = RequestMethod.POST)
	public @ResponseBody String register(UserInfo reqModel,HttpServletRequest req) {
		String msg=this.userInfoService.register(reqModel,RoleInfo.ROLE_ID_COMMON);
		if(StringUtils.isNullOrEmpty(msg)==true)
			return "";
		else
			return "用户信息已经存在";
	}
	
	/**
	 * 管理员用户注册
	 * @param reqModel
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/wxRegisterSchoolAdminUser"}, method = RequestMethod.POST)
	public @ResponseBody String registerSchoolAdminUser(UserInfo reqModel,HttpServletRequest req) {
		//String msg=this.userInfoService.registerSchoolAdminUser(reqModel,RoleInfo.ROLE_ID_SCHOOL);
		String msg=this.userInfoService.register(reqModel,RoleInfo.ROLE_ID_SCHOOL);
		if(StringUtils.isNullOrEmpty(msg)==true)
			return "";
		else
			return "用户信息已经存在";
	}

}
