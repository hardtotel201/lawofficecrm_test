package lj.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.user.ReceiverAddress;

/**
 * 收货人地址Mapper
 * @author samlv
 *
 */
@Mapper
public interface ReceiverAddressMapper extends BaseMapper<ReceiverAddress> {
    Page<ReceiverAddress> findAllPaged(ReceiverAddress reqModel);
    List<ReceiverAddress> findAllByUserId(long userId);
    List<ReceiverAddress> findAllBySchoolAddressId(long schoolAddressId);
    
    List<ReceiverAddress> findAllDefaultByUserId(long userId);
    int updateDefault(@Param("userId")long userId,@Param("receiverAddressId")long receiverAddressId);
}
