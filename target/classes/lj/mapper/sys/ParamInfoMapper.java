package lj.mapper.sys;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.sys.ParamInfo;

@Mapper
public interface ParamInfoMapper extends BaseMapper<ParamInfo> {
    Page<ParamInfo> findAllPaged(ParamInfo reqModel);
    List<ParamInfo> findAllByParamName(String paramName);
}
