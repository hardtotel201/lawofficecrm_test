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

import lj.model.sys.LogInfo;
import lj.service.sys.LogInfoService;
import lj.util.DatatablesReturnObject;
import lj.controller.BaseController;

@Controller
@RequestMapping("sys")
public class LogInfoController extends BaseController {
	@Autowired
	private LogInfoService logInfoService;

	@RequestMapping(value = { "/logInfoPage" }, method = RequestMethod.GET)
	public String logInfoPage(Model model,HttpServletRequest req) {
		return "sys/logInfoPage";
	}
	
	@RequestMapping(value = { "/logInfo" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<LogInfo> findAllPaged(LogInfo requestModel,HttpServletRequest req) {
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<LogInfo> page = logInfoService.findAllPagedLogInfo(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<LogInfo> obj = new DatatablesReturnObject<LogInfo>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	@RequestMapping(value = { "/logInfo" }, method = RequestMethod.POST)
	public @ResponseBody String insertLogInfo(@RequestBody LogInfo obj,HttpServletRequest req)
	{
		Long retId=logInfoService.insertLogInfo(obj);
		if(retId>0)
			return "";
		else
			return "新增失败";
	}
	
	@RequestMapping(value = { "/logInfo" }, method = RequestMethod.PUT)
	public @ResponseBody String updateLogInfo(@RequestBody LogInfo obj,HttpServletRequest req)
	{
		String msg=logInfoService.updateLogInfo(obj);
		return msg;
	}
	
	@RequestMapping(value = { "/logInfo/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteLogInfo(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=logInfoService.deleteLogInfo(id);
		return msg;
	}
	
}
