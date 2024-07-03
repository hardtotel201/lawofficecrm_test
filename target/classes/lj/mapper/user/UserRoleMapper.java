package lj.mapper.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.user.UserRole;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    Page<UserRole> findAllPaged(UserRole reqModel);
    
    UserRole[] findAllByUserId(@Param("userId")Long userId);
    
    int deleteByUserId(long userId);
    
    UserRole[] findAllByUserIds(String strUserIds);
    
    
}
