//package lj.util;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.security.*;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
//import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
//import lj.config.WechatpayConfig;
//import lj.model.sale.OrderInfo;
//import lj.model.wx.wxpayConstant;
//import lj.rest.RestResult;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class WeixinUtils {
//    @Autowired
//    private static WechatpayConfig wechatpayConfig;
//
//    @Autowired
//    private static WechatPayHttpClientBuilder builder;
//
//    private static String getToken(long timestamp, String nonceStr, String pakeage) {
//
//        String message = buildMessage(timestamp, nonceStr, pakeage);
//        String signature = null;
//        try {
//            signature = sign(message.getBytes("utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        return signature;
//    }
//
//    private static String buildMessage(long timestamp, String nonceStr, String pakeage) {
//
//        return wechatpayConfig.getAppId() + "\n"
//                + timestamp + "\n"
//                + nonceStr + "\n"
//                + pakeage + "\n";
//    }
//
//    private static String sign(byte[] message) {
//        Signature sign = null;
//        try {
//            PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
//                    new FileInputStream(wechatpayConfig.getCertUrl()));
//            sign = Signature.getInstance("SHA256withRSA");
//            sign.initSign(merchantPrivateKey);
//            sign.update(message);
//            return Base64.getEncoder().encodeToString(sign.sign());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (SignatureException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static RestResult CreateOrder(OrderInfo orderInfo) throws Exception{
//    //请求URL
//        CloseableHttpClient httpClient = builder.build();
//        HttpPost httpPost = new HttpPost(wechatpayConfig.getDomain().concat(wxpayConstant.CREATE_PAY_URL));
//        Map<String, Object> params = new HashMap<>();
//        params.put("appid", wechatpayConfig.getAppId());
//        params.put("mchid", wechatpayConfig.getMerchantId());
//        params.put("description", orderInfo.getShopName());
//        //商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一
//        params.put("out_trade_no", orderInfo.getOrderId());
//        //异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
//        //公网域名必须为https，如果是走专线接入，使用专线NAT IP或者私有回调域名可使用http
//        params.put("notify_url", wechatpayConfig.getNotifyDomain());
//
//        Map<String, Object> amountMap = new HashMap<>();
//        //订单总金额，单位为分。
//        amountMap.put("total", 1);
//        amountMap.put("currency", "CNY");
//        params.put("amount", amountMap);
//        params.put("payer", orderInfo.getOpenId());
//
//
//         // 请求body参数
//        String reqdata = JSON.toJSONString(params);
//        StringEntity entity = new StringEntity(reqdata,"utf-8");
//        entity.setContentType("application/json");
//        httpPost.setEntity(entity);
//        httpPost.setHeader("Accept", "application/json");
//
//        //完成签名并执行请求
//        CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpPost);
//        try {
//            int statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode == 200) {
//                System.out.println("success,return body = " + EntityUtils.toString(response.getEntity()));
//                String bodyAsString = EntityUtils.toString(response.getEntity());
//                //获取预支付会话id
//                Map<String, Object> rtnBody= JSON.parseObject(bodyAsString, Map.class);
//                String prepay_id = (String) rtnBody.get("prepay_id");
//                //生成支付所需的参数
//                String nonceStr = RandomUtil.getRandomStr(10);
//                long timestamp = System.currentTimeMillis() / 1000;
//
//                //生成签名，给前端使用，和后端请求微信端没有半点关系，主要给前端使用
//                String token = getToken(timestamp, nonceStr, "prepay_id="+prepay_id);
//                Map<String, Object> resp = new HashMap<>();
//                resp.put("appId", wechatpayConfig.getAppId());
//                resp.put("timeStamp", timestamp);
//                resp.put("nonceStr", nonceStr);
//                resp.put("package", "prepay_id=".concat(prepay_id));
//                resp.put("signType", "RSA");
//                resp.put("paySign", token);
//                return new RestResult(RestResult.RESULTCODE_SUCCESS,JSON.toJSONString(resp));
//
//                //前端发起wx.requestPayment后响应小程序
//
//            } else if (statusCode == 204) {
//                System.out.println("success");
//            } else {
//                System.out.println("failed,resp code = " + statusCode+ ",return body = " + EntityUtils.toString(response.getEntity()));
//                //如果throw抛出，后续代码将不会执行
//                throw new IOException("request failed");
//            }
//        } finally {
//            response.close();
//        }
//        return new RestResult(RestResult.RESULTCODE_FAIL,"");
//    }
//
//
//
//
//}
