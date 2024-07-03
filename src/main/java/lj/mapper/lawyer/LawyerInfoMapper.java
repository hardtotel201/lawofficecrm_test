package lj.mapper.lawyer;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

import lj.mapper.base.BaseMapper;
import lj.model.client.ClientInfo;
import lj.model.lawyer.LawyerInfo;
import lj.model.user.UserInfo;

@Mapper
public interface LawyerInfoMapper extends BaseMapper<LawyerInfo> {
	public Date getNowTime();
    //追加查询方法
    Page<LawyerInfo> findAllPaged(LawyerInfo reqModel);
    
    //LawyerInfo[] findAllByLawyerPhone(String lawyerPhone);
    
    List<LawyerInfo> findAllByRequestObj(LawyerInfo reqModel);
    //Page<LawyerInfo> findAllPagedClientInfo(LawyerInfo reqModel);

    LawyerInfo[] findAllLawyers();
}
