package lj.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import lj.mapper.sys.ModuleInfoMapper;
import lj.mapper.user.RolePrivMapper;
import lj.model.sys.ModuleInfo;
import lj.model.user.RolePriv;
import lj.service.base.BaseService;

@ComponentScan(basePackages = "lj")
@Service
public class ModuleInfoService extends BaseService {
	@Autowired
	private ModuleInfoMapper moduleInfoDao = null;
	
	@Autowired
	private RolePrivMapper rolePrivDao=null;
	
	/**
	 * 分页查询模块信息
	 * 
	 * @param pageIndex
	 * @param sortField
	 * @param sortType
	 * @param queryModuleName
	 * @param queryEntityName
	 * @return
	 */
	public Page<ModuleInfo> findAllPagedModuleInfos(ModuleInfo reqModel) {
		Page<ModuleInfo> pager = moduleInfoDao.findAllPaged(reqModel);
		return pager;
	}

	public ModuleInfo[] findAllModules() {
		ModuleInfo[] modules = moduleInfoDao.findAll();
		return modules;
	}

	public ModuleInfo[] findAllParentModules() {
		ModuleInfo[] modules = moduleInfoDao.findAllParentModules();
		return modules;
	}

	/**
	 * 查询所有父模块(包含其子模块)
	 * 
	 * @return
	 */
	public ModuleInfo[] findAllViParentModules() {
		ModuleInfo[] modules = moduleInfoDao.findAllParentModules();
		for (ModuleInfo temp : modules) {
			ModuleInfo[] children = moduleInfoDao.findAllChildModules(temp.getModuleId());
			temp.setChildModules(children);
		}
		return modules;
	}

	public long insertModuleInfo(ModuleInfo obj) {
		long newId = moduleInfoDao.insert(obj);
		return newId;
	}

	public String updateModuleInfo(ModuleInfo obj) {
		int ret= moduleInfoDao.update(obj);
		if(ret<=0)
			return "更新失败!";
		else
			return "";
	}

	public String deleteModuleInfo(long id) {
		if (this.isInuse(id)==true)
			return "模块已经使用，不允许删除!";
		int ret= moduleInfoDao.deleteById(id);
		if(ret<=0)
			return "删除失败!";
		else
			return "";
	}
	
	/**
	 * 模块标识是否已经使用
	 * @param moduleId
	 * @return
	 */
	public boolean isInuse(long moduleId) {
		if (moduleInfoDao.countChildModules(moduleId)>0)
			return true;
		List<RolePriv> rolePrivs=this.rolePrivDao.findAllByModuleId(moduleId);
		if(rolePrivs!=null && rolePrivs.size()>0)
			return true;
		return false;
	}

	

}
