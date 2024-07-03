package lj.model.user;

import org.apache.ibatis.type.Alias;

@Alias(value = "RolePriv")
public class RolePriv {
	private Long privId;
	private Long moduleId;
	private Long roleId;
	private String privInfo;
	//视图字段
	private String moduleTitle;
	private String roleName;
	

	public Long getPrivId() {
		return privId;
	}

	public void setPrivId(Long privId) {
		this.privId = privId;
	}

	

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	

	public String getPrivInfo() {
		return privInfo;
	}

	public void setPrivInfo(String privInfo) {
		this.privInfo = privInfo;
	}

	

	public String getModuleTitle() {
		return moduleTitle;
	}

	public void setModuleTitle(String moduleTitle) {
		this.moduleTitle = moduleTitle;
	}

	public RolePriv() {
		super();
	}

	public RolePriv(Long privId, Long roleId, Long moduleId, String privInfo) {
		super();
		this.privId = privId;
		this.moduleId = moduleId;
		this.roleId = roleId;
		this.privInfo = privInfo;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}