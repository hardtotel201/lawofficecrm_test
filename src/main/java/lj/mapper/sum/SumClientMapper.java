package lj.mapper.sum;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import lj.mapper.base.BaseMapper;
import lj.model.client.ClientInfo;
import lj.model.sum.SumByString;

@Mapper
public interface SumClientMapper extends BaseMapper<SumByString>{

	
	//采集统计
	List<SumByString> sumClientsByMonth(ClientInfo reqModel);
		
}
