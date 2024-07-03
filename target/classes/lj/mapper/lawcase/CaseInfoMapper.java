package lj.mapper.lawcase;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.lawcase.CaseInfo;

@Mapper
public interface CaseInfoMapper extends BaseMapper<CaseInfo> {
    public Date getNowTime();
/*    public String insertByClientName();*/
    //追加查询方法
    Page<CaseInfo> findAllPaged(CaseInfo reqModel);
    List<CaseInfo> findAllByRequestObj(CaseInfo reqModel);

    /*Page<CasePlan> findAllPaged(CasePlan reqModel);*/

    //追加聚合方法
}
