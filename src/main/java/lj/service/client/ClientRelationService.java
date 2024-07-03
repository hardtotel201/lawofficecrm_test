package lj.service.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;

import lj.mapper.client.ClientRelationMapper;
import lj.model.client.ClientRelation;
import lj.service.base.BaseService;
import lj.util.StringUtils;

@Service
public class ClientRelationService extends BaseService {

    @Autowired
	private ClientRelationMapper clientRelationDao=null;
	
	public ClientRelation[] findAll()
    {
    	ClientRelation[] objs=clientRelationDao.findAll();
    	return objs;
    }
    
    public List<ClientRelation> findAllByRequestObj(ClientRelation reqModel)
    {
    	List<ClientRelation> objs=clientRelationDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public ClientRelation find(Long id)
	{
		ClientRelation obj=clientRelationDao.findById(id);
		return obj;
	}
    

    public Page<ClientRelation> findAllPagedClientRelation(ClientRelation reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("clientRelationId");
    		reqModel.setSortType("asc");
    	}
		Page<ClientRelation> page=clientRelationDao.findAllPaged(reqModel);
		return page;
	}
	
	public Long insertClientRelation(ClientRelation obj)
	{
		long ret=clientRelationDao.insert(obj);
		if(ret>0)
		   return obj.getClientRelationId();
		else
		   return -1l;
	}
	
	public String updateClientRelation(ClientRelation obj)
	{
		int ret=clientRelationDao.update(obj);
		if(ret>0)
			return "";
		else
			return "修改失败!";
	}
	
	public String deleteClientRelation(Long id)
	{
		int ret=clientRelationDao.deleteById(id);
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long clientRelationId) {
		ClientRelation[] oldObjs = this.clientRelationDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (ClientRelation obj : oldObjs)
			if (obj.getClientRelationId().equals(clientRelationId) == true)
				return false;
		return true;
	}
	
}