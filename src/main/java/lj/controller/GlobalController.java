package lj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lj.global.AppConst;
import lj.global.AppVar;
import lj.model.sys.ModuleInfo;
import lj.model.user.UserInfo;
import lj.service.user.UserInfoService;
import lj.service.user.UserRoleService;
import lj.util.StringUtils;

/**
 * 全局控制器
 * @author samlv
 *
 */
@Controller
public class GlobalController extends BaseController {
	
	@Autowired
	private UserInfoService userService = null;
	
	@Autowired
	private UserRoleService userRoleService=null;
	


	@RequestMapping(value = { "/" })
	public String index(Model model) {
		System.out.println("GlobalController.index:to login.jsp");
		return "login";
	}

	@RequestMapping(value = { "/login"}, method = RequestMethod.POST)
	public @ResponseBody String login(HttpServletRequest req,String userCode, String userPassword,Model model) {
		System.out.println("login userCode:" + userCode + ",userPassword:" + userPassword);
		String msg = userService.login(userCode, userPassword);
		System.out.println("login reponse:" + msg);
		if (StringUtils.isNullOrEmpty(msg) == true){
			//1-将用户信息放入Session
			UserInfo userInfo=userService.findUserInfoByUserCode(userCode);
			req.getSession().setAttribute(AppConst.SESSION_USERINFO
					, userInfo);
			return msg;
		}
		else{
			model.addAttribute("userCode", userCode);
			model.addAttribute("userPassword", userPassword);
			model.addAttribute("errorMessage", msg);
			return msg;
		}
	}
	
	
	@RequestMapping(value = { "main" })
	public String main(HttpServletRequest req,Model model) {
		//1-读入用户模块信息
		long loginUserId = BaseController.getLoginUser(req).getUserId();
		ModuleInfo[] parentModules=userRoleService.findAllUserModules(loginUserId);
		System.out.println("GlobalController.main() parentModules:"+lj.util.JsonUtils.objectToJson(parentModules));
		List<ModuleInfo> list=new ArrayList<ModuleInfo>();
		for(ModuleInfo obj:parentModules)
			if(obj.getChildModules().length>0)
				list.add(obj);
		model.addAttribute("parentModules", list);
		return "main";
	}
	
	
	@RequestMapping(value = { "/exitSystem" })
	public @ResponseBody String exitSystem(HttpServletRequest req) {
		//1-清空Session
		req.getSession().invalidate();
		//2-退出系统
		return StringUtils.STR_EMPTY;
	}
	

	
	/**
	 * 下载文件Action
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadFile")
	public  @ResponseBody String downloadFile(String fileName, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ fileName);
		try {
			String path =AppVar.staticConfig.getUploadDir();
			System.out.println("downloadFile path:"+path);
			System.out.println("downloadFile fileName:"+fileName);
			InputStream inputStream = new FileInputStream(new File(path
					+ File.separator +fileName));
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[2048];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				System.out.println("downloadFile segment length:"+length);
				os.write(buffer, 0, length);
			}
			 // 这里主要关闭。
			os.flush();
			os.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	
}
