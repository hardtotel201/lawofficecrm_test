package lj.service.client;

import java.util.Date;
import java.util.List;

import lj.service.base.BaseServiceConst;
import lj.service.user.UserInfoServiceConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;

import lj.mapper.client.ClientAssignMapper;
import lj.model.client.ClientAssign;
import lj.service.base.BaseService;
import lj.util.StringUtils;

@Service
public class ClientAssignService extends BaseService {

    @Autowired
	private ClientAssignMapper clientAssignDao=null;
	
	public ClientAssign[] findAll()
    {
    	ClientAssign[] objs=clientAssignDao.findAll();
    	return objs;
    }
	
	/**
	 * 查询分配信息
	 * @param userId
	 * @param clientId
	 * @return
	 */
	public List<ClientAssign> findAllByUserIdAndClientId(long userId,long clientId)
    {
		ClientAssign reqObj=new ClientAssign();
		reqObj.setUserId(userId);
		reqObj.setClientId(clientId);
		List<ClientAssign> objs=this.findAllByRequestObj(reqObj);
    	return objs;
    }
    
    public List<ClientAssign> findAllByRequestObj(ClientAssign reqModel)
    {
    	List<ClientAssign> objs=clientAssignDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public ClientAssign find(Long id)
	{
		ClientAssign obj=clientAssignDao.findById(id);
		return obj;
	}
    

    public Page<ClientAssign> findAllPagedClientAssign(ClientAssign reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("clientAssignId");
    		reqModel.setSortType("asc");
    	}
		Page<ClientAssign> page=clientAssignDao.findAllPaged(reqModel);
		return page;
	}
	
	public String insertClientAssign(ClientAssign newClientAssign)
	{
		Date nowTime=this.clientAssignDao.getNowTime();
		newClientAssign.setAssignTime(nowTime);

		//3-新增客户分配信息
		int rows = this.clientAssignDao.insert(newClientAssign);
		if (rows<=0)
			return BaseServiceConst.MSG_INSERT_FAIL;
		return StringUtils.STR_EMPTY;
		//		long ret=this.clientAssignDao.insert(newClientAssign);
		//		if(ret>0)
		//		   return newClientAssign.getClientAssignId();
		//		else
		//		   return -1l;
	}

	/**
	 * 更新客户分配信息
	 * @param obj
	 * @return
	 */
	public String updateClientAssign(ClientAssign obj)
	{
		Date nowTime=this.clientAssignDao.getNowTime();
		obj.setAssignTime(nowTime);

		int ret=clientAssignDao.update(obj);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}
	
	public String deleteClientAssign(Long id)
	{
		int ret=clientAssignDao.deleteById(id);
		if(ret>0)
			return "";
		else
			return "删除失败!";
	}
	
	/**
	 * 某个字段值是否重复
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @param id
	 * @return
	 */
	public boolean isRepeat(String fieldName, Object fieldValue, Long clientAssignId) {
		ClientAssign[] oldObjs = this.clientAssignDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (ClientAssign obj : oldObjs)
			if (obj.getClientAssignId().equals(clientAssignId) == true)
				return false;
		return true;
	}
	
}