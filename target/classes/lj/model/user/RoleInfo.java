package lj.model.user;

import org.apache.ibatis.type.Alias;

import lj.model.base.ClientRequest;

/**
 * 角色信息
 * @author samlv
 *
 */
@Alias(value = "RoleInfo")
public class RoleInfo extends ClientRequest{
	public final static long ROLE_ID_ADMIN  =1l;   //管理员
	public final static long ROLE_ID_COMMON =2l;   //用户
	public final static long ROLE_ID_SHOP   =3l;   //商家
	public final static long ROLE_ID_SCHOOL =4l;   //学校管理员

	
	private Long  roleId;
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	private String  roleName;
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	private String  roleMemo;
	public String getRoleMemo() {
		return roleMemo;
	}
	public void setRoleMemo(String roleMemo) {
		this.roleMemo = roleMemo;
	}
	
	public  RoleInfo() {
		super();
	}
	
	public  RoleInfo(
	  Long roleId,String roleName,String roleMemo){
		super();
		this.roleId= roleId;
		this.roleName= roleName;
		this.roleMemo= roleMemo;
	}
}