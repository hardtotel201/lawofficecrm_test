package lj.mapper.survey;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.survey.QuestionInfo;

@Mapper
public interface QuestionInfoMapper extends BaseMapper<QuestionInfo> {
    public Date getNowTime();
    //追加查询方法
    Page<QuestionInfo> findAllPaged(QuestionInfo reqModel);
    List<QuestionInfo> findAllByRequestObj(QuestionInfo reqModel);
    QuestionInfo findOneByQuestionTitle(String questionTitle);
    //追加聚合方法
    
}
