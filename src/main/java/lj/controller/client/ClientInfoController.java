package lj.controller.client;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lj.controller.BaseController;
import lj.model.client.ClientAssign;
import lj.model.client.ClientInfo;
import lj.model.user.UserInfo;
import lj.service.client.ClientAssignService;
import lj.service.client.ClientInfoService;
import lj.service.user.UserInfoService;
import lj.util.DatatablesReturnObject;
import lj.util.JsonUtils;
import lj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 客户信息控制器
 * @author samlvThinkpad
 *
 */
@Controller
@RequestMapping("client")
public class ClientInfoController extends BaseController {
	private final static String REQUEST_PAGE_INDEX="requestPageIndex";

	@Autowired
	private ClientInfoService clientInfoService;
	
	@Autowired
	private ClientAssignService ClientAssignService;

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 进入基本信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/clientBasicPage","/clientBasicPage/{queryType}","/clientBasicPage/{queryType}/{requestPageIndex}"}, method = RequestMethod.GET)
	public String clientBasicPage(@PathVariable(required=false) String queryType,@PathVariable(required=false) Integer requestPageIndex,
			Model model,HttpServletRequest req) {
		System.out.println("ClientInfoController.clientBasicPage() queryType:"+queryType);
		if(StringUtils.isNullOrEmpty(queryType)==false)
			model.addAttribute("queryType",queryType);
		UserInfo[] userInfos = userInfoService.findAllUsers();
		model.addAttribute("userInfos", userInfos);
		model.addAttribute("clientStates", ClientInfoService.getClientStates());
		req.getSession().setAttribute(REQUEST_PAGE_INDEX, requestPageIndex);
		System.out.println("ClientInfoController.clientBasicPage() requestPageIndex:"+requestPageIndex);
		System.out.println("ClientInfoController.clientBasicPage() clientStates:"+ClientInfoService.getClientStates());
		return "client/clientBasicPage";
	}
	
