package lj.controller.lawcase;

import java.util.Date;

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
import lj.model.lawcase.CaseEvaluate;
import lj.model.lawcase.CaseInfo;
import lj.service.lawcase.CaseEvaluateService;
import lj.service.lawcase.CaseInfoService;
import lj.util.DatatablesReturnObject;


@Controller
@RequestMapping("lawcase")
public class CaseEvaluateController extends BaseController {
	@Autowired
	private CaseEvaluateService caseEvaluateService;
	
	@Autowired
	private CaseInfoService caseInfoService;

    /**
	 * 进入页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/caseEvaluatePage" }, method = RequestMethod.GET)
	public String caseEvaluatePage(Model model,HttpServletRequest req) {
		CaseInfo[] caseInfos=this.caseInfoService.findAll();
		model.addAttribute("caseInfos", caseInfos);
		
		String[] evaluateContents=CaseEvaluateService.getEvaluateContents();
		model.addAttribute("evaluateContents", evaluateContents);
		return "lawcase/caseEvaluatePage";
	}
	
	/**
	 * 分页查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/caseEvaluate" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<CaseEvaluate> findAllPaged(CaseEvaluate requestModel,HttpServletRequest req) {
	
		System.out.println("CaseEvaluateController.findAllPaged() request:"+lj.util.JsonUtils.objectToJson(requestModel));
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<CaseEvaluate> page = caseEvaluateService.findAllPagedCaseEvaluate(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<CaseEvaluate> obj = new DatatablesReturnObject<CaseEvaluate>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	/**
	 * 新增
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/caseEvaluate" }, method = RequestMethod.POST)
	public @ResponseBody String insertCaseEvaluate(@RequestBody CaseEvaluate obj,HttpServletRequest req)
	{
		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		obj.setOpUserId(loginUserId);
		Date nowTime=this.caseEvaluateService.getNowTime();
		obj.setOpTime(nowTime);
		obj.setEvaluateTime(nowTime);
		String msg=caseEvaluateService.insertCaseEvaluate(obj);
		return msg;
	}
	
	/**
	 * 修改
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/caseEvaluate" }, method = RequestMethod.PUT)
	public @ResponseBody String updateCaseEvaluate(@RequestBody CaseEvaluate obj,HttpServletRequest req)
	{
		CaseEvaluate oldUser=this.caseEvaluateService.find(obj.getCaseEvaluateId());
		obj.setEvaluateTime(oldUser.getEvaluateTime());
		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		obj.setOpUserId(loginUserId);
		Date nowTime=this.caseEvaluateService.getNowTime();
		obj.setOpTime(nowTime);
		String msg=caseEvaluateService.updateCaseEvaluate(obj);
		return msg;
	}
	
	/**
	 * 删除
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/caseEvaluate/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteCaseEvaluate(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=caseEvaluateService.deleteCaseEvaluate(id);
		return msg;
	}
	
}
