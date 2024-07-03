package lj.mapper.sys;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.github.pagehelper.Page;
import lj.mapper.base.BaseMapper;
import lj.model.sys.ModuleInfo;


@Mapper
public interface ModuleInfoMapper extends BaseMapper<ModuleInfo> {
	
	ModuleInfo[] findAllParentModules();
	ModuleInfo[] findAllChildModules(long moduleId);
	Page<ModuleInfo> findAllPaged(ModuleInfo reqModel);
    int countChildModules(long moduleId);
	
	
}
