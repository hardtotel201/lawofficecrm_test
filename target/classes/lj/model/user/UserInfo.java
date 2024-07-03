package lj.model.user;

import java.util.Arrays;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lj.model.base.ClientRequest;

/**
 * 用户信息
 * @author samlv
 *
 */
@Alias(value = "UserInfo")
public class UserInfo extends ClientRequest{
	//数据表字段
	private Long userId;               //用户标识
	private String userCode;           //用户登录ID
	private String userPassword;       //用户密码
	private String userName;           //用户密码
	private String userSex;            //用户性别 
	private String userMobile;         //用户手机
	private String userIdCard;         //用户身份证
	private String userEmail;          //用户邮件
	private String userWeixin;         //用户微信(OpenID)
	private String userState;          //用户状态
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registerTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date   lastLoginTime;
	private String lastLoginAddress;
	private Integer isDataTotal;          //信息是否完整
	//视图字段
	private String opUserName;            //操作用户姓名
	private UserRole[] userRoles;         //用户角色对象数组
	private String userRoleNames;         //用户角色对象数组生成字符串
	private String roleIds;               //用户分配的角色
	private String randomCode;            //随机码(微信客户端授权)
	//查询字段
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registerTimeBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registerTimeEnd;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserIdCard() {
		return userIdCard;
	}

	public void setUserIdCard(String userIdCard) {
		this.userIdCard = userIdCard;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	
	
	public String getUserWeixin() {
		return userWeixin;
	}

	public void setUserWeixin(String userWeixin) {
		this.userWeixin = userWeixin;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	

	public String getLastLoginAddress() {
		return lastLoginAddress;
	}

	public void setLastLoginAddress(String lastLoginAddress) {
		this.lastLoginAddress = lastLoginAddress;
	}

	private Date opTime;

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	private Long opUserId;

	public Long getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Long opUserId) {
		this.opUserId = opUserId;
	}
	
	

	
	public UserInfo() {
		super();
	}

	
	

	public String getUserRoleNames() {
		return userRoleNames;
	}

	public void setUserRoleNames(String userRoleNames) {
		this.userRoleNames = userRoleNames;
	}
	
	

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	

	public String getOpUserName() {
		return opUserName;
	}

	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}

	public Integer getIsDataTotal() {
		return isDataTotal;
	}

	public void setIsDataTotal(Integer isDataTotal) {
		this.isDataTotal = isDataTotal;
	}

	public UserRole[] getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(UserRole[] userRoles) {
		this.userRoles = userRoles;
	}
	

	
	public Date getRegisterTimeBegin() {
		return registerTimeBegin;
	}

	public void setRegisterTimeBegin(Date registerTimeBegin) {
		this.registerTimeBegin = registerTimeBegin;
	}

	public Date getRegisterTimeEnd() {
		return registerTimeEnd;
	}

	public void setRegisterTimeEnd(Date registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
	}


	@Override
	public String toString() {
		return "UserInfo{" +
				"userId=" + userId +
				", userCode='" + userCode + '\'' +
				", userPassword='" + userPassword + '\'' +
				", userName='" + userName + '\'' +
				", userSex='" + userSex + '\'' +
				", userMobile='" + userMobile + '\'' +
				", userIdCard='" + userIdCard + '\'' +
				", userEmail='" + userEmail + '\'' +
				", userWeixin='" + userWeixin + '\'' +
				", userState='" + userState + '\'' +
				", registerTime=" + registerTime +
				", lastLoginTime=" + lastLoginTime +
				", lastLoginAddress='" + lastLoginAddress + '\'' +
				", isDataTotal=" + isDataTotal +
				", opUserName='" + opUserName + '\'' +
				", userRoles=" + Arrays.toString(userRoles) +
				", userRoleNames='" + userRoleNames + '\'' +
				", roleIds='" + roleIds + '\'' +
				", randomCode='" + randomCode + '\'' +
				", registerTimeBegin=" + registerTimeBegin +
				", registerTimeEnd=" + registerTimeEnd +
				", opTime=" + opTime +
				", opUserId=" + opUserId +
				'}';
	}

	public String getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}

	/**
	 * 管理员
	 * @return
	 */
	public boolean isAdmin() {
		if(this.getUserRoles()==null)
			return false;
		for(UserRole obj:this.getUserRoles())
			if(obj.getRoleId()==lj.model.user.RoleInfo.ROLE_ID_ADMIN)
				return true;
		return false;
	}
	
}