package lj.mapper.client;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.client.ClientRelation;

@Mapper
public interface ClientRelationMapper extends BaseMapper<ClientRelation> {
    //追加查询方法
    Page<ClientRelation> findAllPaged(ClientRelation reqModel);
    List<ClientRelation> findAllByRequestObj(ClientRelation reqModel);

    Page<ClientRelation> findById(ClientRelation reqModel, long clientId);

    //追加聚合方法
}
