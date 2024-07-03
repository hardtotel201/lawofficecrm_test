package lj.controller.survey;

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
import lj.model.survey.QuestionInfo;
import lj.model.survey.QuestionItem;
import lj.service.survey.QuestionInfoService;
import lj.service.survey.QuestionItemService;
import lj.util.DatatablesReturnObject;

/**
 * 问题选择项控制器
 * @author samlvThinkpad
 *
 */
@Controller
@RequestMapping("survey")
public class QuestionItemController extends BaseController {
	@Autowired
	private QuestionItemService questionItemService;
	
	@Autowired
	private QuestionInfoService questionInfoService;

	/**
	 * 进入页面
	 * @param model
	 * @param questionId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/questionItemPage","/questionItemPage/{questionId}" }, method = RequestMethod.GET)
	public String questionItemPage(Model model,@PathVariable(required=false)  Long questionId,HttpServletRequest req) {
		System.out.println("QuestionItemService.questionItemPage() request:"+questionId);
		QuestionInfo questionInfo=this.questionInfoService.find(questionId);
		model.addAttribute("questionInfo", questionInfo);
		
		QuestionInfo[] questionInfos=this.questionInfoService.findAll();
		model.addAttribute("questionInfos", questionInfos);
		return "survey/questionItemPage";
	}
	
	@RequestMapping(value = { "/questionItem" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<QuestionItem> findAllPaged(QuestionItem requestModel,HttpServletRequest req) {
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<QuestionItem> page = questionItemService.findAllPagedQuestionItem(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<QuestionItem> obj = new DatatablesReturnObject<QuestionItem>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	@RequestMapping(value = { "/questionItem" }, method = RequestMethod.POST)
	public @ResponseBody String insertQuestionItem(@RequestBody QuestionItem obj,HttpServletRequest req)
	{
		Long retId=questionItemService.insertQuestionItem(obj);
		if(retId>0)
			return "";
		else
			return "新增失败";
	}
	
	@RequestMapping(value = { "/questionItem" }, method = RequestMethod.PUT)
	public @ResponseBody String updateQuestionItem(@RequestBody QuestionItem obj,HttpServletRequest req)
	{
		Long questionId = (Long) req.getSession()
									.getAttribute("questionId");
		obj.setQuestionId(questionId);
		String msg=questionItemService.updateQuestionItem(obj);
		return msg;
	}
	
	@RequestMapping(value = { "/questionItem/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteQuestionItem(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=questionItemService.deleteQuestionItem(id);
		return msg;
	}
	
}
