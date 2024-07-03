package lj.model.user;

import org.apache.ibatis.type.Alias;

import java.util.Date;
import lj.model.base.ClientRequest;

@Alias(value = "UserRole")
public class UserRole extends ClientRequest{
	private Long  userRoleId;
	private Long  roleId;
	private Long  userId;
    //下面是视图字段定义
	private String roleName;
    
	public Long getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public  UserRole() {
		super();
	}
	
	public  UserRole(
	    Long userRoleId,Long roleId,Long userId){
		super();
		this.userRoleId= userRoleId;
		this.roleId= roleId;
		this.userId= userId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}