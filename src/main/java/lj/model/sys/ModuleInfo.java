package lj.model.sys;

import org.apache.ibatis.type.Alias;

import lj.model.base.ClientRequest;

@Alias(value = "ModuleInfo")
public class ModuleInfo extends ClientRequest {
	// 数据表字段
	private Long moduleId;
	private Long parentModuleId;
	private String moduleName;
	private String moduleCode;
	private String moduleTitle;
	private String moduleKind;
	private String moduleUrl;
	private String moduleIcon;
	// 视图字段
	private String parentModuleTitle;
	private ModuleInfo[] childModules;

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getParentModuleId() {
		return parentModuleId;
	}

	public void setParentModuleId(Long parentModuleId) {
		this.parentModuleId = parentModuleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleTitle() {
		return moduleTitle;
	}

	public void setModuleTitle(String moduleTitle) {
		this.moduleTitle = moduleTitle;
	}

	public String getModuleKind() {
		return moduleKind;
	}

	public void setModuleKind(String moduleKind) {
		this.moduleKind = moduleKind;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public String getModuleIcon() {
		return moduleIcon;
	}

	public void setModuleIcon(String moduleIcon) {
		this.moduleIcon = moduleIcon;
	}

	public ModuleInfo() {
		super();
	}



	public String getParentModuleTitle() {
		return parentModuleTitle;
	}

	public void setParentModuleTitle(String parentModuleTitle) {
		this.parentModuleTitle = parentModuleTitle;
	}

	public ModuleInfo[] getChildModules() {
		return childModules;
	}

	public void setChildModules(ModuleInfo[] childModules) {
		this.childModules = childModules;
	}

	public ModuleInfo(Long moduleId, Long parentModuleId, String moduleName, String moduleCode, String moduleTitle,
			String moduleKind, String moduleUrl,String moduleIcon) {
		super();
		this.moduleId = moduleId;
		this.parentModuleId = parentModuleId;
		this.moduleName = moduleName;
		this.moduleCode = moduleCode;
		this.moduleTitle = moduleTitle;
		this.moduleKind = moduleKind;
		this.moduleUrl = moduleUrl;
		this.moduleIcon=moduleIcon;
	}
}