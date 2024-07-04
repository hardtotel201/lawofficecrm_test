package lj.service.user;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import lj.global.AppVar;
import lj.mapper.user.UserInfoMapper;
import lj.model.user.RoleInfo;
import lj.model.user.UserInfo;
import lj.model.user.UserRole;
import lj.service.base.BaseService;
import lj.service.base.BaseServiceConst;
import lj.util.JsonUtils;
import lj.util.Md5Utils;
import lj.util.StringUtils;
import net.bytebuddy.asm.Advice.This;

/**
 * 用户信息服务
 * @author samlv
 *
 */
@ComponentScan(basePackages = "lj")
@Service
public class UserInfoService extends BaseService {
	//管理员用户标识
	public final static long USERID_ADMIN=1l;

	@Autowired
	private UserInfoMapper userDao = null;

	@Autowired
	private UserRoleService userRoleService=null;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

	
	/**
	 * 分页查询
	 * @param reqModel
	 * @return
	 */
	public Page<UserInfo> findAllPaged(UserInfo reqModel) {
		//1-分页查询
		Page<UserInfo> pager = userDao.findAllPaged(reqModel);
		return pager;
	}
	
	/**
	 * 根据角色标识返回用户对象数组
	 * @param roleId
	 * @return
	 */
	public UserInfo[] findAllByRoleId(long roleId) {
		UserInfo[] objs=this.userDao.findAllByRoleId(roleId);
		return objs;
	}
	

	/**
	 * 根据用户编号查询用户信息
	 * 
	 * @param userCode
	 * @return
	 */
	public UserInfo findUserInfoByUserCode(String userCode) {
		//1-查询用户信息
		UserInfo user = userDao.findByUserCode(userCode);
		if(user==null)
		{
			System.out.println("UserInfoService.findUserInfoByUserCode:not find user by "+userCode);
			return null;
		}
		//2-用户角色
		UserRole[] userRoles = this.userRoleService.findAllByUserId(user.getUserId());
		user.setUserRoles(userRoles);
		//3-查询用户角色
		List<String> strs = new ArrayList<String>();
		for (UserRole ur : userRoles)
				strs.add(ur.getRoleName());
		user.setUserRoleNames(String.join(",", strs));
		strs = new ArrayList<String>();
		for (UserRole ur : userRoles)
				strs.add(""+ur.getRoleId());
		user.setRoleIds(String.join(",", strs));
		return user;
	}
	

	/**
	 * 登陆
	 * 
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	public String login(String userCode, String userPassword) {
		UserInfo reqObj=new UserInfo();
		reqObj.setUserCode(userCode);
		reqObj.setUserPassword(userPassword);
		String str=this.login(reqObj);
		return str;
	}
	
	/**
	 * 登陆
	 * @param reqObj
	 * @return
	 */
	public String login(UserInfo reqObj) {
		String str = StringUtils.STR_EMPTY;
		// 1-根据用户口令查询用户信息
		UserInfo oldObj = this.findUserInfoByUserCode(reqObj.getUserCode());
		if(oldObj==null)
			return "用户账号不存在!";
		// 2-比较用户密码
		String encodeInput = Md5Utils.MD5Encode(reqObj.getUserPassword());
		if (oldObj.getUserPassword().equals(encodeInput) == false)
			return UserInfoServiceConst.LOGIN_ERROR_PASSWORD;
		// 3-查询用户状态
		String userState = oldObj.getUserState();
		if (userState != null && userState.equals(UserInfoServiceConst.USER_STATE_WRITEOFF))
			return UserInfoServiceConst.LOGIN_ERROR_WRITEOFF;
		// 4-更新用户信息
		Date nowTime=userDao.getNowTime();
		oldObj.setLastLoginTime(nowTime);
		if(StringUtils.isNullOrEmpty(reqObj.getUserWeixin())==false)
			oldObj.setUserWeixin(reqObj.getUserWeixin());
		System.out.println("UserInfoService.login return:" + JsonUtils.objectToJson(oldObj));
		if (userDao.update(oldObj) <= 0)
			return "更新用户登陆信息失败!";
		return str;
	}
	


