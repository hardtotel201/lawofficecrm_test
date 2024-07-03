package lj.controller.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lj.controller.BaseController;
import lj.service.client.WechatService;
import lj.service.weixin.WxBaseService;

@Controller
@RequestMapping("client")
public class WechartController extends BaseController {
	
	@Autowired
	private WxBaseService wxService=null;
	
	@Autowired
	private WechatService wechatService=null;

	/**
	 * 进入生成微信二维码页面
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/wechartQImagePage" }, method = RequestMethod.GET)
	public String clientAssignPage(Model model,HttpServletRequest req) {
		List<String> locationNames=this.wechatService.getLocationNames();
		List<String> locationCodes=this.wechatService.getLocationCodes();
		for(int i=0;i<locationNames.size();++i) {
			System.out.println("WechartController.clientAssignPage():"+
					locationNames.get(i)+"_"+locationCodes.get(i));
		}
		model.addAttribute("locationNames",locationNames);
		model.addAttribute("locationCodes",locationNames);
		return "client/wechartQImagePage";
	}
	
	/**
	 * 生成微信二维码
	 * @param locationName
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/wechartQImage" }, method = RequestMethod.POST)
	public @ResponseBody String post(String locationName,Model model,HttpServletRequest req) {
		
		String locationCode=this.wechatService.findLocationCode(locationName);
		System.out.println("WechartController.post() request locationName:"+locationName);
		System.out.println("WechartController.post() request locationCode:"+locationCode);
		String fileName=this.wxService.createQRImgByLocation(locationCode);
		return fileName;
	}
	
}
