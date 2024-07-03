package lj.controller.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lj.model.client.ClientInfo;
import lj.model.user.RoleInfo;
import lj.model.user.UserInfo;
import lj.service.user.UserInfoService;
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

import lj.model.client.ClientAssign;
import lj.service.client.ClientAssignService;
import lj.util.DatatablesReturnObject;
import lj.controller.BaseController;

/**
 * 客户分配控制器
 * @author samlvThinkpad
 *
 */
@Controller
@RequestMapping("client")
public class ClientAssignController extends BaseController {

	@Autowired
	private UserInfoService userInfoService = null;

	@Autowired
	private ClientInfoService clientInfoService = null;

	@Autowired
	private ClientAssignService clientAssignService;

	@RequestMapping(value = { "/clientAssignPage","/clientAssignPage/{clientId}" }, method = RequestMethod.GET)
	public String clientAssignPage(@PathVariable(required=false) Long clientId,Model model,HttpServletRequest req) {
		System.out.println("ClientAssignController.clientAssignPage() request:"+clientId);
		//1-如果包含路径参数查询客户信息
		if(clientId!=null && clientId>0)
		{
			ClientInfo clientInfo=this.clientInfoService.find(clientId);
			if(clientInfo!=null)
				model.addAttribute("clientInfo",clientInfo);
		}
		UserInfo[] userInfos = userInfoService.findAllUsers();
		model.addAttribute("userInfos", userInfos);
		//暂时逻辑可以分配所有(后面可能需要修改)
		ClientInfo[] clientInfos=this.clientInfoService.findAll();
		model.addAttribute("clientInfos", clientInfos);
		return "client/clientAssignPage";
	}
	
	/**
	 * 分页查询客户分配信息
	 * @param requestModel
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/clientAssign" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<ClientAssign> findAllPaged(ClientAssign requestModel,HttpServletRequest req) {
		System.out.println("ClientAssignController.findAllPaged() request:" + JsonUtils.objectToJson(requestModel));
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<ClientAssign> page = clientAssignService.findAllPagedClientAssign(requestModel);
		//2.1-填充视图字段clientId->clientIds
		page.getResult().forEach(obj->{
			obj.setClientIds(""+obj.getClientId());
		});
		// 3-生成返回格式
		DatatablesReturnObject<ClientAssign> obj = new DatatablesReturnObject<ClientAssign>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	/**
	 * 新增客户分配信息
	 * @param newClientAssign
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/clientAssign" }, method = RequestMethod.POST)
	public @ResponseBody String insertClientAssign(@RequestBody ClientAssign newClientAssign,HttpServletRequest req)
	{
		System.out.println("ClientAssignController.insertClientAssign() request:" + JsonUtils.objectToJson(newClientAssign));
		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		newClientAssign.setAssignUserId(loginUserId);
		//2-新增客户分配信息
		String msg="";
		if(StringUtils.isNullOrEmpty(newClientAssign.getClientIds())==false) {
			//2.1-分割提交客户标识字符串
			String[] clientIds=newClientAssign.getClientIds().split(";");
			//2.2-逐个遍历客户分配信息
			for(String strClientId:clientIds) {
				//2.2.1-使用被分配用户标识和被分配客户标识查询分配信息
				long clientId=Long.parseLong(strClientId);
				newClientAssign.setClientId(clientId);
				List<ClientAssign> objs=this.clientAssignService.findAllByUserIdAndClientId(newClientAssign.getUserId(),clientId);
				//2.2.2-被分配信息不存在，则新增分配信息
				if(objs==null || objs.size()<=0)
					msg=msg+clientAssignService.insertClientAssign(newClientAssign);
			}
		}
		System.out.println("ClientAssignController.insertClientAssign() response:" + msg);
		return msg;
	}
	
	/**
	 * 修改客户分配信息
	 * @param obj
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/clientAssign" }, method = RequestMethod.PUT)
	public @ResponseBody String updateClientAssign(@RequestBody ClientAssign obj,HttpServletRequest req)
	{
		System.out.println("ClientAssignController.updateClientAssign() request:" + JsonUtils.objectToJson(obj));
		//0-查找老的客户分配信息
		ClientAssign oldClientAssign = this.clientAssignService.find(obj.getClientAssignId());
		if (oldClientAssign==null)
			return "用户分配不存在";
		//1-设置分配用户标识
		long loginUserId=this.getLoginUserId(req);
		obj.setAssignUserId(loginUserId);
		//2-删除老的分配信息
		String msg=this.clientAssignService.deleteClientAssign(obj.getClientAssignId());
		//3-新增分配信息
		if(StringUtils.isNullOrEmpty(obj.getClientIds())==false) {
			//2.1-分割提交客户标识字符串
			String[] clientIds=obj.getClientIds().split(";");
			//2.2-逐个遍历客户分配信息
			for(String strClientId:clientIds) {
				//2.2.1-使用被分配用户标识和被分配客户标识查询分配信息
				long clientId=Long.parseLong(strClientId);
				obj.setClientId(clientId);
				List<ClientAssign> objs=this.clientAssignService.findAllByUserIdAndClientId(obj.getUserId(),clientId);
				//2.2.2-被分配信息不存在，则新增分配信息
				if(objs==null || objs.size()<=0)
					msg=msg+clientAssignService.insertClientAssign(obj);
			}
		}
		return msg;
	}
	
	@RequestMapping(value = { "/clientAssign/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteClientAssign(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=clientAssignService.deleteClientAssign(id);
		return msg;
	}
	
}
