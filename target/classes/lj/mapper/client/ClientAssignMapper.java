package lj.mapper.client;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.client.ClientAssign;

@Mapper
public interface ClientAssignMapper extends BaseMapper<ClientAssign> {
    //追加查询方法

    public Date getNowTime();

    Page<ClientAssign> findAllPaged(ClientAssign reqModel);
    List<ClientAssign> findAllByRequestObj(ClientAssign reqModel);
    //追加聚合方法
}
