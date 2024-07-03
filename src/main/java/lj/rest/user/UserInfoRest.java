package lj.rest.user;

import lj.model.client.ClientInfo;
import lj.model.user.RoleInfo;
import lj.model.user.UserInfo;
import lj.rest.RestResult;
import lj.service.client.ClientInfoService;
import lj.service.user.UserInfoService;
import lj.service.weixin.WxBaseService;
import lj.util.JsonUtils;
import lj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import static lj.rest.RestResult.RESULTCODE_FAIL;
import static lj.rest.RestResult.RESULTCODE_SUCCESS;

/**
 * @author nichols
 * @date 2023/5/26 10:11
 */
@Controller
@RequestMapping("rest/userinfo")
public class UserInfoRest {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private WxBaseService wxBaseService;
    @Autowired
    private ClientInfoService clientInfoService;

    @RequestMapping(value = { "/login/randomCode/{randomCode}" }, method = RequestMethod.GET)
    public @ResponseBody RestResult loginOrRegisterByUserOpenId(HttpServletRequest req, @PathVariable String randomCode) {
        Map<String, String> params = wxBaseService.getOpenIdUnionIdByCode(randomCode);
        if (params == null || params.containsKey("openid") == false) {
            System.out.println("UserInfoRest.loginByRandomCode() 获得OpenId失败!");
            return new RestResult(RestResult.RESULTCODE_FAIL, "获得OpenId失败!");
        }
        // 2-查询用户信息
        String userWeixin = params.get("openid");
        UserInfo userInfo = userInfoService.loginOrRegisterByUserWeixin(userWeixin, RoleInfo.ROLE_ID_COMMON);
        if (userInfo == null) {
            return new RestResult(RESULTCODE_FAIL,"登录/注册失败",null);
        }
        return new RestResult(RESULTCODE_SUCCESS,"", JsonUtils.objectToJson(userInfo));
    }

    @RequestMapping(value = { "/getOpenId/{randomCode}" }, method = RequestMethod.GET)
    public @ResponseBody RestResult getOpenId(HttpServletRequest req, @PathVariable String randomCode) {
        Map<String, String> params = wxBaseService.getOpenIdUnionIdByCode(randomCode);
        if (params == null || params.containsKey("openid") == false) {
            System.out.println("UserInfoRest.loginByRandomCode() 获得OpenId失败!");
            return new RestResult(RestResult.RESULTCODE_FAIL, "获得OpenId失败!");
        }
        // 2-查询用户信息
        String userWeixin = params.get("openid");
        return new RestResult(RESULTCODE_SUCCESS,"", userWeixin);
    }

    @RequestMapping(value = "/getClientInfoByOpenId/{openId}",method = RequestMethod.GET)
    public @ResponseBody RestResult loginByWx(HttpServletRequest req,@PathVariable String openId) {
        List<ClientInfo> clientList = clientInfoService.findAllByClientWeixin(openId);
        if(clientList==null || clientList.size()<=0) {
            return new RestResult(RESULTCODE_SUCCESS,"暂无该用户");
        }
        return new RestResult(RESULTCODE_SUCCESS,"", JsonUtils.objectToJson(clientList.get(0)));
    }

    @RequestMapping(value = "/getWxUserPhoneNumber/{phoneCode}/{clientWeixin}",method = RequestMethod.GET)
    public @ResponseBody RestResult getWxUserPhoneNumber(HttpServletRequest req, @PathVariable String phoneCode,@PathVariable String clientWeixin) {
        // System.out.println(phoneCode);
        String wxUserPhoneNumber = wxBaseService.getWxUserPhoneNumber(phoneCode);
        if (StringUtils.isNullOrEmpty(wxUserPhoneNumber)) {
            return new RestResult(RestResult.RESULTCODE_FAIL,"获取用户手机号失败");
        }
        ClientInfo clientInfo = new ClientInfo();
        List<ClientInfo> clientList = clientInfoService.findAllByClientWeixin(clientWeixin);
        if(clientList==null || clientList.size()<=0) {
            String clientName = "用户"+StringUtils.generateRandomString(4);
            clientInfo.setClientName(clientName);
            clientInfo.setClientPhone(wxUserPhoneNumber);
            clientInfo.setClientWeixin(clientWeixin);
            Long errCode = clientInfoService.insertClient(clientInfo);
            if (errCode <= 0) return new RestResult(RestResult.RESULTCODE_FAIL,"新增用户失败");
            return new RestResult(RESULTCODE_SUCCESS,"",JsonUtils.objectToJson(clientInfo));
        } else {
            clientInfo = clientList.get(0);
            clientInfo.setClientPhone(wxUserPhoneNumber);
            String errMsg = clientInfoService.updateClientInfo(clientInfo, 1L);
            if (!StringUtils.isNullOrEmpty(errMsg)) return new RestResult(RESULTCODE_FAIL,"更新用户信息失败");
            return new RestResult(RESULTCODE_SUCCESS,"",JsonUtils.objectToJson(clientList.get(0)));
        }
    }
}
