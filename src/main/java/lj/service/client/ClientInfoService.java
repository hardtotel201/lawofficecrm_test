package lj.service.client;

import java.util.Date;
import java.util.List;

import lj.service.base.BaseServiceConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;

import lj.mapper.client.ClientInfoMapper;
import lj.model.client.ClientInfo;
import lj.service.base.BaseService;
import lj.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户信息服务
 * @author samlvThinkpad
 *
 */
@ComponentScan(basePackages = "lj")
@Service
public class ClientInfoService extends BaseService {
	public final static String CLIENT_STATE_NORMAL="正常";
	public final static String CLIENT_STATE_WRITEOFF="注销";
	
	

    @Autowired
	private ClientInfoMapper clientInfoDao=null;

    /**
     * 返回候选客户状态
     * @return
     */
    public static String[] getClientStates() {
    	return new String[] {CLIENT_STATE_NORMAL,CLIENT_STATE_WRITEOFF};
    }
    

	public ClientInfo[] findAll()
    {
    	ClientInfo[] objs=clientInfoDao.findAll();
    	return objs;
    }
    
    public List<ClientInfo> findAllByRequestObj(ClientInfo reqModel)
    {
    	List<ClientInfo> objs=clientInfoDao.findAllByRequestObj(reqModel);
    	return objs;
    }

	public List<ClientInfo> findAllByClientPhone(String clientPhone)
	{
		List<ClientInfo> objs=clientInfoDao.findAllByClientPhone(clientPhone);
		return objs;
	}

	public List<ClientInfo> findAllByClientWeixin(String clientWeixin){
		return clientInfoDao.findAllByClientWeixin(clientWeixin);
	}
    
    public ClientInfo find(Long id)
	{
		ClientInfo obj=clientInfoDao.findById(id);
		return obj;
	}


    public Page<ClientInfo> findAllPagedClientInfo(ClientInfo reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("clientId");
    		reqModel.setSortType("asc");
    	}
		Page<ClientInfo> page=clientInfoDao.findAllPaged(reqModel);
		return page;
	}
	
    /**
     * 新增客户
     * @param newClientInfo
     * @return
     */
	public String insertClientInfo(ClientInfo newClientInfo)
	{
		//1-客户信息是否重复
		ClientInfo reqModel=new ClientInfo();
		reqModel.setClientName(newClientInfo.getClientName());
//		reqModel.setClientPhone(newClientInfo.getClientPhone());
		List<ClientInfo> exists=this.findAllByRequestObj(reqModel);
		if(exists!=null && exists.size()>0) {
			return "该名称的客户已经存在!";
		}
		//2-设置后端属性
		newClientInfo.setClientState(ClientInfoServiceConst.CLIENT_STATE_NORMAL);
        //3-新增
		int rows = this.clientInfoDao.insert(newClientInfo);
		if (rows<=0)
			return BaseServiceConst.MSG_INSERT_FAIL;
		return StringUtils.STR_EMPTY;
	}

	public Long insertClient(ClientInfo obj) {
		Date nowTime=this.clientInfoDao.getNowTime();
		obj.setOpTime(nowTime);
		obj.setRegisterTime(nowTime);
		obj.setClientState(ClientInfoServiceConst.CLIENT_STATE_NORMAL);
		long ret=clientInfoDao.insert(obj);
		if(ret>0)
			return obj.getClientId();
		else
			return -1l;
	}

//	public String importClientInfo()
//	{
//
//	}

	public String updateClientInfo(ClientInfo obj, long opUserId)
	{
		Date nowTime=this.clientInfoDao.getNowTime();
		obj.setOpTime(nowTime);
//		if (clientInfoDao.update(obj) <= 0)
//			return "更新客户信息失败!";
		int ret=clientInfoDao.update(obj);
		if(ret>0)
			return "";
		else
			return "更新客户信息失败!";
	}

	/**
	 * 删除客户
	 * @param id
	 * @return
	 */
	@Transactional
	public String deleteClientInfo(Long id)
	{
		int ret=clientInfoDao.deleteById(id);
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
	 * @param clientId
	 * @return
	 */
	public boolean isRepeat(String fieldName, Object fieldValue, Long clientId) {
		ClientInfo[] oldObjs = this.clientInfoDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (ClientInfo obj : oldObjs)
			if (obj.getClientId().equals(clientId) == true)
				return false;
		return true;
	}

	public void save(List<ClientInfo> list) {
	}
}