	/**
	 * 新增用户信息
	 * 
	 * @param newUserInfo
	 * @param opUserId
	 * @return
	 */
	public String insertUserInfo(UserInfo newUserInfo) {
		//1-检查用户输入
		UserInfo existUserInfo = userDao.findByUserCode(newUserInfo.getUserCode());
		if (existUserInfo != null)
			return "用户编号已经存在";
		UserInfo[] objs = userDao.findAllByUserMobile(newUserInfo.getUserMobile());
		if(objs!=null && objs.length>0)
			return "用户手机已经存在";
		//2-设置一些后端设置的字段
		String encodePassword = "";
		if (StringUtils.isNullOrEmpty(newUserInfo.getUserPassword()) == true)
			encodePassword = Md5Utils.MD5Encode(AppVar.defaultPassword);
		else
			encodePassword = Md5Utils.MD5Encode(newUserInfo.getUserPassword());
		if(newUserInfo.getOpUserId()<=0)
			newUserInfo.setOpUserId(USERID_ADMIN);
		newUserInfo.setUserPassword(encodePassword);
		Date nowTime=this.userDao.getNowTime();
		newUserInfo.setOpTime(nowTime);
		newUserInfo.setRegisterTime(nowTime);
		newUserInfo.setUserState(UserInfoServiceConst.USER_STATE_NORMAL);
		//3-新增用户信息
		int rows = this.userDao.insert(newUserInfo);
		if (rows<=0)
			return BaseServiceConst.MSG_INSERT_FAIL;
		//4-新增用户权限
		long userId=newUserInfo.getUserId();
		if(StringUtils.isNullOrEmpty(newUserInfo.getRoleIds())==false){
			String[] strRoleIds=newUserInfo.getRoleIds().split(",");
			for(int i=0;i<strRoleIds.length;++i)
			{
				long roleId=Long.parseLong(strRoleIds[i]);
				System.out.println("UserInfoService.insertUserInfo insert role roleId:"+roleId+";userId:"+userId);
				this.userRoleService.insert(new UserRole(-1l,roleId,userId));
			}
		}
		return StringUtils.STR_EMPTY;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param obj
	 * @param opUserId
	 * @return
	 */
	public String updateUserInfo(UserInfo obj, long opUserId) {

		// 工号存在&&状态=="正常";
		UserInfo otherUser = userDao.findByUserCode(obj.getUserCode());
		if (otherUser != null
				&& (otherUser.getUserState() == null
						|| otherUser.getUserState().equals(UserInfoServiceConst.USER_STATE_NORMAL))
				&& otherUser.getUserId().equals(obj.getUserId()) == false)
			return "不能使用正在使用的用户编号";
		Date opTime = userDao.getNowTime();
		obj.setOpTime(opTime);
		obj.setOpUserId(opUserId);

		if (userDao.update(obj) <= 0)
			return "更新用户信息失败!";
		//2-更新用户角色
		if(StringUtils.isNullOrEmpty(obj.getRoleIds())==false){
			String[] strRoleIds=obj.getRoleIds().split(",");
			long[] roleIds=new long[strRoleIds.length];
			for(int i=0;i<roleIds.length;++i)
				roleIds[i]=Long.parseLong(strRoleIds[i]);
			this.userRoleService.updateUserRoles(obj.getUserId(),roleIds);
		}
		return "";
	}

	/**
	 * 更新用户密码
	 * 
	 * @param userId
	 * @param userPassword
	 * @return
	 */
	public String changeUserPassword(long userId, String userPassword, long opUserId) {
		String encodePassword = Md5Utils.MD5Encode(userPassword);
		int ret = userDao.changeUserPassword(userId, encodePassword);
		if (ret > 0)
			return "";
		else
			return "更新密码失败!";
	}

	/**
	 * 删除用户信息
	 * 
	 * @param groupId
	 * @return
	 */
	public String deleteUserInfo(long userId) {
		userDao.deleteById(userId);
		return "";
	}

	/**
	 * 注销用户信息
	 * 
	 * @param groupId
	 * @return
	 */
	public String writeoffUserInfo(long userId, long opUserId) {
		UserInfo userInfo = userDao.findById(userId);
		userInfo.setUserState(UserInfoServiceConst.USER_STATE_WRITEOFF);
		int ret = userDao.update(userInfo);
		if (ret > 0)
			return "";
		else
			return "注销失败!";
	}


	public UserInfo find(long userId) {
		UserInfo user = this.userDao.findById(userId);
		return user;
	}

	public UserInfo findByUserName(String UserName) {
		UserInfo user = this.userDao.findAllByUserName(UserName);
		return user;
	}

	/**
	 * APP功能：用户注册
	 * 
	 * @param userMobile
	 * @return
	 */
	@Transactional
	public String register(UserInfo userInfo, long roleId) {
		userInfo.setRoleIds(""+roleId);
	    userInfo.setOpUserId(null);
		String msg = this.insertUserInfo(userInfo);
		return msg;
	}

	/**
	 * APP功能：用户注册
	 * 
	 * @param userMobile
	 * @return
	 */
	@Transactional
	public String registerByUserMobile(String userMobile) {
		UserInfo[] objs=this.findAllByUserMobile(userMobile);
		if(objs!=null && objs.length>0)
			return "此手机已经注册";
		UserInfo newUserInfo = new UserInfo();
		newUserInfo.setUserMobile(userMobile);
		String userCode = "user" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_FILENAME);
		newUserInfo.setUserName(userCode);
		newUserInfo.setUserCode(userCode);
		newUserInfo.setRoleIds(""+RoleInfo.ROLE_ID_COMMON);
		newUserInfo.setOpUserId(USERID_ADMIN);
		String msg = this.insertUserInfo(newUserInfo);
		return msg;
	}
	
