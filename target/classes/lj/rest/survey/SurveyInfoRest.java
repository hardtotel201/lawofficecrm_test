package lj.rest.survey;

import lj.global.AppVar;
import lj.model.client.ClientInfo;
import lj.model.survey.*;
import lj.model.sys.ParamInfo;
import lj.model.weixin.WxPushMsgRequest;
import lj.model.weixin.WxRequest;
import lj.rest.RestResult;
import lj.service.client.ClientInfoService;
import lj.service.client.WechatService;
import lj.service.survey.*;
import lj.service.sys.ParamInfoService;
import lj.service.weixin.GetWxAccessTokenThread;
import lj.service.weixin.WxBaseService;
import lj.service.weixin.WxBaseServiceConst;
import lj.util.JsonUtils;
import lj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lj.rest.BaseRest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("rest/surveyinfo")
public class SurveyInfoRest extends BaseRest{
    @Autowired
    private WxBaseService wxBaseService;

    @Autowired
    private SurveyInfoService surveyInfoService;
    @Autowired
    private SurveyQuestionService surveyQuestionService;
    @Autowired
    private QuestionInfoService questionInfoService;
    @Autowired
    private QuestionItemService questionItemService;
    @Autowired
    private WechatService wechatService;
    @Autowired
    private ClientInfoService clientInfoService;
    @Autowired
    private SurveyQuestionnaireService surveyQuestionnaireService;

    /**
     * 根据调研标识获取问卷信息
     * @param req
     * @return 问卷json对象
     */
    @RequestMapping(value = {"/getSurveyInfo"},method = RequestMethod.GET)
    public @ResponseBody SurveyInfo getSurveyInfoById(HttpServletRequest req) {
//        ParamInfo surveyInfoParam = paramInfoService.findByParamName("最新调研标识");
//        Long surveyId = Long.parseLong(surveyInfoParam.getParamValue());
        System.out.println("最新调研标识："+AppVar.latestSurveryId);
        Long surveyId = AppVar.latestSurveryId;
        SurveyInfo surveyInfo = surveyInfoService.find(surveyId);
        SurveyQuestion surveyQuestion = new SurveyQuestion();
        surveyQuestion.setSurveyId(surveyId);
        List<QuestionInfo> questionInfos = new ArrayList<>();
        List<SurveyQuestion> surveyQuestions = surveyQuestionService.findAllByRequestObj(surveyQuestion);
        for (SurveyQuestion question : surveyQuestions) {
            Long questionId = question.getQuestionId();
            QuestionInfo questionInfo = questionInfoService.find(questionId);
            questionInfo.setSurveyQuestionId(question.getSurveyQuestionId());
            List<QuestionItem> questionItems = questionItemService.findByQuestionId(questionId);
            questionInfo.setQuestionItems(questionItems);
            questionInfos.add(questionInfo);
        }
        surveyInfo.setQuestionInfos(questionInfos);
        return surveyInfo;
    }

    /**
     * 通过微信小程序推送消息
     * @param wxPushMsgRequest 请求对象
     * @param req
     * @return
     */
    @RequestMapping(value = { "/pushMsgByApp" }, method = RequestMethod.POST)
    public @ResponseBody String pushMsgByApp(@RequestBody WxPushMsgRequest wxPushMsgRequest, HttpServletRequest req) {
//        System.out.println(JsonUtils.objectToJson(wxPushMsgRequest));
//        wxPushMsgRequest.setTemplate_id(WxBaseServiceConst.APP_TEMPLATE_ID);
//        wxPushMsgRequest.setPage("pages/index/index");
//        wxPushMsgRequest.setMiniprogram_state("trial"); // 体验版
//        wxPushMsgRequest.setLang("zh_CN");
        String resString = wxBaseService.pushMsgByWxApp(wxPushMsgRequest);
        return resString;
    }

    /**
     * 通过微信公众号推送消息
     * @param wxPushMsgRequest 请求对象
     * @param req
     * @return
     */
    @RequestMapping(value = { "/pushMsgByWxOfficialAccount" }, method = RequestMethod.POST)
    public @ResponseBody String pushMsgByWxOfficialAccount(@RequestBody WxPushMsgRequest wxPushMsgRequest, HttpServletRequest req) {
//        System.out.println(JsonUtils.objectToJson(wxPushMsgRequest));
        String resString = wxBaseService.pushMsgByWxOfficialAccount(wxPushMsgRequest);
        return resString;
    }

