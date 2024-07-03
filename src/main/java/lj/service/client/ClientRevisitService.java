package lj.service.client;

import java.util.Date;
import java.util.List;

import lj.service.base.BaseServiceConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;

import lj.mapper.client.ClientRevisitMapper;
import lj.model.client.ClientRevisit;
import lj.service.base.BaseService;
import lj.util.StringUtils;

@Service
public class ClientRevisitService extends BaseService {

    @Autowired
	private ClientRevisitMapper clientRevisitDao=null;
	
	public ClientRevisit[] findAll()
    {
    	ClientRevisit[] objs=clientRevisitDao.findAll();
    	return objs;
    }
    
    public List<ClientRevisit> findAllByRequestObj(ClientRevisit reqModel)
    {
    	List<ClientRevisit> objs=clientRevisitDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public ClientRevisit find(Long id)
	{
		ClientRevisit obj=clientRevisitDao.findById(id);
		return obj;
	}
    

    public Page<ClientRevisit> findAllPagedClientRevisit(ClientRevisit reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("clientRevisitId");
    		reqModel.setSortType("asc");
    	}
		Page<ClientRevisit> page=clientRevisitDao.findAllPaged(reqModel);
		return page;
	}
	
	public String insertClientRevisit(ClientRevisit newClientRevisit)
	{

		Date nowTime=this.clientRevisitDao.getNowTime();
		newClientRevisit.setOpTime(nowTime);


		int rows = this.clientRevisitDao.insert(newClientRevisit);
		if (rows<=0)
			return BaseServiceConst.MSG_INSERT_FAIL;
		return StringUtils.STR_EMPTY;
	}
	
	public String updateClientRevisit(ClientRevisit obj)
	{
		Date nowTime=this.clientRevisitDao.getNowTime();
		obj.setOpTime(nowTime);
		int ret=clientRevisitDao.update(obj);
		if(ret>0)
			return "";
		else
			return "更新客户跟进信息失败!";
	}
	
	public String deleteClientRevisit(Long id)
	{
		int ret=clientRevisitDao.deleteById(id);
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long clientRevisitId) {
		ClientRevisit[] oldObjs = this.clientRevisitDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (ClientRevisit obj : oldObjs)
			if (obj.getClientRevisitId().equals(clientRevisitId) == true)
				return false;
		return true;
	}
	
}