	/**
	 * APP功能：用户注册
	 * 
	 * @param userWeixin
	 * @param roleId
	 * @return
	 */
	@Transactional
	public String registerByUserWeixin(String userWeixin,long roleId) {
		//1-查询用户信息
		UserInfo[] objs=this.findAllByUserWeixin(userWeixin);
		if(objs!=null && objs.length>0)
			return "此微信已经注册";
		//2-新增用户信息和权限信息
		UserInfo newUserInfo = new UserInfo();
		newUserInfo.setUserWeixin(userWeixin);
		String userCode = "";
		switch((int)roleId) {
			case (int)RoleInfo.ROLE_ID_ADMIN:
				userCode="admin" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_STR);
				break;
			case (int)RoleInfo.ROLE_ID_SCHOOL:
				userCode="school" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_STR);
				break;
			case (int)RoleInfo.ROLE_ID_SHOP:
				userCode="shop" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_STR);
				break;
			default:
				userCode="user" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_STR);
		}
		newUserInfo.setUserName(userCode);
		newUserInfo.setUserCode(userCode);
		newUserInfo.setRoleIds(""+roleId);
		newUserInfo.setOpUserId(USERID_ADMIN);
		String msg = this.insertUserInfo(newUserInfo);
		return msg;
	}

	/**
	 * 根据用户微信登陆或注册用户
	 * @param userWeixin 用户微信（openId）
	 * @param roleId 角色信息
	 * @return
	 */
	@Transactional
	public UserInfo loginOrRegisterByUserWeixin(String userWeixin,long roleId) {
		//1-查询用户信息
		UserInfo[] objs=this.findAllByUserWeixin(userWeixin);
		if(objs!=null && objs.length>0)
			return objs[0];
		//2-新增用户信息和权限信息
		UserInfo newUserInfo = new UserInfo();
		newUserInfo.setUserWeixin(userWeixin);
		String userCode = "";
		switch((int)roleId) {
			case (int)RoleInfo.ROLE_ID_ADMIN:
				userCode="admin" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_STR);
				break;
			case (int)RoleInfo.ROLE_ID_SCHOOL:
				userCode="school" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_STR);
				break;
			case (int)RoleInfo.ROLE_ID_SHOP:
				userCode="shop" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_STR);
				break;
			default:
				userCode="user" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_STR);
		}
		newUserInfo.setUserName(userCode);
		newUserInfo.setUserCode(userCode);
		newUserInfo.setRoleIds(""+roleId);
		newUserInfo.setOpUserId(USERID_ADMIN);
		String msg = this.insertUserInfo(newUserInfo);
		if (!StringUtils.isNullOrEmpty(msg)) return null;
		return newUserInfo;
	}
	
	/**
	 * APP功能：用户注册
	 * 
	 * @param userInfo
	 * @param roleId
	 * @return
	 */
	@Transactional
	public String registerWeixinUser(UserInfo userInfo,long roleId) {
		//1-查询用户信息
		UserInfo[] objs=this.findAllByUserWeixin(userInfo.getUserWeixin());
		if(objs!=null && objs.length>0)
			return "此微信已经注册";
		//2-新增用户信息和权限信息
		UserInfo newUserInfo = new UserInfo();
		newUserInfo.setUserWeixin(userInfo.getUserWeixin());
		String userCode = "";
		switch((int)roleId) {
			case (int)RoleInfo.ROLE_ID_ADMIN:
				userCode="admin" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_STR);
				break;
			case (int)RoleInfo.ROLE_ID_SCHOOL:
				userCode="school" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_STR);
				break;
			case (int)RoleInfo.ROLE_ID_SHOP:
				userCode="shop" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_STR);
				break;
			default:
				userCode="user" + StringUtils.timeToString(this.getNowTime(), StringUtils.DATETIME_FORMAT_STR);
		}
		newUserInfo.setUserName(userInfo.getUserName());
		newUserInfo.setUserMobile(userInfo.getUserMobile());
		newUserInfo.setUserSex(userInfo.getUserSex());
		newUserInfo.setUserCode(userCode);
		newUserInfo.setRoleIds(""+roleId);
		newUserInfo.setUserWeixin(""+userInfo.getUserWeixin());
		newUserInfo.setUserEmail(userInfo.getUserEmail());
		newUserInfo.setOpUserId(USERID_ADMIN);
		String msg = this.insertUserInfo(newUserInfo);
		return msg;
	}
	
	/**
	 * APP功能：管理员用户注册
	 * 
	 * @param userMobile
	 * @return
	 */
