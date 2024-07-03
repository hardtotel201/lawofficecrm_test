package lj.service.user;

import lj.service.base.BaseServiceConst;

public class UserInfoServiceConst extends BaseServiceConst {
	public final static String USER_STATE_NORMAL="正常";
	public final static String USER_STATE_WRITEOFF="注销";
	
	public final static String LOGIN_ERROR_USERNOTEXISTS="用户不存在";
	public final static String LOGIN_ERROR_PASSWORD="密码不正确!";
	public final static String LOGIN_ERROR_WRITEOFF= "用户已经注销!";
	
	public final static String MSG_USER_EXISTS="用户信息已经存在!";
}
