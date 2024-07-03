package lj.controller.user;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lj.controller.BaseController;
import lj.global.AppConst;
import lj.model.client.ClientInfo;
import lj.model.sum.SumByString;
import lj.service.client.ClientInfoService;
import lj.service.sum.SumClientService;

/**
 * 用户看板控制器
 * @author samlvThinkpad
 *
 */
@Controller
@RequestMapping("user")
public class DashboardController extends BaseController {
	
	@Autowired
	private ClientInfoService clientInfoService=null;
	
	@Autowired
	private SumClientService sumClientService=null;
	
    /**
     * 进入用户看板页面
     * @param model
     * @param req
     * @return
     */
	@RequestMapping(value = { "/dashboard" })
	public String dashboard(Model model,HttpServletRequest req) {
		System.out.println("dashboard is requested");
		//0-根据用户角色,设置是否右连接ClientAssign表
		boolean isJoin=this.getLoginUser(req).isAdmin()==false;
		ClientInfo requestModel=new ClientInfo();
		requestModel.setIsRightJoinClientAssign(isJoin);
		long loginUserId=this.getLoginUserId(req);
		requestModel.setLoginUserId(loginUserId);
		
		// 2-分页查询数据
		//2.1-查询待跟进
		requestModel.setSortField("registerTime");
		requestModel.setSortType("desc");
		requestModel.setIsQueryMonthNew(true);
		requestModel.setIsQueryMonthTransacted(false);
		requestModel.setIsQueryUnrevisit(false);
		System.out.println("DashboardController.dashboard() requestModel:"+lj.util.JsonUtils.objectToJson(requestModel));
		PageHelper.startPage(1, AppConst.PAGE_SIZE);
		Page<ClientInfo> pageMonthNew = clientInfoService.findAllPagedClientInfo(requestModel);
		while(pageMonthNew.size()<AppConst.PAGE_SIZE)
			pageMonthNew.add(new ClientInfo());
		model.addAttribute("pageMonthNew",pageMonthNew);
		model.addAttribute("pageMonthNewCount",pageMonthNew.getTotal());
	    System.out.println("DashboardController.dashboard() pageMonthNew:"+pageMonthNew.getTotal()+" "+lj.util.JsonUtils.objectToJson(pageMonthNew));
	    //2.2-查询30天未跟进
	    requestModel.setSortField("registerTime");
		requestModel.setSortType("desc");
		requestModel.setIsQueryMonthNew(false);
		requestModel.setIsQueryMonthTransacted(true);
		requestModel.setIsQueryUnrevisit(false);
		System.out.println("DashboardController.dashboard() requestModel:"+lj.util.JsonUtils.objectToJson(requestModel));
		PageHelper.startPage(1, AppConst.PAGE_SIZE);
		Page<ClientInfo> pageMonthTransacted = clientInfoService.findAllPagedClientInfo(requestModel);
		while(pageMonthTransacted.size()<AppConst.PAGE_SIZE)
			pageMonthTransacted.add(new ClientInfo());
		model.addAttribute("pageMonthTransacted",pageMonthTransacted);
		model.addAttribute("pageMonthTransactedCount",pageMonthTransacted.getTotal());
		System.out.println("DashboardController.dashboard() pageMonthTransacted:"+pageMonthTransacted.getTotal()+" "+lj.util.JsonUtils.objectToJson(pageMonthTransacted));
		//2.3-查询待回访
	    requestModel.setSortField("registerTime");
		requestModel.setSortType("desc");
		requestModel.setIsQueryMonthNew(false);
		requestModel.setIsQueryMonthTransacted(false);
		requestModel.setIsQueryUnrevisit(true);
		System.out.println("DashboardController.dashboard() requestModel:"+lj.util.JsonUtils.objectToJson(requestModel));
		PageHelper.startPage(1, AppConst.PAGE_SIZE);
		Page<ClientInfo> pageUnrevisit = clientInfoService.findAllPagedClientInfo(requestModel);
		while(pageUnrevisit.size()<AppConst.PAGE_SIZE)
			pageUnrevisit.add(new ClientInfo());
		model.addAttribute("pageUnrevisit",pageUnrevisit);
		model.addAttribute("pageUnrevisitCount",pageUnrevisit.getTotal());
		System.out.println("DashboardController.dashboard() pageUnrevisit:"+pageUnrevisit.getTotal()+" "+lj.util.JsonUtils.objectToJson(pageUnrevisit));
		return "user/dashboard";
	}
	
	@RequestMapping(value = { "/sumClientsByMonth" })
	public @ResponseBody List<SumByString> sumClientsByMonth(HttpServletRequest req,Model model) {
		//0-根据用户角色,设置是否右连接ClientAssign表
		boolean isJoin=this.getLoginUser(req).isAdmin()==false;
		ClientInfo requestModel=new ClientInfo();
		requestModel.setIsRightJoinClientAssign(isJoin);
		long loginUserId=this.getLoginUserId(req);
		requestModel.setLoginUserId(loginUserId);
				
		Date timeEnd=this.clientInfoService.getNowTime();
		Calendar calendarBegin=Calendar.getInstance();
		calendarBegin.setTime(timeEnd);
		calendarBegin.add(Calendar.MONTH, -12);
		requestModel.setRegisterTimeBegin(calendarBegin.getTime());
		requestModel.setRegisterTimeEnd(timeEnd);
	    List<SumByString> objs=this.sumClientService.sumClients(requestModel);
	    return objs;
	}
	
}
