package lj.controller.lawyer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lj.model.lawcase.CaseEvaluate;
import lj.service.lawcase.CaseEvaluateService;
import lj.util.DatatablesReturnObject;

@Controller
@RequestMapping("lawyer")
public class LawyerEvaluateController {
	@Autowired
	private CaseEvaluateService caseEvaluateService;
	
	
	/**
	 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/lawyerEvaluationPage" }, method = RequestMethod.GET)
	public String lawyerEvaluationPage(Model model,HttpServletRequest req) {
		return "lawyer/lawyerEvaluationPage";
	}
	
	/**
	 * 分页查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/lawyerEvaluate" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<CaseEvaluate> findAllPaged(CaseEvaluate requestModel,HttpServletRequest req) {
		System.out.println("LawyerEvaluateController.findAllPaged() request:"+lj.util.JsonUtils.objectToJson(requestModel));
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<CaseEvaluate> page = caseEvaluateService.findAllLaywerPaged(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<CaseEvaluate> obj = new DatatablesReturnObject<CaseEvaluate>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
}
