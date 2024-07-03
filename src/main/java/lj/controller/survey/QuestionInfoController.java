package lj.controller.survey;

import javax.servlet.http.HttpServletRequest;

import lj.service.survey.QuestionItemService;
import lj.service.survey.SurveyQuestionService;
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

import lj.model.survey.QuestionInfo;
import lj.service.survey.QuestionInfoService;
import lj.util.DatatablesReturnObject;
import lj.util.LogUtils;
import lj.controller.BaseController;

@Controller
@RequestMapping("survey")
public class QuestionInfoController extends BaseController {
	@Autowired
	private QuestionInfoService questionInfoService;
	@Autowired
	private SurveyQuestionService surveyQuestionService;
	@Autowired
	private QuestionItemService questionItemService;

	@RequestMapping(value = { "/questionInfoPage" }, method = RequestMethod.GET)
	public String questionInfoPage(Model model,HttpServletRequest req) {
		return "survey/questionInfoPage";
	}
	
	@RequestMapping(value = { "/questionInfo" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<QuestionInfo> findAllPaged(QuestionInfo requestModel,HttpServletRequest req) {
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<QuestionInfo> page = questionInfoService.findAllPagedQuestionInfo(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<QuestionInfo> obj = new DatatablesReturnObject<QuestionInfo>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	/**
	 * 新增
	 * @param questionInfo
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/questionInfo" }, method = RequestMethod.POST)
	public @ResponseBody String insertQuestionInfo(@RequestBody QuestionInfo questionInfo,HttpServletRequest req)
	{	
		//2-新增问题信息
		long loginUserId=this.getLoginUserId(req);
		questionInfo.setOpUserId(loginUserId);
		String msg = questionInfoService.insertQuestionInfo(questionInfo);
		System.out.println("QuestionInfoController.insertQuestionInfo() response:" + msg);
		return msg;
		/*Long retId=questionInfoService.insertQuestionInfo(obj);
		if(retId>0)
			return "";
		else
			return "新增失败";*/
	}
	
	/**
	 * 修改问题信息
	 * @param obj
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/questionInfo" }, method = RequestMethod.PUT)
	public @ResponseBody String updateQuestionInfo(@RequestBody QuestionInfo obj,HttpServletRequest req)
	{
		long loginUserId=this.getLoginUserId(req);
		obj.setOpUserId(loginUserId);
		String msg=questionInfoService.updateQuestionInfo(obj);
		return msg;
	}
	
	/**
	 * 删除问题信息
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/questionInfo/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteQuestionInfo(@PathVariable Long id,HttpServletRequest req)
	{
		LogUtils.logInfoAndConsole("QuestionInfoController.deleteQuestionInfo() id:"+id);
		String msg=questionInfoService.deleteQuestionInfo(id);
		return msg;
	}
	
}
