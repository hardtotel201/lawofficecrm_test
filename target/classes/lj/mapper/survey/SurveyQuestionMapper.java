package lj.mapper.survey;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.survey.SurveyInfo;
import lj.model.survey.SurveyQuestion;

@Mapper
public interface SurveyQuestionMapper extends BaseMapper<SurveyQuestion> {
    //追加查询方法
    Page<SurveyQuestion> findAllPaged(SurveyQuestion reqModel);
    List<SurveyQuestion> findAllByRequestObj(SurveyQuestion reqModel);
    List<SurveyQuestion> findAllBySurveyId(Long surveyId);
    List<SurveyQuestion> findAllByQuestionId(Long questionId);
    Integer deleteBySurveyId(Long id);
    Integer deleteByQuestionId(Long id);
    //追加聚合方法
}
