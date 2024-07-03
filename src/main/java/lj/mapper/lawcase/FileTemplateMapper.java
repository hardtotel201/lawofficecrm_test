package lj.mapper.lawcase;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.lawcase.FileTemplate;

@Mapper
public interface FileTemplateMapper extends BaseMapper<FileTemplate> {
    //追加查询方法
    Page<FileTemplate> findAllPaged(FileTemplate reqModel);
    List<FileTemplate> findAllByRequestObj(FileTemplate reqModel);
    //追加聚合方法
}
