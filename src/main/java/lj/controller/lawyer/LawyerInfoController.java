package lj.controller.lawyer;

import javax.servlet.http.HttpServletRequest;
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

import lj.model.client.ClientInfo;
import lj.model.lawyer.LawyerInfo;
import lj.model.user.UserInfo;
import lj.service.lawyer.LawyerInfoService;
import lj.service.user.UserRoleService;
import lj.util.DatatablesReturnObject;
import lj.util.JsonUtils;
import lj.controller.BaseController;
/**
 * 客户信息控制器
 * @author samlvThinkpad
 *
 */
@Controller
@RequestMapping("lawyer")
public class LawyerInfoController extends BaseController {
	@Autowired
	private LawyerInfoService lawyerInfoService;


	/**
	 * 进入基本信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/lawyerInfoPage" }, method = RequestMethod.GET)
	public String lawyerInfoPage(Model model,HttpServletRequest req) {
		String[] lawyerStates=this.lawyerInfoService.getLawyerStates();
		model.addAttribute("lawyerStates",lawyerStates);
		return "lawyer/lawyerInfoPage";
	}

	/**
	 * 进入基本信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/lawyerCasePage" }, method = RequestMethod.GET)
	public String lawyerCasePage(Model model,HttpServletRequest req) {
		return "lawyer/lawyerCasePage";
	}


	
	/**
	 * 分页查询用户信息
	 * @param requestModel
	 * @return
	 */
	@RequestMapping(value = { "/lawyerInfo" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<LawyerInfo> findAllPaged(LawyerInfo requestModel,HttpServletRequest req) {
		System.out.println("LawyerInfoController.findAllPaged() request:" + JsonUtils.objectToJson(requestModel));
		
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<LawyerInfo> page = lawyerInfoService.findAllPagedLawyerInfo(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<LawyerInfo> obj = new DatatablesReturnObject<LawyerInfo>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	@RequestMapping(value = { "/lawyerInfo" }, method = RequestMethod.POST)
	public @ResponseBody String insertLawyerInfo(@RequestBody LawyerInfo lawyerInfo,HttpServletRequest req)
	{
		System.out.println("lawyerInfoController.insertLawyerInfo() request:" + JsonUtils.objectToJson(lawyerInfo));
		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		lawyerInfo.setOpUserId(loginUserId);
		lawyerInfo.setOpTime(this.lawyerInfoService.getNowTime());
		//2-新增用户
		String msg = lawyerInfoService.insertLawyerInfo(lawyerInfo);
		System.out.println("LawyerInfoController.postUserInfo() response:" + msg);
		return msg;
//		String retId=lawyerInfoService.insertLawyerInfo(obj);
//		if(retId>0)
//			return "";
//		else
//			return "新增失败";
		
	}
	
	@RequestMapping(value = { "/lawyerInfo" }, method = RequestMethod.PUT)
	public @ResponseBody String updateLawyerInfo(@RequestBody LawyerInfo obj,HttpServletRequest req)
	{
		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		obj.setOpUserId(loginUserId);
		obj.setOpTime(this.lawyerInfoService.getNowTime());
		//2-更新律师信息
		String msg=lawyerInfoService.updateLawyerInfo(obj);
		return msg;
	}
	
	@RequestMapping(value = { "/lawyerInfo/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteLawyerInfo(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=lawyerInfoService.deleteLawyerInfo(id);
		return msg;
	}
	
	
}