	/**
	 * 进入扩展信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/clientExtendPage/{clientId}" }, method = RequestMethod.GET)
	public String clientExtendPage(Model model,@PathVariable long clientId) {
		System.out.println("ClientInfoController.clientExtendPage() request clientId:"+clientId);
		return "client/clientExtendPage";
	}
	
	
	/**
	 * 分页查询
	 * @param requestModel
	 * @return
	 */
	@RequestMapping(value = { "/clientInfo" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<ClientInfo> findAllPaged(ClientInfo requestModel,HttpServletRequest req,
			Model model) {
		System.out.println("ClientInfoController.findAllPaged() request:" + JsonUtils.objectToJson(requestModel));
		//0-根据用户角色,设置是否右连接ClientAssign表
		boolean isJoin=this.getLoginUser(req).isAdmin()==false;
		requestModel.setIsRightJoinClientAssign(isJoin);
		long loginUserId=this.getLoginUserId(req);
		requestModel.setLoginUserId(loginUserId);
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<ClientInfo> page = clientInfoService.findAllPagedClientInfo(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<ClientInfo> obj = new DatatablesReturnObject<ClientInfo>(
				requestModel.getDraw(),page.getTotal(),page);
		int requestPageIndex=page.getPageNum()<1?0:page.getPageNum()-1;
		req.getSession().setAttribute(REQUEST_PAGE_INDEX, requestPageIndex);
		System.out.println("ClientInfoController.findAllPaged() requestPageIndex:" +requestPageIndex);
		//System.out.println("ClientInfoController.findAllPaged() return:" + JsonUtils.objectToJson(obj));
		return obj;
	}
	
	/**
	 * 新增客户
	 * @param clientInfo
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/clientInfo" }, method = RequestMethod.POST)
	public @ResponseBody String insertClientInfo(@RequestBody ClientInfo clientInfo,HttpServletRequest req)
	{
		System.out.println("clientInfoController.insertClientInfo() request:" + JsonUtils.objectToJson(clientInfo));
		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		clientInfo.setOpUserId(loginUserId);
		//2-新增用户
		Date nowTime=this.clientInfoService.getNowTime();
		clientInfo.setOpTime(nowTime);
		clientInfo.setRegisterTime(nowTime);
		//判断SourceMemo是否为空
		if (StringUtils.isNullOrEmpty(clientInfo.getSourceMemo()))
		{
			clientInfo.setSourceMemo("web端录入");
		}
		String msg = clientInfoService.insertClientInfo(clientInfo);
		if(StringUtils.isNullOrEmpty(msg)==false)
			return msg;
		//3-如果不是管理员，直接分配给他
		ClientAssign newClientAssign=new ClientAssign(null,null,clientInfo.getClientId(),loginUserId,nowTime);
		this.ClientAssignService.insertClientAssign(newClientAssign);

		System.out.println("ClientInfoController.insertClientInfo() response:" + msg);
		return msg;

	}
	
	/**
	 * 更新客户端信息
	 * @param obj
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/clientInfo" }, method = RequestMethod.PUT)
	public @ResponseBody String updateClientInfo(@RequestBody ClientInfo obj,HttpServletRequest req)
	{
		System.out.println("clientInfoController.updateClientInfo() request:" + JsonUtils.objectToJson(obj));
		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		obj.setOpUserId(loginUserId);
		//2-原有信息
		ClientInfo oldClient = this.clientInfoService.find(obj.getClientId());
		if(oldClient==null)
			return "客户信息不存在!";
		obj.setRegisterTime(oldClient.getRegisterTime());
		//obj.setClientState(oldClient.getClientState());
		//3-修改信息
		long opUserId = BaseController.getLoginUser(req).getUserId();
		obj.setOpUserId(opUserId);
		String msg=clientInfoService.updateClientInfo(obj, opUserId);
		System.out.println("ClientInfoController.updateClientInfo() response:" + msg);
		return msg;
	}

	/**
	 * 导入
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/import")
	@ResponseBody
	public String importClientInfoExcel(@RequestPart(value = "file") MultipartFile file,HttpServletRequest req) {
		try {
			List<ClientInfo> cleintInfoList = EasyExcel.read(file.getInputStream())
					.head(ClientInfo.class)
					.headRowNumber(1 )
					.sheet()
					.doReadSync();
			int i = 1 ;
			StringBuilder insertFailedName = new StringBuilder();
			for (ClientInfo clientInfo: cleintInfoList) {
				System.out.println("clientInfoController.insertClientInfo() request:" + JsonUtils.objectToJson(clientInfo));
				//1-设置后端设置属性
				long loginUserId=this.getLoginUserId(req);
				clientInfo.setOpUserId(loginUserId);
				//2-新增用户
				Date nowTime=this.clientInfoService.getNowTime();
				clientInfo.setOpTime(nowTime);
				clientInfo.setRegisterTime(nowTime);
				if (clientInfo.getSourceMemo()==null)
				{
					clientInfo.setSourceMemo("excel录入");
				}
				String msg = clientInfoService.insertClientInfo(clientInfo);
				if (msg == "该名称的客户已经存在!"){

					insertFailedName.append(clientInfo.getClientName() +'，');
				}
				else {
					//3-如果能正常添加客户，添加客户分配信息
					Long AssignUserId = null;
					UserInfo user = userInfoService.findByUserName(clientInfo.getAssignUserName());
					if (user != null) {
						AssignUserId = user.getUserId();
					}
					ClientAssign newClientAssign = new ClientAssign(null, AssignUserId, clientInfo.getClientId(), loginUserId, nowTime);
					this.ClientAssignService.insertClientAssign(newClientAssign);
					System.out.println("ClientInfoController.updateClientInfo() response:" + msg);
				}
			}
			if (insertFailedName.length() > 0) {
				insertFailedName.deleteCharAt(insertFailedName.length() - 1);
			}
			// 批量插入
			clientInfoService.save(cleintInfoList);
			return insertFailedName.toString();
		} catch (IOException e) {
			return "导入失败";
		}
	}

//	/**
//	 * 数据导入-监听每一行数据
//	 * @param file
//	 * @return
//	 */
//	@PostMapping("/clientInfo/importListenerClientInfoExcel")
//	public Result importListenerClientInfoExcel(@RequestPart(value = "file") MultipartFile file) {
//		try {
//			EasyExcel.read(file.getInputStream(), ClientInfo.class, new ImportClientListener(clientInfoService)).sheet().doRead();
//			return Result.success("success");
//		} catch (IOException e) {
//			return Result.fail("导入失败");
//		}
//	}

	@RequestMapping(value = { "/clientInfo/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteClientInfo(@PathVariable Long id)
	{
		String msg=clientInfoService.deleteClientInfo(id);
		return msg;
	}

}
