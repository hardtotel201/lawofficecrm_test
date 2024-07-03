package lj.service.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lj.model.sys.ParamInfo;
import lj.service.base.BaseServiceConst;
import lj.service.sys.ParamInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;

import lj.mapper.client.ClientFollowMapper;
import lj.model.client.ClientFollow;
import lj.service.base.BaseService;
import lj.util.StringUtils;

@Service
public class ClientFollowService extends BaseService {

    @Autowired
	private ClientFollowMapper clientFollowDao=null;

	@Autowired
	private ParamInfoService paramInfoService;
	/**
	 * 查询成交客户服务项列表
	 * @return
	 */
	public List<String> getServiceItemNames(){
		ParamInfo param=this.paramInfoService.findByParamName(ParamInfoService.PARAMNAME_SERVICE_ITEM);
		String[] serviceItems=param.getParamValue().split(";");
		List<String> names=new ArrayList<String>();
		for(String serviceItem:serviceItems) {
//			String[] strs=serviceItem.split(",");
			names.add(serviceItem);
		}
		return names;
	}

	/**
	 * 查询客户跟进供应商列表
	 * @return
	 */
	public List<String> getContractOperators(){
		ParamInfo param=this.paramInfoService.findByParamName(ParamInfoService.PARAMNAME_CONTRACT_OPERATOR);
		String[] contractOperators=param.getParamValue().split(";");
		List<String> names=new ArrayList<String>();
		for(String contractOperator:contractOperators) {
			names.add(contractOperator);
		}
		return names;
	}

	/**
	 * 查询客户跟进商务列表
	 * @return
	 */
	public List<String> getFollowSalesPersons(){
		ParamInfo param=this.paramInfoService.findByParamName(ParamInfoService.PARAMNAME_FOLLOW_SALES_PERSON);
		String[] followSalesPersons=param.getParamValue().split(";");
		List<String> names=new ArrayList<String>();
		for(String followSalesPerson:followSalesPersons) {
			names.add(followSalesPerson);
		}
		return names;
	}

	/**
	 * //	 * 判断是否为成交客户
	 * //	 *
	 * //	 * @return String
	 * //
	 */
	public boolean isClientLevelDeal(Long clientId) {
		String thisLevel =  clientFollowDao.getClientLevelByClientId(clientId);
		return thisLevel != null && thisLevel.startsWith("成交");
	}

	public ClientFollow[] findAll()
    {
    	ClientFollow[] objs=clientFollowDao.findAll();
    	return objs;
    }
    
    public List<ClientFollow> findAllByRequestObj(ClientFollow reqModel)
    {
    	List<ClientFollow> objs=clientFollowDao.findAllByRequestObj(reqModel);
    	return objs;
    }
    
    public ClientFollow find(Long id)
	{
		ClientFollow obj=clientFollowDao.findById(id);
		return obj;
	}


    public Page<ClientFollow> findAllPagedClientFollow(ClientFollow reqModel)
	{
	    String orderFieldName=reqModel.getSortField();
	    if(StringUtils.isNullOrEmpty(orderFieldName)==true){
    		reqModel.setSortField("clientFollowId");
    		reqModel.setSortType("asc");
    	}
		Page<ClientFollow> page=clientFollowDao.findAllPaged(reqModel);
		return page;
	}
	
	public String insertClientFollow(ClientFollow newClientFollow)
	{

		Date nowTime=this.clientFollowDao.getNowTime();
		newClientFollow.setOpTime(nowTime);


		int rows = this.clientFollowDao.insert(newClientFollow);
		if (rows<=0)
			return BaseServiceConst.MSG_INSERT_FAIL;
		return StringUtils.STR_EMPTY;
	}
	
	public String updateClientFollow(ClientFollow obj)
	{
		Date nowTime=this.clientFollowDao.getNowTime();
		obj.setOpTime(nowTime);
		int ret=clientFollowDao.update(obj);
		if(ret>0)
			return "";
		else
			return "更新客户跟进信息失败!";
	}
	
	public String deleteClientFollow(Long id)
	{
		int ret=clientFollowDao.deleteById(id);
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
	public boolean isRepeat(String fieldName, Object fieldValue, Long clientFollowId) {
		ClientFollow[] oldObjs = this.clientFollowDao.findAllByField(fieldName, fieldValue);
		if (oldObjs == null || oldObjs.length <= 0)
			return false;
		for (ClientFollow obj : oldObjs)
			if (obj.getClientFollowId().equals(clientFollowId) == true)
				return false;
		return true;
	}
	
}