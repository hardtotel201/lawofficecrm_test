package lj.controller.lawcase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lj.controller.BaseController;
import lj.model.client.ClientInfo;
import lj.model.lawcase.CaseInfo;
import lj.model.lawcase.CaseInfoDownload;
import lj.model.lawyer.LawyerInfo;
import lj.model.user.UserInfo;
import lj.service.client.ClientInfoService;
import lj.service.lawcase.CaseInfoService;
import lj.service.lawyer.LawyerInfoService;
import lj.service.user.UserInfoService;
import lj.util.DatatablesReturnObject;
import lj.util.JsonUtils;

/**
 * 案件信息控制器
 * @author samlvThinkpad
 *http://127.0.0.1:8081/lawofficecrm/lawcase/caseInfo/
 */
@Controller
@RequestMapping("lawcase")
public class CaseInfoController extends BaseController {

	@Autowired
	private CaseInfoService caseInfoService;

	@Autowired
	private ClientInfoService clientInfoService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private LawyerInfoService lawyerInfoService;
	
	/**
	 * 进入收案登记页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/caseInfoPage" }, method = RequestMethod.GET)
	public String caseInfoPage(Model model,HttpServletRequest req) {
		UserInfo loginUser=BaseController.getLoginUser(req);
		UserInfo[] userInfos = userInfoService.findAllProgrammer();
		ClientInfo[] clientInfos = clientInfoService.findAll();
		LawyerInfo[] lawyerInfos = lawyerInfoService.findAllLawyers();
		model.addAttribute("userInfos", userInfos);
		model.addAttribute("clientInfos", clientInfos);
		model.addAttribute("lawyerInfos", lawyerInfos);
		String[] caseTypes=this.caseInfoService.getCaseTypes();
		model.addAttribute("caseTypes", caseTypes);
		return "lawcase/caseInfoPage";
	}

	/**
	 * 分页查询
	 * @param requestModel
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/caseInfo" }, method = RequestMethod.GET)
	public @ResponseBody DatatablesReturnObject<CaseInfo> findAllPaged(CaseInfo requestModel,HttpServletRequest req) {
		System.out.println("CaseInfoController.findAllPaged() request:" + JsonUtils.objectToJson(requestModel));
		// 1-设置分页
		int pageNum=requestModel.getStart()/requestModel.getLength()+1;
		//1.先把前端传过来的值通过requestModel对象获取到；
		//2.把对应的值，去访问数据库进行查询
		//3.把数据返回
		PageHelper.startPage(pageNum, requestModel.getLength());
		// 2-获得查询数据
		Page<CaseInfo> page = caseInfoService.findAllPagedCaseInfo(requestModel);
		// 3-生成返回格式
		DatatablesReturnObject<CaseInfo> obj = new DatatablesReturnObject<CaseInfo>(
				requestModel.getDraw(),page.getTotal(),page);
		return obj;
	}
    
	/**
	 * 新增
	 * @param caseInfo
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/caseInfo" }, method = RequestMethod.POST)
	public @ResponseBody String insertCaseInfo(@RequestBody CaseInfo caseInfo,HttpServletRequest req) {
		System.out.println("caseInfoController.postCaseInfo() request:" + JsonUtils.objectToJson(caseInfo));
		//1-设置后端设置属性
		long loginUserId=this.getLoginUserId(req);
		caseInfo.setOpUserId(loginUserId);
		caseInfo.setCaseState(CaseInfoService.CASE_STATE_NEW);
		
		//2-新增案件属性
		String msg = caseInfoService.insertCaseInfo(caseInfo);
		System.out.println("CaseInfoController.insertCaseInfo() response:" + msg);
		return msg;

	}
	
	/**
	 * 更新基础信息
	 * @param obj
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/caseInfo" }, method = RequestMethod.PUT)
	public @ResponseBody String updateCaseInfo(@RequestBody CaseInfo obj,HttpServletRequest req) {
		System.out.println("CaseInfoController.updateCaseInfo() request:" + JsonUtils.objectToJson(obj));
		//1-查询案件信息
		CaseInfo oldObj=this.caseInfoService.find(obj.getCaseId());
		//2-设置操作用户和操作时间
		long loginUserId=this.getLoginUserId(req);
		oldObj.setOpUserId(loginUserId);
		oldObj.setOpTime(this.caseInfoService.getNowTime());
		//3-设置客户信息、案件编号、案件名称、案件类型、委托内容、案件来源和接收时间
		oldObj.setClientId(obj.getClientId());
		oldObj.setCaseCode(obj.getCaseCode());
		oldObj.setCaseName(obj.getCaseName());
		oldObj.setCaseType(obj.getCaseType());
		oldObj.setCaseConent(obj.getCaseConent());
		oldObj.setCaseSource(obj.getCaseSource());
		oldObj.setAcceptTime(obj.getAcceptTime());
		oldObj.setCaseProgram(obj.getCaseProgram());
		oldObj.setCasePhrase(obj.getCasePhrase());
		oldObj.setOtherClient(obj.getOtherClient());
		oldObj.setPredealMatters(obj.getPredealMatters());
		oldObj.setDealJudge(obj.getDealJudge());
		oldObj.setDealUnit(obj.getDealUnit());
		//3-更新
		String msg=caseInfoService.updateCaseInfo(oldObj);
		System.out.println("CaseInfoController.updateCaseInfo() response:" + msg);
		return msg;
	}
	
	/**
	 * 更新方案信息
	 * @param obj
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/casePlan" }, method = RequestMethod.PUT)
	public @ResponseBody String updateCasePlan(@RequestBody CaseInfo obj,HttpServletRequest req) {
		System.out.println("CaseInfoController.updateCasePlan() request:" + JsonUtils.objectToJson(obj));
		//1-查询案件信息
		CaseInfo oldObj=this.caseInfoService.find(obj.getCaseId());
		//2-设置程序员、律师和合同编号
		oldObj.setUserId(obj.getUserId());
		oldObj.setLawyerId(obj.getLawyerId());
		oldObj.setContractCode(obj.getContractCode());
		//3-设置操作用户和操作时间
		long loginUserId=this.getLoginUserId(req);
		oldObj.setOpUserId(loginUserId);
		oldObj.setOpTime(this.caseInfoService.getNowTime());
		//4-更新
		String msg=caseInfoService.updateCaseInfo(oldObj);
		System.out.println("CaseInfoController.updateCasePlan() response:" + msg);
		return msg;
	}
	
	/**
	 * 更新价格信息
	 * @param obj
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/casePrice" }, method = RequestMethod.PUT)
	public @ResponseBody String updateCasePrice(@RequestBody CaseInfo obj,HttpServletRequest req) {
		System.out.println("CaseInfoController.updateCasePrice() request:" + JsonUtils.objectToJson(obj));
		//0-查询案件信息
		CaseInfo oldObj=this.caseInfoService.find(obj.getCaseId());
		//2-设置案件标的、律师费、收费方式和支付方式
		oldObj.setCasePrice(obj.getCasePrice());
		oldObj.setLawyerFee(obj.getLawyerFee());
		oldObj.setFeeType(obj.getFeeType());
		oldObj.setPayType(obj.getPayType());
		//1-设置操作用户和操作时间
		long loginUserId=this.getLoginUserId(req);
		oldObj.setOpUserId(loginUserId);
		oldObj.setOpTime(this.caseInfoService.getNowTime());
		//2-更新
		String msg=caseInfoService.updateCaseInfo(oldObj);
		System.out.println("CaseInfoController.updateCasePrice() response:" + msg);
		return msg;
	}
	
	/**
	 * 确定项目
	 * @param obj
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/caseEnsure" }, method = RequestMethod.PUT)
	public @ResponseBody String caseCaseEnsure(@RequestBody CaseInfo obj,HttpServletRequest req) {
		System.out.println("CaseInfoController.caseEnsure() request:" + JsonUtils.objectToJson(obj));
		//0-查询案件信息
		CaseInfo oldObj=this.caseInfoService.find(obj.getCaseId());
		//2-设置案件状态
		oldObj.setCaseState(CaseInfoService.CASE_STATE_ENSURE);
		//1-设置操作用户和操作时间
		long loginUserId=this.getLoginUserId(req);
		oldObj.setOpUserId(loginUserId);
		oldObj.setOpTime(this.caseInfoService.getNowTime());
		//2-更新
		String msg=caseInfoService.updateCaseInfo(oldObj);
		System.out.println("CaseInfoController.caseEnsure() response:" + msg);
		return msg;
	}

	/**
	 * 导出Excel
	 */
	@RequestMapping(value = { "/caseInfoDownload" }, method = RequestMethod.POST)
	public void exportDataToExcel(@RequestBody CaseInfo obj,HttpServletResponse response)throws IOException {
		Page<CaseInfo> result = caseInfoService.findAllPagedCaseInfo(obj);
		List<CaseInfo> all = result.getResult();
		List<CaseInfoDownload> downloadList = new ArrayList<>();
		for(CaseInfo ci : all){
			CaseInfo caseInfo=this.caseInfoService.find(ci.getCaseId());
			CaseInfoDownload caseInfoDownload = new CaseInfoDownload();
			BeanUtils.copyProperties(caseInfo,caseInfoDownload);
			downloadList.add(caseInfoDownload);
			//System.out.println(downloadList);
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setCharacterEncoding("utf-8");
		// 这里URLEncoder.encode可以防止中文乱码
		String fileName = URLEncoder.encode("收案登记信息", "UTF-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
		EasyExcel.write(response.getOutputStream(), CaseInfoDownload.class).sheet("模板").doWrite(downloadList);
	}


	/**
	 * 删除
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/caseInfo/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody String deleteCaseInfo(@PathVariable Long id,HttpServletRequest req)
	{
		String msg=caseInfoService.deleteCaseInfo(id);
		return msg;
	}
}