//	@Transactional
//	public String registerSchoolAdminUser(UserInfo userInfo, long roleId) {
//		String msg = this.insertUserInfo(userInfo, 1L);
//		if (!StringUtils.isNullOrEmpty(msg))
//			return msg;
//		UserRole userRole = new UserRole(-1L, roleId, userInfo.getUserId());
//	    long id= this.userRoleService.insert(userRole);
//		if (id > 0)
//			return StringUtils.STR_EMPTY;
//		else
//			return "注册用户失败!";
//	}

	/**
	 * 用户签到
	 * @param userId
	 * @return
	 */
	public boolean signIn(long userId) {
		String key = "signIn:" + userId;
		String value = LocalDate.now().format(dtf);
		System.out.println("key: " + key + ", value: " + value);
		if(redisTemplate.opsForValue().get(key) != null) {
			System.out.println(redisTemplate.opsForValue().get(key));
			return false; // 已签到
		} else {
			redisTemplate.opsForValue().set(key, value);
			return true; // 签到成功
		}
	}




	/**
	 * 根据用户手机查询用户信息
	 * 
	 * @param userMobile
	 * @return
	 */
	public UserInfo[] findAllByUserMobile(String userMobile) {
		UserInfo[] objs = this.userDao.findAllByUserMobile(userMobile);
		return objs;
	}
	
	public UserInfo[] findAllByUserWeixin(String weixin) {
		UserInfo[] objs = this.userDao.findAllByUserWeixin(weixin);
		return objs;
	}
	
	/**
	 * 查询所有用户简单信息(userId,userName,userCode,userMobile)
	 * @return
	 */
	public UserInfo[] findAllSimpleInfos()
	{
		UserInfo[] objs=this.userDao.findAllSimpleInfos();
		return objs;
	}


    public UserInfo[] findAllUsers() {
		UserInfo[] objs = userDao.findAll();
		return objs;
    }

	public UserInfo[] findAllProgrammer() {
		UserInfo[] objs = userDao.findAllProgrammer();
		return objs;
	}

}
