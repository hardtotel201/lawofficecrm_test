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

import lj.model.survey.SurveyQuestionnaire;
import lj.service.survey.SurveyQuestionnaireService;
import lj.util.DatatablesReturnObject;
import lj.controller.BaseController;

/**
 * 调研问卷控制器
 * @author samlvThinkpad
 *
 */
@Controller
@RequestMapping("survey")
public class SurveyQuestionnaireController extends BaseController {
	@Autowired
	private SurveyQuestionnaireService surveyQuestionnaireService;

	/**
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/surveyQuestionnairePage" }, method = RequestMethod.GET)
	public String surveyQuestionnairePage(Model model,HttpServletRequest req) {
		return "survey/surveyQuestionnairePage";
	}
	
	/**
	 * 进入调研问卷页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/surveyQuestionnaireQueryPage" }, method = RequestMethod.GET)
	public String surveyQuestionnaireQueryPage(Model model,HttpServletRequest req) {
		return "survey/surveyQuestionnaireQueryPage";
	}
	
	/**
	 * 分页查询
	 * @param requestModel
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/surveyQuestionnaire" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<SurveyQuestionnaire> findAllPaged(SurveyQuestionnaire requestModel,HttpServletRequest req) {
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<SurveyQuestionnaire> page = surveyQuestionnaireService.findAllPagedSurveyQuestionnaire(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<SurveyQuestionnaire> obj = new DatatablesReturnObject<SurveyQuestionnaire>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
	
	@RequestMapping(value = { "/surveyQuestionnaire" }, method = RequestMethod.POST)
	public @ResponseBody String insertSurveyQuestionnaire(@RequestBody SurveyQuestionnaire obj,HttpServletRequest req)
	{
		Long retId=surveyQuestionnaireService.insertSurveyQuestionnaire(obj);
		if(retId>0)
			return "";
		else
			return "新增失败";
	}
	
	@RequestMapping(value = { "/surveyQuestionnaire" }, method = RequestMethod.PUT)
	public @ResponseBody String updateSurveyQuestionnaire(@RequestBody SurveyQuestionnaire obj,HttpServletRequest req)
	{
		String msg=surveyQuestionnaireService.updateSurveyQuestionnaire(obj);
		return msg;
	}
	
	@RequestMapping(value = { "/surveyQuestionnaire/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteSurveyQuestionnaire(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=surveyQuestionnaireService.deleteSurveyQuestionnaire(id);
		return msg;
	}
	
}