    /**
     * 新增/更新用户信息以及问卷信息
     * @param wxRequest 请求对象
     * @param req
     * @return
     */
    @RequestMapping(value = {"/postSurveyQuestionnaire"},method = RequestMethod.POST)
    public @ResponseBody RestResult postSurveyInfo(@RequestBody WxRequest wxRequest,HttpServletRequest req) {
        // System.out.println(wxRequest);
        // 根据code获取locationName
        String locationCode = wxRequest.getLocationCode();
        String locationName = wechatService.findLocationName(locationCode);
        String errMsg = "";
        ClientInfo clientInfo = wxRequest.getClientInfo();
        clientInfo.setClientAddress(locationName);
        List<ClientInfo> clientList = clientInfoService.findAllByClientWeixin(clientInfo.getClientWeixin());
        if(clientList==null || clientList.size()<=0) {
            // 新增clientInfo信息
            clientInfo.setSourceMemo(locationName);
            clientInfo.setSourceTable("surveyinfo");
            clientInfo.setSourceId(wxRequest.getSurveyId());
            Long errCode = clientInfoService.insertClient(clientInfo);
            if (errCode <= 0) return new RestResult(RestResult.RESULTCODE_FAIL,"新增用户失败");
        } else {
            // 更新clientInfo信息
            clientInfo.setClientId(clientList.get(0).getClientId());
            ClientInfo updateClientInfo = clientList.get(0);
            updateClientInfo.setClientName(clientInfo.getClientName());
            updateClientInfo.setClientPhone(clientInfo.getClientPhone());
            updateClientInfo.setClientWeixin(clientInfo.getClientWeixin());
            if (StringUtils.isNullOrEmpty(updateClientInfo.getSourceMemo())) {
                updateClientInfo.setSourceMemo(locationName);
            }
            if (StringUtils.isNullOrEmpty(updateClientInfo.getSourceTable())) {
                updateClientInfo.setSourceTable("surveyinfo");
            }
            updateClientInfo.setSourceId(wxRequest.getSurveyId());
            updateClientInfo.setClientAddress(locationName);
            errMsg = clientInfoService.updateClientInfo(updateClientInfo, 1L);
        }
        if (!StringUtils.isNullOrEmpty(errMsg)) return new RestResult(RestResult.RESULTCODE_FAIL,errMsg);
        List<SurveyQuestionnaire> surveyQuestionnaires = wxRequest.getSurveyQuestionnaires();
        for (SurveyQuestionnaire surveyQuestionnaire : surveyQuestionnaires) {
            surveyQuestionnaire.setClientId(clientInfo.getClientId());
            surveyQuestionnaire.setQuestionnaireState("已回答");
            surveyQuestionnaire.setAnswerTime(surveyQuestionnaireService.getNowTime());
            SurveyQuestionnaire reqSurveyQuestionnaire = new SurveyQuestionnaire();
            reqSurveyQuestionnaire.setSurveyQuestionId(surveyQuestionnaire.getSurveyQuestionId());
            reqSurveyQuestionnaire.setClientId(clientInfo.getClientId());
            List<SurveyQuestionnaire> surveyQuestionnaireList = surveyQuestionnaireService.findAllByRequestObj(reqSurveyQuestionnaire);
            if(surveyQuestionnaireList==null || surveyQuestionnaireList.size()<=0) {
                // 新增调研问卷信息
                Long errCode = surveyQuestionnaireService.insertSurveyQuestionnaire(surveyQuestionnaire);
                if (errCode <= 0) return new RestResult(RestResult.RESULTCODE_FAIL,"新增问卷失败");
            } else {
                // 更新调研问卷信息
                surveyQuestionnaire.setSurveyQuestionnaireId(surveyQuestionnaireList.get(0).getSurveyQuestionnaireId());
                errMsg = surveyQuestionnaireService.updateSurveyQuestionnaire(surveyQuestionnaire);
                if (!StringUtils.isNullOrEmpty(errMsg)) return new RestResult(RestResult.RESULTCODE_FAIL,errMsg);
            }
        }
        return new RestResult(RestResult.RESULTCODE_SUCCESS,"");
    }

    /**
     * 接收小程序上传的文件
     * @param file 文件对象
     * @param req
     * @return
     */
    @RequestMapping(value = {"/document"},method = RequestMethod.POST)
    public @ResponseBody RestResult uploadDoc(@RequestParam("file") MultipartFile file, HttpServletRequest req) {
        String dest="";
        try {
            String labelLocation = AppVar.staticConfig.getUploadDir();
            String fileName = file.getOriginalFilename();
            System.out.println(file);
            dest = labelLocation + "\\" + fileName;
            File filedest = new File(dest);
            if (!filedest.exists()) filedest.mkdirs();
            file.transferTo(filedest);
        } catch (Exception e) {
            System.out.println(e);
            return new RestResult(RestResult.RESULTCODE_FAIL,e.getMessage());
        }
        return new RestResult(RestResult.RESULTCODE_SUCCESS,"");
    }
}
