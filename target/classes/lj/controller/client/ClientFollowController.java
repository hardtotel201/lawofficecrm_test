package lj.controller.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lj.model.lawcase.FileTemplate;
import lj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lj.controller.BaseController;
import lj.model.client.ClientFollow;
import lj.model.client.ClientInfo;
import lj.service.client.ClientFollowService;
import lj.service.client.ClientInfoService;
import lj.util.DatatablesReturnObject;
import lj.util.JsonUtils;

/**
 * 客户关联控制器
 * @author samlvThinkpad
 *
 */
@Controller
@RequestMapping("client")
public class ClientFollowController extends BaseController {
	@Autowired
	private ClientFollowService clientFollowService;

	@Autowired
	private ClientInfoService clientInfoService;

	/**
	 * 客户关联页面进入控制器
	 * @param model
	 * @param clientId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/clientFollowPage","/clientFollowPage/{clientId}" }, method = RequestMethod.GET)
	public String clientFollowPage(@PathVariable(required=false)  Long clientId,Model model,HttpServletRequest req) {
		System.out.println("ClientFollowController.clientFollowPage() request:"+clientId);
		//0-所有客户都查询服务项
		List<String> serviceItemNames=this.clientFollowService.getServiceItemNames();
		for (String serviceItemName:serviceItemNames) {
			System.out.println("ClientFollowController.clientFollowPage() serviceItemName:"+serviceItemName);
		}
		model.addAttribute("serviceItemNames",serviceItemNames);
		//0-如果是成交客户,查询设置服务项
//		if (this.clientFollowService.isClientLevelDeal(clientId)) {
//			List<String> serviceItemNames=this.clientFollowService.getServiceItemNames();
//			for (String serviceItemName:serviceItemNames) {
//				System.out.println("ClientFollowController.clientFollowPage() serviceItemName:"+serviceItemName);
//			}
//			model.addAttribute("serviceItemNames",serviceItemNames);
//		}
		//1-如果包含路径参数查询客户信息
		if(clientId!=null && clientId>0)
		{
			ClientInfo clientInfo=this.clientInfoService.find(clientId);
			if(clientInfo!=null)
				model.addAttribute("clientInfo",clientInfo);
		}
		//2-查询授权客户
		//根据用户角色,设置是否右连接ClientAssign表
		ClientInfo requestModel=new ClientInfo();
		boolean isJoin=this.getLoginUser(req).isAdmin()==false;
		requestModel.setIsRightJoinClientAssign(isJoin);
		long loginUserId=this.getLoginUserId(req);
		requestModel.setLoginUserId(loginUserId);
		// model增加商务
		List<String> followSalesPersons=this.clientFollowService.getFollowSalesPersons();
		model.addAttribute("followSalesPersons",followSalesPersons);
		for (String followSalesPerson:followSalesPersons) {
			System.out.println("ClientFollowController.clientFollowPage() followSalesPerson:"+followSalesPerson);
		}
		// model增加供应商
		List<String> contractOperators=this.clientFollowService.getContractOperators();
		model.addAttribute("contractOperators",contractOperators);
		for (String contractOperator:contractOperators) {
			System.out.println("ClientFollowController.clientFollowPage() contractOperator:"+contractOperator);
		}
		// model增加客户信息
		List<ClientInfo> clientInfos = this.clientInfoService.findAllByRequestObj(requestModel);		
		model.addAttribute("clientInfos", clientInfos);
		return "client/clientFollowPage";
	}
	
	@RequestMapping(value = { "/clientFollow" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<ClientFollow> findAllPaged(ClientFollow requestModel,HttpServletRequest req) {
		System.out.println("ClientFollowController.findAllPaged() request:" + JsonUtils.objectToJson(requestModel));
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<ClientFollow> page = clientFollowService.findAllPagedClientFollow(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<ClientFollow> obj = new DatatablesReturnObject<ClientFollow>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	@RequestMapping(value = { "/clientFollow" }, method = RequestMethod.POST)
	public @ResponseBody String insertClientFollow(@RequestBody ClientFollow newClientFollow,HttpServletRequest req)
	{
		System.out.println("ClientFollowController.insertClientFollow() request:" + JsonUtils.objectToJson(newClientFollow));

		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		newClientFollow.setOpUserId(loginUserId);
		//2-新增客户跟进
		String msg = clientFollowService.insertClientFollow(newClientFollow);
		System.out.println("ClientFollowController.insertClientFollow() response:" + msg);
		return msg;
	}
	
	@RequestMapping(value = { "/clientFollow" }, method = RequestMethod.PUT)
	public @ResponseBody String updateClientFollow(@RequestBody ClientFollow obj,HttpServletRequest req)
	{
		System.out.println("ClientFollowController.updateClientFollow() request:" + JsonUtils.objectToJson(obj));

		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		obj.setOpUserId(loginUserId);
		//2-原有信息
		ClientFollow oldClientFollow = this.clientFollowService.find(obj.getClientFollowId());
		if(oldClientFollow==null)
			return "客户跟进信息不存在!";
		//3-修改信息
		long opUserId = BaseController.getLoginUser(req).getUserId();
		obj.setOpUserId(opUserId);
		ClientFollow oldObj=this.clientFollowService.find(obj.getClientFollowId());
		if(StringUtils.isNullOrEmpty(obj.getFileName())==true)
			obj.setFileName(oldObj.getFileName());
		String msg=clientFollowService.updateClientFollow(obj);
		System.out.println("ClientFollowController.updateClientFollow() response:" + msg);
		return msg;
	}

	//文件上传


	@RequestMapping(value = { "/clientFollow/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteClientFollow(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=clientFollowService.deleteClientFollow(id);
		return msg;
	}
	
}
