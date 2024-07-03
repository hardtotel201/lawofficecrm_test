package lj.service.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lj.global.AppVar;
import lj.model.weixin.WxPushMsgRequest;
import lj.util.JsonUtils;
import lj.util.StringUtils;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author nichols
 * @date 2023/5/22 10:13
 */
@Service
public class WxBaseService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取微信小程序token
     * @return 微信token，有效期7200s
     */
    public String getWxToken() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            Request request = new Request.Builder()
                    .url(WxBaseServiceConst.GET_APP_ACCESS_TOKEN_URL)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            // 解析JSON字符串
            JSONObject accessTokenJsonObject = JSON.parseObject(response.body().string());
            String accessToken = accessTokenJsonObject.getString("access_token");
//            System.out.println(accessToken);
            return accessToken;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取微信公众号token
     * @return
     */
    public String getWxOfficialAccountToken() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(WxBaseServiceConst.GET_WX_OFFICIAL_ACCOUNT_ACCESS_TOKEN_URL)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            // 解析JSON字符串
            JSONObject accessTokenJsonObject = JSON.parseObject(response.body().string());
            String wxOfficialAccountAccessToken = accessTokenJsonObject.getString("access_token");
            return wxOfficialAccountAccessToken;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据randomCode获取微信openId
     * @param code randomCode
     * @return 微信openId的map对象
     */
    public Map<String, String> getOpenIdUnionIdByCode(String code) {
        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WxBaseServiceConst.APP_ID + "&secret="
                    + WxBaseServiceConst.APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            JsonObject jsonObject = new JsonParser().parse(Objects.requireNonNull(responseEntity.getBody()))
                    .getAsJsonObject();
            // System.out.println(lj.util.JsonUtils.objectToJson(jsonObject));
            Map<String, String> map = new HashMap<>();
            map.put("openid", jsonObject.get("openid").getAsString());
            return map;
        } catch (Exception e) {
            System.out.println("微信openId获取失败:code:[" + code + "]已经被使用");
        }
        return null;
    }


    /**
     * 根据微信提供的接口获取二维码
     * @param pagePath 小程序页面路径
     * @param scene 需要传递的参数，用逗号分隔
     *     env_version  跳转小程序的版本：正式版为 "release"，体验版为 "trial"，开发版为 "develop"
     * @return
     */
    public String createQRImg(String pagePath,String scene) {
        FileOutputStream fos = null;
        try {
            // 1.通过微信提供的接口获取二维码buffer流
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n \"page\": \""+pagePath+"\",\r\n \"scene\": \""+scene+"\",\r\n \"check_path\": false,\r\n \"env_version\": \"release\"\r\n}");
            Request request = new Request.Builder()
                    .url(WxBaseServiceConst.WX_QR_URL+"?access_token="+GetWxAccessTokenThread.token)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
//            JSONObject jsonObject = JSON.parseObject(response.body().string());
//            if (jsonObject.containsKey("errcode")) {
//                return jsonObject.getString("errmsg");
//            }
            // 2.将buffer流转为文件存储到服务器中
//            System.out.println(response.body().string());
            byte[] qrImgBytes = response.body().bytes();
            // 获取当前时间
            LocalDateTime currentDateTime = LocalDateTime.now();

            // 格式化时间为字符串形式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String fileName = currentDateTime.format(formatter);
            fos = new FileOutputStream(AppVar.staticConfig.getUploadDir()+ File.separator+fileName+".png");
            fos.write(qrImgBytes);
            fos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return "";
    }

    /**
     * 根据location生成小程序二维码
     * @param location 位置信息(只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）)
     * @return 成功返回文件名，失败返回空
     *  env_version  版本：正式版为 "release"，体验版为 "trial"，开发版为 "develop"
     */
    public String createQRImgByLocation(String location) {
        FileOutputStream fos = null;
        String fileName = "";
        try {
            // 1.通过微信提供的接口获取二维码buffer流
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n \"page\": \"pages/index/index\",\r\n \"scene\": \""+location+"\",\r\n \"check_path\": false,\r\n \"env_version\": \"release\"\r\n}");
            Request request = new Request.Builder()
                    .url(WxBaseServiceConst.WX_QR_URL+"?access_token="+GetWxAccessTokenThread.token)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
//            JSONObject jsonObject = JSON.parseObject(response.body().string());
//            if (jsonObject.containsKey("errcode")) {
//                return jsonObject.getString("errmsg");
//            }
            // 2.将buffer流转为文件存储到服务器中
//            System.out.println(response.body().string());
            byte[] qrImgBytes = response.body().bytes();
            // 获取当前时间
            LocalDateTime currentDateTime = LocalDateTime.now();

            // 格式化时间为字符串形式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            fileName = currentDateTime.format(formatter);
            System.out.println(AppVar.staticConfig.getUploadDir()+ File.separator+fileName+".png");
            fos = new FileOutputStream(AppVar.staticConfig.getUploadDir()+ File.separator+fileName+".png");
            fos.write(qrImgBytes);
            fos.flush();
        } catch (IOException e) {
            System.out.println(e);
            return "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return fileName+".png";
    }

    /**
     * 通过微信小程序推送消息
     * @param wxPushMsgRequest 请求体，请求示例（模型其他字段不能填写）：{
     *   "touser": "oTZI74yU-T-sak_Lm9R81vD3q1YY",
     *   "template_id": "EeFLMtRhXiPrtAJCKhTw7854nlUBemGgQabZiue-f9M",
     *   "page": "pages/index/index",
     *   "data": {
     *       "thing1": {
     *           "value": "小程序跳转测试"
     *       },
     *       "time2": {
     *           "value": "2019年10月1日 15:01"
     *       },
     *       "thing3": {
     *           "value": "跳转小程序"
     *       }
     *   },
     *   "miniprogram_state" : "trial",
     *   "lang": "zh_CN"
     * }
     * @return 错误信息，成功为空
     */
    public String pushMsgByWxApp(WxPushMsgRequest wxPushMsgRequest) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            String contentInfo = JsonUtils.objectToJson(wxPushMsgRequest);
            RequestBody body = RequestBody.create(mediaType, contentInfo);
            Request request = new Request.Builder()
                    .url(WxBaseServiceConst.PUSH_MSG_BY_APP_URL+"?access_token="+GetWxAccessTokenThread.token)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            // 解析JSON字符串
            JSONObject accessTokenJsonObject = JSON.parseObject(response.body().string());
            String errmsg = accessTokenJsonObject.getString("errmsg");
            if (!"ok".equals(errmsg)) return errmsg;
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过微信公众号推送消息（个人公众号无法测试，使用测试公众号，网址链接：https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index）
     * @param wxPushMsgRequest 请求体信息，示例如下: {
     *            "touser":"oG-i952N7KR1JaRe14ZrrV7VFpCM",
     *            "template_id":"iGRJCZOP9o-EkjgrxWPzSbjzuTo5EweGQa7uLiwfI8g",
     *            "page":"",
     *            "data":{
     *                    "thing1":{
     *                        "value":"巧克力"
     *                    },
     *                    "time2": {
     *                        "value":"2019年10月1日 15:01"
     *                    },
     *                    "thing3": {
     *                        "value":"测试模板"
     *                    }
     *            }
     * }
     * @return 空为成功，其他返回错误信息
     * 注1： toUserOpenId 推送用户的openId（测试公众号号无法向正式用户推送） 示例：oG-i952N7KR1JaRe14ZrrV7VFpCM
     *      templateId  模板Id（同样为测试状态） iGRJCZOP9o-EkjgrxWPzSbjzuTo5EweGQa7uLiwfI8g
     *      除示例字段外还可填写 miniprogram（包含字符串类型的appid 所需跳转到的小程序appid，字符串类型 pagepath（非必填）	所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），要求该小程序已发布，暂不支持小游戏）、client_msg_id、url字段
     * 注2：该方法暂无法跳转微信小程序，跳转需添加miniprogram字段填写小程序信息，且要求小程序已发布。具体开发链接：https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html
     */
    public String pushMsgByWxOfficialAccount(WxPushMsgRequest wxPushMsgRequest) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            String contentInfo = JsonUtils.objectToJson(wxPushMsgRequest);
            RequestBody body = RequestBody.create(mediaType, contentInfo);
            Request request = new Request.Builder()
                    .url(WxBaseServiceConst.PUSH_MSG_BY_WX_OFFICIAL_ACCOUNT_URL+"?access_token="+GetWxAccessTokenThread.wxOfficialAccountAccessToken)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            // 解析JSON字符串
            JSONObject accessTokenJsonObject = JSON.parseObject(response.body().string());
            String errmsg = accessTokenJsonObject.getString("errmsg");
            if (!"ok".equals(errmsg)) return errmsg;
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据 code 获取微信用户手机号
     * @param phoneCode 微信小程序获得的 code
     * @return 用户授权的手机号
     */
    public String getWxUserPhoneNumber(String phoneCode) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n\"code\": \""+phoneCode+"\"\r\n}");
            Request request = new Request.Builder()
                    .url(WxBaseServiceConst.GET_USER_WX_PHONE_NUMBER_URL+GetWxAccessTokenThread.token)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            JSONObject responseJObject = JSON.parseObject(response.body().string());
            String errMsg = responseJObject.getString("errmsg");
            if (!"ok".equals(errMsg)) return "";
            JSONObject phoneResult = JSON.parseObject(responseJObject.getString("phone_info"));
            return phoneResult.getString("phoneNumber");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
