package lj.controller.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lj.model.client.ClientInfo;
import lj.model.user.UserInfo;
import lj.service.client.ClientInfoService;
import lj.util.JsonUtils;
import lj.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lj.model.client.ClientRelation;
import lj.service.client.ClientRelationService;
import lj.util.DatatablesReturnObject;
import lj.controller.BaseController;

/**
 * 客户关联控制器
 * @author samlvThinkpad
 *
 */
@Controller
@RequestMapping("client")
public class ClientRelationController extends BaseController {
	@Autowired
	private ClientRelationService clientRelationService;

	@Autowired
	private ClientInfoService clientInfoService = null;

	/**
	 * 进入客户关联页面
	 * @param model
	 * @param clientId
	 * @return
	 */
	@RequestMapping(value = { "/clientRelationPage","/clientRelationPage/{clientId}" }, method = RequestMethod.GET)
	public String clientRelationPage(Model model,@PathVariable(required=false)  Long clientId,HttpServletRequest req) {
		System.out.println("ClientRelationController.clientRelationPage() request:"+clientId);
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
		return "client/clientRelationPage";
	}

	/**
	 * 分页查询
	 * @param requestModel
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/clientRelation" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<ClientRelation> findAllPaged(ClientRelation requestModel,HttpServletRequest req) {
		System.out.println("ClientRelationController.findAllPaged() request:" + JsonUtils.objectToJson(requestModel));
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<ClientRelation> page = clientRelationService.findAllPagedClientRelation(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<ClientRelation> obj = new DatatablesReturnObject<ClientRelation>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	@RequestMapping(value = { "/clientRelation" }, method = RequestMethod.POST)
	public @ResponseBody String insertClientRelation(@RequestBody ClientRelation obj,HttpServletRequest req)
	{
		Long ret=clientRelationService.insertClientRelation(obj);
		if(ret>0)
			return "";
		else
			return "新增失败";
	}
	
	@RequestMapping(value = { "/clientRelation" }, method = RequestMethod.PUT)
	public @ResponseBody String updateClientRelation(@RequestBody ClientRelation obj,HttpServletRequest req)
	{
		String msg=clientRelationService.updateClientRelation(obj);
		return msg;
	}
	
	@RequestMapping(value = { "/clientRelation/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteClientRelation(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=clientRelationService.deleteClientRelation(id);
		return msg;
	}
	
}
