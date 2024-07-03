package lj.controller.survey;

import javax.servlet.http.HttpServletRequest;

import lj.service.survey.SurveyQuestionService;
import lj.util.JsonUtils;
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

import lj.model.survey.SurveyInfo;
import lj.service.survey.SurveyInfoService;
import lj.util.DatatablesReturnObject;
import lj.controller.BaseController;
import lj.global.AppVar;

@Controller
@RequestMapping("survey")
public class SurveyInfoController extends BaseController {
	@Autowired
	private SurveyInfoService surveyInfoService;
	@Autowired
	private SurveyQuestionService surveyQuestionService;

	@RequestMapping(value = { "/surveyInfoPage" }, method = RequestMethod.GET)
	public String surveyInfoPage(Model model,HttpServletRequest req) {
		model.addAttribute("latestSurveryId",AppVar.latestSurveryId);
		return "survey/surveyInfoPage";
	}
	
	@RequestMapping(value = { "/surveyInfo" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<SurveyInfo> findAllPaged(SurveyInfo requestModel,HttpServletRequest req) {
		System.out.println("SurveyInfoController.findAllPaged() request:" + JsonUtils.objectToJson(requestModel));
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());


		// 2-获得查询数据
		Page<SurveyInfo> page = surveyInfoService.findAllPagedSurveyInfo(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<SurveyInfo> obj = new DatatablesReturnObject<SurveyInfo>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	@RequestMapping(value = { "/surveyInfo" }, method = RequestMethod.POST)
	public @ResponseBody String insertSurveyInfo(@RequestBody SurveyInfo surveyInfo,HttpServletRequest req)
	{
		System.out.println("surveyInfoController.postSurveyInfo() request:" + JsonUtils.objectToJson(surveyInfo));
		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		surveyInfo.setOpUserId(loginUserId);
		//2-新增用户
		String msg = surveyInfoService.insertSurveyInfo(surveyInfo);
		System.out.println("SurveyInfoController.insertSurveyInfo() response:" + msg);
		return msg;
		/*Long retId=surveyInfoService.insertSurveyInfo(obj);
		if(retId>0)
			return "";
		else
			return "新增失败";*/
	}
	
	@RequestMapping(value = { "/surveyInfo" }, method = RequestMethod.PUT)
	public @ResponseBody String updateSurveyInfo(@RequestBody SurveyInfo obj,HttpServletRequest req)
	{
		long loginUserId=this.getLoginUserId(req);
		obj.setOpUserId(loginUserId);
		String msg=surveyInfoService.updateSurveyInfo(obj);
		return msg;
	}
	
	@RequestMapping(value = { "/surveyInfo/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteSurveyInfo(@PathVariable Long id,HttpServletRequest req)
	{
		surveyQuestionService.deleteBySurveyId(id);
		String msg=surveyInfoService.deleteSurveyInfo(id);
		return msg;
	}
	
}
