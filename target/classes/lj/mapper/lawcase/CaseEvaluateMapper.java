package lj.mapper.lawcase;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.lawcase.CaseEvaluate;

@Mapper
public interface CaseEvaluateMapper extends BaseMapper<CaseEvaluate> {
    //追加查询方法
    Page<CaseEvaluate> findAllPaged(CaseEvaluate reqModel);
    Page<CaseEvaluate> findAllLaywerPaged(CaseEvaluate reqModel);
    List<CaseEvaluate> findAllByRequestObj(CaseEvaluate reqModel);
    //追加聚合方法
}
