package lj.mapper.user;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.user.RoleInfo;

@Mapper
public interface RoleInfoMapper extends BaseMapper<RoleInfo>{
	Page<RoleInfo> findAllPaged(RoleInfo reqObj);
	
	/**
	 * 查询用户的所有角色
	 * @param userId
	 * @return
	 */
	RoleInfo[] findAllByUserId(Long userId);
}
