package lj.mapper.sys;

import java.util.Map;
import java.util.Date;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.sys.LogInfo;

@Mapper
public interface LogInfoMapper extends BaseMapper<LogInfo> {
    Page<LogInfo> findAllPaged(LogInfo reqModel);
}
