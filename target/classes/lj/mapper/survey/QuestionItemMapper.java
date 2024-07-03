package lj.mapper.survey;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.survey.QuestionItem;

@Mapper
public interface QuestionItemMapper extends BaseMapper<QuestionItem> {
    //追加查询方法
    Page<QuestionItem> findAllPaged(QuestionItem reqModel);
    List<QuestionItem> findAllByRequestObj(QuestionItem reqModel);
    List<QuestionItem> findByQuestionId(Long questionId);

    Integer deleteByQuestionId(Long id);
    //追加聚合方法
}
