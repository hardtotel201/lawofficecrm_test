package lj.model.weixin;

import java.util.Map;

/**
 * @author nichols
 * @date 2023/5/29 20:37
 */
public class WxPushMsgRequest {

    private String touser;

    private String template_id;

    private String url;

    private Map<String,Object> miniprogram;

    private String client_msg_id;

    private Map<String,Object> data;

    private String miniprogram_state;

    private String lang;

    private String page;

    public WxPushMsgRequest() {
    }

    public WxPushMsgRequest(String touser, String template_id, String url, Map<String, Object> miniprogram, String client_msg_id, Map<String, Object> data, String miniprogram_state, String lang, String page) {
        this.touser = touser;
        this.template_id = template_id;
        this.url = url;
        this.miniprogram = miniprogram;
        this.client_msg_id = client_msg_id;
        this.data = data;
        this.miniprogram_state = miniprogram_state;
        this.lang = lang;
        this.page = page;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(Map<String, Object> miniprogram) {
        this.miniprogram = miniprogram;
    }

    public String getClient_msg_id() {
        return client_msg_id;
    }

    public void setClient_msg_id(String client_msg_id) {
        this.client_msg_id = client_msg_id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMiniprogram_state() {
        return miniprogram_state;
    }

    public void setMiniprogram_state(String miniprogram_state) {
        this.miniprogram_state = miniprogram_state;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "{" +
                "touser='" + touser + '\'' +
                ", template_id='" + template_id + '\'' +
                ", url='" + url + '\'' +
                ", miniprogram=" + miniprogram +
                ", client_msg_id='" + client_msg_id + '\'' +
                ", data=" + data +
                ", miniprogram_state='" + miniprogram_state + '\'' +
                ", lang='" + lang + '\'' +
                ", page='" + page + '\'' +
                '}';
    }
}
