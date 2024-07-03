package lj.controller.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lj.model.client.*;
import lj.model.client.ClientRevisit;
import lj.model.user.UserInfo;
import lj.service.client.ClientInfoService;
import lj.util.JsonUtils;
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

import lj.model.client.ClientRevisit;
import lj.service.client.ClientRevisitService;
import lj.util.DatatablesReturnObject;
import lj.controller.BaseController;

/**
 * 客户回访控制器
 * @author samlvThinkpad
 *
 */
@Controller
@RequestMapping("client")
public class ClientRevisitController extends BaseController {
	@Autowired
	private ClientRevisitService clientRevisitService;

	@Autowired
	private ClientInfoService clientInfoService;

	@RequestMapping(value = {"/clientRevisitPage", "/clientRevisitPage/{clientId}" }, method = RequestMethod.GET)
	public String clientRevisitPage(Model model,@PathVariable(required=false)  Long clientId,HttpServletRequest req) {
		System.out.println("ClientRevisitController.clientRevisitPage() request:"+clientId);
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
		List<ClientInfo> clientInfos = this.clientInfoService.findAllByRequestObj(requestModel);	
		model.addAttribute("clientInfos", clientInfos);
		return "client/clientRevisitPage";
	}
	
	@RequestMapping(value = { "/clientRevisit" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<ClientRevisit> findAllPaged(ClientRevisit requestModel,HttpServletRequest req) {
		System.out.println("ClientRevisitController.findAllPaged() request:" + JsonUtils.objectToJson(requestModel));
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<ClientRevisit> page = clientRevisitService.findAllPagedClientRevisit(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<ClientRevisit> obj = new DatatablesReturnObject<ClientRevisit>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	@RequestMapping(value = { "/clientRevisit" }, method = RequestMethod.POST)
	public @ResponseBody String insertClientRevisit(@RequestBody ClientRevisit newClientRevisit,HttpServletRequest req)
	{
		System.out.println("ClientRevisitController.insertClientRevisit() request:" + JsonUtils.objectToJson(newClientRevisit));

		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		newClientRevisit.setOpUserId(loginUserId);
		//2-新增客户跟进
		String msg = clientRevisitService.insertClientRevisit(newClientRevisit);
		System.out.println("ClientRevisitController.insertClientRevisit() response:" + msg);
		return msg;
	}
	
	@RequestMapping(value = { "/clientRevisit" }, method = RequestMethod.PUT)
	public @ResponseBody String updateClientRevisit(@RequestBody ClientRevisit obj,HttpServletRequest req)
	{
		System.out.println("ClientRevisitController.updateClientRevisit() request:" + JsonUtils.objectToJson(obj));

		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		obj.setOpUserId(loginUserId);
		//2-原有信息
		ClientRevisit oldClientRevisit = this.clientRevisitService.find(obj.getClientRevisitId());
		if(oldClientRevisit==null)
			return "客户回访信息不存在!";
		//3-修改信息
		long opUserId = BaseController.getLoginUser(req).getUserId();
		obj.setOpUserId(opUserId);
		ClientRevisit oldObj=this.clientRevisitService.find(obj.getClientRevisitId());
		if(StringUtils.isNullOrEmpty(obj.getFileName())==true)
			obj.setFileName(oldObj.getFileName());
		String msg=clientRevisitService.updateClientRevisit(obj);
		System.out.println("ClientRevisitController.updateClientRevisit() response:" + msg);
		return msg;
	}
	
	@RequestMapping(value = { "/clientRevisit/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteClientRevisit(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=clientRevisitService.deleteClientRevisit(id);
		return msg;
	}
	
}
