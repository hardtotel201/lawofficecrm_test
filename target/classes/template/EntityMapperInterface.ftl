package ${packageName};

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import ${modelClassPath}.${modelClassName};

@Mapper
public interface ${interfaceName} extends BaseMapper<${modelClassName}> {
    //追加查询方法
    Page<${modelClassName}> findAllPaged(${modelClassName} reqModel);
    List<${modelClassName}> findAllByRequestObj(${modelClassName} reqModel);
    //追加聚合方法
}
