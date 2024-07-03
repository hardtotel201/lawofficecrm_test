package lj.mapper.survey;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.survey.SurveyInfo;

@Mapper
public interface SurveyInfoMapper extends BaseMapper<SurveyInfo> {
    public Date getNowTime();
    //追加查询方法
    Page<SurveyInfo> findAllPaged(SurveyInfo reqModel);
    List<SurveyInfo> findAllByRequestObj(SurveyInfo reqModel);
   
    //追加聚合方法
}
