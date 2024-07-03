package lj.controller.sys;

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

import lj.controller.BaseController;
import lj.model.sys.ParamInfo;
import lj.service.sys.ParamInfoService;
import lj.util.DatatablesReturnObject;

@Controller
@RequestMapping("sys")
public class ParamInfoController extends BaseController {
	@Autowired
	private ParamInfoService paramInfoService;

	@RequestMapping(value = { "/paramInfoPage" }, method = RequestMethod.GET)
	public String paramInfoPage(Model model,HttpServletRequest req) {
		String[] paramTypes=ParamInfo.getParamTypes();
		model.addAttribute("paramTypes", paramTypes);
		return "sys/paramInfoPage";
	}
	
	@RequestMapping(value = { "/paramInfo" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<ParamInfo> findAllPaged(ParamInfo requestModel,HttpServletRequest req) {
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<ParamInfo> page = paramInfoService.findAllPagedParamInfo(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<ParamInfo> obj = new DatatablesReturnObject<ParamInfo>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	@RequestMapping(value = { "/paramInfo" }, method = RequestMethod.POST)
	public @ResponseBody String insertParamInfo(@RequestBody ParamInfo obj,HttpServletRequest req)
	{
		long opUserId = BaseController.getLoginUser(req).getUserId();
		obj.setOpUserId(opUserId);
		obj.setOpTime(this.paramInfoService.getNowTime());
		Long retId=paramInfoService.insertParamInfo(obj);
		if(retId>0)
			return "";
		else
			return "新增失败";
	}
	
	@RequestMapping(value = { "/paramInfo" }, method = RequestMethod.PUT)
	public @ResponseBody String updateParamInfo(@RequestBody ParamInfo obj,HttpServletRequest req)
	{
		long opUserId = BaseController.getLoginUser(req).getUserId();
		obj.setOpUserId(opUserId);
		obj.setOpTime(this.paramInfoService.getNowTime());
		String msg=paramInfoService.updateParamInfo(obj);
		return msg;
	}
	
	@RequestMapping(value = { "/paramInfo/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteParamInfo(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=paramInfoService.deleteParamInfo(id);
		return msg;
	}
	
}
