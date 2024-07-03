package lj.controller.survey;

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
import lj.global.AppVar;
import lj.model.survey.QuestionInfo;
import lj.model.survey.SurveyInfo;
import lj.model.survey.SurveyQuestion;
import lj.service.survey.QuestionInfoService;
import lj.service.survey.SurveyInfoService;
import lj.service.survey.SurveyQuestionService;
import lj.util.DatatablesReturnObject;

/**
 * 调研问题控制器
 * @author samlvThinkpad
 *
 */
@Controller
@RequestMapping("survey")
public class SurveyQuestionController extends BaseController {
	@Autowired
	private SurveyQuestionService surveyQuestionService;
	@Autowired
	private QuestionInfoService questionInfoService;
	@Autowired
	private SurveyInfoService surveyInfoService;

	/**
	 * 进入调研问题页面
	 * @param model
	 * @param surveyId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/surveyQuestionPage/{surveyId}" }, method = RequestMethod.GET)
	public String surveyQuestionPage(Model model,@PathVariable long surveyId,HttpServletRequest req) {
		System.out.println("SurveyQuestionController.surveyQuestionPage() request:"+surveyId);
		SurveyInfo surveyInfo=this.surveyInfoService.find(surveyId);
		model.addAttribute("surveyInfo",surveyInfo);
		QuestionInfo[] questionInfos = questionInfoService.findAll();
		model.addAttribute("questionInfos",questionInfos);
		SurveyInfo[] surveyInfos=this.surveyInfoService.findAll();
		model.addAttribute("surveyInfos",surveyInfos);
		model.addAttribute("latestSurveryId",AppVar.latestSurveryId);
		return "survey/surveyQuestionPage";
	}
	
	/**
	 * 分页查询
	 * @param requestModel
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/surveyQuestion" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<SurveyQuestion> findAllPaged(SurveyQuestion requestModel,HttpServletRequest req) {
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<SurveyQuestion> page = surveyQuestionService.findAllPagedSurveyQuestion(requestModel);
		
		// 3-生成返回格式
		DatatablesReturnObject<SurveyQuestion> obj = new DatatablesReturnObject<SurveyQuestion>(requestModel.getDraw(),page.getTotal(),page);
		System.out.println("SurveyQuestionController.findAllPaged() return:"+lj.util.JsonUtils.objectToJson(obj));
		return obj;
	}
	
	/**
	 * 新增
	 * @param obj
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/surveyQuestion" }, method = RequestMethod.POST)
	public @ResponseBody String insertSurveyQuestion(@RequestBody SurveyQuestion obj,HttpServletRequest req)
	{  
		System.out.println("SurveyQuestionController.insertSurveyQuestion() request:"+lj.util.JsonUtils.objectToJson(obj));
		//1-设置后端属性
		long loginUserId=this.getLoginUserId(req);
		obj.setOpUserId(loginUserId);
		Date nowTime=this.surveyInfoService.getNowTime();
		obj.setOpTime(nowTime);
		//2-新增
		String msg=surveyQuestionService.insertSurveyQuestion(obj);
		return msg;
	}
	
	/**
	 * 修改
	 * @param obj
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/surveyQuestion" }, method = RequestMethod.PUT)
	public @ResponseBody String updateSurveyQuestion(@RequestBody SurveyQuestion obj,HttpServletRequest req)
	{
		System.out.println("SurveyQuestionController.updateSurveyQuestion() request:"+lj.util.JsonUtils.objectToJson(obj));
		//1-设置后端属性
		long loginUserId=this.getLoginUserId(req);
		obj.setOpUserId(loginUserId);
		Date nowTime=this.surveyInfoService.getNowTime();
		obj.setOpTime(nowTime);
		//2-修改
		String msg=surveyQuestionService.updateSurveyQuestion(obj);
		return msg;
	}
	
	@RequestMapping(value = { "/surveyQuestion/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteSurveyQuestion(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=surveyQuestionService.deleteSurveyQuestion(id);
		return msg;
	}
	
}
