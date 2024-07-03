package lj.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import lj.mapper.base.BaseMapper;
import lj.model.user.RolePriv;

@Mapper
public interface RolePrivMapper extends BaseMapper<RolePriv> {
	RolePriv[] findAllByRoleId(long roleId);
	List<RolePriv> findAllByModuleId(long moduleId);
	int deleteByRoleIdAndNotModuleIds(Map<String,Object> params);
	List<RolePriv> findAllByRoleIdAndModuleIds(Map<String,Object> params);
}
