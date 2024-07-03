package lj.model.user;

import org.apache.ibatis.type.Alias;

@Alias(value = "UserRolePriv")
public class UserRolePriv {
	private Long  userRoleId;
	public Long getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}
	private Long  roleId;
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	private Long  userId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	private String  roleName;
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	private Long  moduleId;
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	
	public  UserRolePriv() {
		super();
	}
	
	public  UserRolePriv(
	  Long userRoleId,Long roleId,Long userId,String roleName,Long moduleId){
		super();
		this.userRoleId= userRoleId;
		this.roleId= roleId;
		this.userId= userId;
		this.roleName= roleName;
		this.moduleId= moduleId;
	}
}