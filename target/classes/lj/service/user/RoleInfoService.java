package lj.service.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import lj.mapper.user.RoleInfoMapper;
import lj.mapper.user.RolePrivMapper;
import lj.model.user.RoleInfo;
import lj.model.user.RolePriv;
import lj.service.base.BaseServiceConst;
import lj.util.StringUtils;

@Service
public class RoleInfoService {
	
	@Autowired
	private RoleInfoMapper roleInfoDao = null;
	
	@Autowired
	private RolePrivMapper rolePrivDao = null;
	
	
	public Page<RoleInfo> findAllPagedRoleInfos(RoleInfo reqObj) {
		Page<RoleInfo> pager = roleInfoDao.findAllPaged(reqObj);
		return pager;
	}

	public RoleInfo[] findAllRoles() {
		RoleInfo[] objs = roleInfoDao.findAll();
		return objs;
	}

	/**
	 * 更新角色信息
	 * @param obj
	 * @return
	 */
	public String updateRoleInfo(RoleInfo obj) {
		int ret=roleInfoDao.update(obj);
		if(ret<=0)
			return "更新失败!";
		else
			return "";
	}
	
	public RoleInfo findRoleInfo(long roleId)
	{
		RoleInfo obj=this.roleInfoDao.findById(roleId);
		return obj;
	}
	
	public RoleInfo[] findAllByUserId(long userId)
	{
		RoleInfo[] objs=this.roleInfoDao.findAllByUserId(userId);
		return objs;
	}
	
	/**
	 * 更新角色权限
	 * 
	 * @param roleId
	 * @param moduleIds
	 * @return
	 */
	public String updateRolePrivs(long roleId, Long[] moduleIds) {
		String msg="";
		//1-删除不在moduleIds的角色权限
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("roleId", roleId);
		String str=StringUtils.join(moduleIds, ",");
		params.put("moduleIdsStr",str);
		int count=this.rolePrivDao.deleteByRoleIdAndNotModuleIds(params);
		//System.out.println("updateRolePrivs delete count:"+count);
		//2-查询已经存在的角色权限
		List<RolePriv> existRolePrivs=this.rolePrivDao.findAllByRoleIdAndModuleIds(params);
		
		Set<Long> existsModuleIdList=new HashSet<Long>();
		for(RolePriv temp:existRolePrivs)
			existsModuleIdList.add(temp.getModuleId());
		Long[] existsModuleIds=existsModuleIdList.toArray(new Long[0]);
		//System.out.println("updateRolePrivs exist count:"+existsModuleIds.length);
		//3-新增不存在的角色权限
		List<Long> newList=new ArrayList<Long>();
		for(long moduleId:moduleIds)
			if(Arrays.binarySearch(existsModuleIds, moduleId)<0){
				//System.out.println("updateRolePrivs not in moduleId:"+moduleId);
				newList.add(moduleId);
			}
		for(Long moduleId:newList)
		{
			RolePriv obj=new RolePriv(null,roleId,moduleId,"");
			long newId=this.rolePrivDao.insert(obj);
			if(newId<0)
				return BaseServiceConst.MSG_UPDATE_FAIL;
		}
		return msg;
	}
	
	
	/**
	 * 根据角色标识查询权限标识
	 * 
	 * @param roleId
	 * @return
	 */
	public RolePriv[] findAllByRoleId(long roleId) {
		RolePriv[] objs = rolePrivDao.findAllByRoleId(roleId);
		return objs;
	}
}
