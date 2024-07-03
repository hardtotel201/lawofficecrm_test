package lj.mapper.user;

import org.apache.ibatis.annotations.Mapper;

import lj.mapper.base.BaseMapper;
import lj.model.user.UserRolePriv;

/**
 * 用户角色权限映射器
 * @author samlv
 *
 */
@Mapper
public interface UserRolePrivMapper extends BaseMapper<UserRolePriv> {
	UserRolePriv[] findAllByUserId(long userId);
}
