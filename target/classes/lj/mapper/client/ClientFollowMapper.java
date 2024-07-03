package lj.mapper.client;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.client.ClientFollow;

@Mapper
public interface ClientFollowMapper extends BaseMapper<ClientFollow> {
    //追加查询方法

    public Date getNowTime();

    Page<ClientFollow> findAllPaged(ClientFollow reqModel);
    List<ClientFollow> findAllByRequestObj(ClientFollow reqModel);
    String getClientLevelByClientId(Long clientId);
    //追加聚合方法
}
