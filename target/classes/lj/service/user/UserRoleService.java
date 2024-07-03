package lj.service.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import lj.mapper.sys.ModuleInfoMapper;
import lj.mapper.user.UserInfoMapper;
import lj.mapper.user.UserRoleMapper;
import lj.mapper.user.UserRolePrivMapper;
import lj.model.sys.ModuleInfo;
import lj.model.user.RoleInfo;
import lj.model.user.UserInfo;
import lj.model.user.UserRole;
import lj.model.user.UserRolePriv;
import lj.service.base.BaseService;
import lj.service.base.BaseServiceConst;
import lj.util.StringUtils;

@ComponentScan(basePackages = "lj")
@Service
public class UserRoleService extends BaseService{
	
	
	@Autowired
	private UserInfoMapper userDao = null;

	@Autowired
	private UserRoleMapper userRoleDao = null;
	
	
	@Autowired
	private UserRolePrivMapper viUserRolePrivDao = null;
	
	@Autowired
	private ModuleInfoMapper moduleInfoDao = null;
	/**
	 * 查询用户所有权限
	 * 
	 * @param userId
	 * @return
	 */
	public UserRole[] findAllByUserId(Long userId) {
		UserRole[] userRoles = this.userRoleDao.findAllByUserId(userId);
		return userRoles;
	}
	
	
	public long insert(UserRole obj)
	{
		long ret=userRoleDao.insert(obj);
		return ret;
	}

	/**
	 * 用户是否是管理员
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isAdmin(long userId) {
		UserRole[] userRoles = this.findAllByUserId(userId);
		Stream<UserRole> stream = Arrays.asList(userRoles).stream()
				.filter(it -> it.getRoleId() == RoleInfo.ROLE_ID_ADMIN);
		return stream.count() > 0;
	}

	
	/**
	 * 查询某个角色的所有用户信息
	 * 
	 * @param roleId
	 * @return
	 */
	public UserInfo[] findAllByRoleId(long roleId) {
		UserInfo[] objs = this.userDao.findAllByRoleId(roleId);
		return objs;
	}
	
	/**
	 * 查询一组用户的用户角色
	 */
	public UserRole[] findAllByUserIds(Long[] userIds) {
		String strUserIds=StringUtils.join(userIds, ",");
		UserRole[] objs=this.userRoleDao.findAllByUserIds(strUserIds);
		System.out.println("UserRoleService.findAllByUserIds:"+lj.util.JsonUtils.objectToJson(objs));
		return objs;
	}

	
	/**
	 * 更新用户权限
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	public String updateUserRoles(long userId, long[] roleIds) {
		//1-删除权限
		int ret=this.userRoleDao.deleteByUserId(userId);
		if (roleIds.length <= 0)
			return StringUtils.STR_EMPTY;
		//2-新增权限
		for (long roleId : roleIds) {
			UserRole obj = new UserRole(null,roleId,userId);
			long newId = this.userRoleDao.insert(obj);
			if (newId < 0)
				return BaseServiceConst.MSG_UPDATE_FAIL;
		}
		return StringUtils.STR_EMPTY;
	}
	
	
	public UserRolePriv[] findAllUserRolePrivsByUserId(long userId) {
		UserRolePriv[] userPrivs = viUserRolePrivDao.findAllByUserId(userId);
		return userPrivs;
	}

	
	public ModuleInfo[] findAllUserModules(long userId) {
		// 1-查询用户权限
		UserRolePriv[] userPrivs = viUserRolePrivDao.findAllByUserId(userId);
		Set<Long> userModuleIds = new HashSet<Long>();
		for (UserRolePriv priv : userPrivs) {
			userModuleIds.add(priv.getModuleId());
		}
		// System.out.println("UserInfoService.findAllUserModules
		// userModuleIds:"+userModuleIds.size());
		// 2-查询所有父模块
		ModuleInfo[] allParentModules = moduleInfoDao.findAllParentModules();
		// System.out.println("UserInfoService.findAllUserModules
		// allParentModules:"+allParentModules.length);
		// 3-逐个模块访问，判断用户是否有权限访问
		List<ModuleInfo> retParentMoubles = new ArrayList<ModuleInfo>();
		for (ModuleInfo temp : allParentModules) {
			ModuleInfo[] children = moduleInfoDao.findAllChildModules(temp.getModuleId());
			boolean ifAdd = false;
			List<ModuleInfo> userModules = new ArrayList<ModuleInfo>();
			for (ModuleInfo module : children)
				if (userModuleIds.contains(module.getModuleId()) == true) {
					userModules.add(module);
					ifAdd = true;
				}

			if (ifAdd == true) {
				temp.setChildModules(userModules.toArray(new ModuleInfo[0]));
				retParentMoubles.add(temp);
			}
		}
		// System.out.println("UserInfoService.findAllUserModules
		// retParentMoubles:"+retParentMoubles.size());

		return retParentMoubles.toArray(new ModuleInfo[0]);
	}

}
