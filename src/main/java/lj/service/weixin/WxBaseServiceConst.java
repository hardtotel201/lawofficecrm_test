package lj.service.weixin;

import freemarker.template.Template;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;

/**
 * @author nichols
 * @date 2023/5/22 10:29
 */
public class WxBaseServiceConst {

    /**
     * 微信小程序appId
     */
    // public static final String APP_ID = "wx8cf9b08470ac362e";
    // 律师
    public static final String APP_ID = "wxc4cd15d718683e3d";

    /**
     * 微信小程序appSecret
     */
    // public static final String APP_SECRET = "040c2863d7f683366322c59908017dd2";
    // 律师
    public static final String APP_SECRET = "3eb927519e1a72ebf1d72df4344f94b5";

    /**
     * 微信小程序模板id
     */
    public static final String APP_TEMPLATE_ID = "qPOnX8AN9bPAQFwsLfaaaDOfyZ0-Fx-U-F9u9aHPkg8";

    /**
     * 获取小程序tokenURL
     */
    public static final String GET_APP_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APP_ID+"&secret="+APP_SECRET;

    /**
     * 生成跳转二维码链接
     */
    public static final String WX_QR_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";

    /**
     * 微信公众号appid（测试id）
     */
    // public static final String WX_OFFICIAL_ACCOUNT_APP_ID = "wx6e7dc6bcb13b86be";
    // 律师
    public static final String WX_OFFICIAL_ACCOUNT_APP_ID = "wxdfa2aacc0c27dc65";

    /**
     * 微信公众号appSECRET（测试SECRET）
     */
    // public static final String WX_OFFICIAL_ACCOUNT_APP_SECRET = "3dad01475fc3e0b5c5a698d781d212b5";
    // 律师
    public static final String WX_OFFICIAL_ACCOUNT_APP_SECRET = "bdf064438e9b35c4ec0b1abf4cc042fd";

    /**
     * 获取微信公众号token
     */
    public static final String GET_WX_OFFICIAL_ACCOUNT_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WX_OFFICIAL_ACCOUNT_APP_ID+"&secret="+WX_OFFICIAL_ACCOUNT_APP_SECRET;

    /**
     * 通过微信小程序推送消息url
     */
    public static final String PUSH_MSG_BY_APP_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";

    /**
     * 通过微信公众号推送消息url
     */
    public static final String PUSH_MSG_BY_WX_OFFICIAL_ACCOUNT_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";

    public static final String GET_USER_WX_PHONE_NUMBER_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";
}
