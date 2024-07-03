package lj.mapper.survey;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.survey.SurveyQuestion;
import lj.model.survey.SurveyQuestionnaire;

@Mapper
public interface SurveyQuestionnaireMapper extends BaseMapper<SurveyQuestionnaire> {
    //追加查询方法
    Page<SurveyQuestionnaire> findAllPaged(SurveyQuestionnaire reqModel);
    List<SurveyQuestionnaire> findAllByRequestObj(SurveyQuestionnaire reqModel);
    //追加聚合方法
}
