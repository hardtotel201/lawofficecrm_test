package lj.mapper.user;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.user.UserInfo;

/**
 * 用户信息Mapper
 * @author samlv
 *
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
	                
	public UserInfo findByUserCode(@Param("userCode") String userCode);
	
	
	public Date getNowTime();
	
	public int updateUserPassword();
	
	public int writeoffUserInfo();
	
	public int changeUserPassword(@Param("userId") long userId, @Param("userPassword")String userPassword);
	
	Page<UserInfo> findAllPaged(UserInfo reqModel);
	
	/**
	 * 根据手机查询用户信息
	 * @param userMobile
	 * @return
	 */
	UserInfo[] findAllByUserMobile(String userMobile);

	UserInfo findAllByUserName(String userName);

	UserInfo[] findAllByUserWeixin(String userWeixin);
	
	UserInfo[] findAllByRoleId(long roleId);
	
	/**
	 * 查询所有用户简易信息
	 * @return
	 */
	UserInfo[] findAllSimpleInfos();


    UserInfo[] findAllProgrammer();
}
