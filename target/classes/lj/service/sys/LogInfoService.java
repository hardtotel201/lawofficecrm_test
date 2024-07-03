package lj.service.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import lj.mapper.sys.LogInfoMapper;
import lj.model.sys.LogInfo;
import lj.service.base.BaseService;

@Service
public class LogInfoService extends BaseService {

    @Autowired
	private LogInfoMapper logInfoDao=null;
	
	public LogInfo[] findAll()
    {
    	LogInfo[] objs=logInfoDao.findAll();
    	return objs;
    }
    
    public LogInfo find(Long id)
	{
		LogInfo obj=logInfoDao.findById(id);
		return obj;
	}
    

    public Page<LogInfo> findAllPagedLogInfo(LogInfo reqModel)
	{
		Page<LogInfo> page=logInfoDao.findAllPaged(reqModel);
		return page;
	}
	
	public Long insertLogInfo(LogInfo obj)
	{
		logInfoDao.insert(obj);
		return obj.getLogId();
	}
	
	public String updateLogInfo(LogInfo obj)
	{
		int ret=logInfoDao.update(obj);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}
	
	public String deleteLogInfo(Long id)
	{
		int ret=logInfoDao.deleteById(id);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}
	
}