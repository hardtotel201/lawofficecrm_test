package lj.mapper.client;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.client.ClientInfo;

@Mapper
public interface ClientInfoMapper extends BaseMapper<ClientInfo> {
    //追加查询方法
    public Date getNowTime();


    Page<ClientInfo> findAllPaged(ClientInfo reqModel);
    List<ClientInfo> findAllByRequestObj(ClientInfo reqModel);
    Page<ClientInfo> findAllPagedClientInfo(ClientInfo reqModel);
    List<ClientInfo> findAllByClientPhone(String clientPhone);
    List<ClientInfo> findAllByClientWeixin(String clientWeixin);
    //追加聚合方法

    // 删除客户

}