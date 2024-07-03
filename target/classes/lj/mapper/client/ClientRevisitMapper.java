package lj.mapper.client;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.client.ClientRevisit;

@Mapper
public interface ClientRevisitMapper extends BaseMapper<ClientRevisit> {
    //追加查询方法

    public Date getNowTime();

    Page<ClientRevisit> findAllPaged(ClientRevisit reqModel);
    List<ClientRevisit> findAllByRequestObj(ClientRevisit reqModel);
    //追加聚合方法
}
