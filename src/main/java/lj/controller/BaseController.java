package lj.controller;

import javax.servlet.http.HttpServletRequest;

import lj.global.AppConst;
import lj.model.user.UserInfo;

/**
 * 控制器基类
 * @author samlv
 *
 */
public class BaseController {
	/**
	 * 获得登录用户
	 * 
	 * @param req
	 * @return
	 */
	public static UserInfo getLoginUser(HttpServletRequest req) {
		
		UserInfo user = (UserInfo) req.getSession().getAttribute(AppConst.SESSION_USERINFO);
		return user;
	}
	
	
	public static long getLoginUserId(HttpServletRequest req) {
		return getLoginUser(req).getUserId();
	}
}
