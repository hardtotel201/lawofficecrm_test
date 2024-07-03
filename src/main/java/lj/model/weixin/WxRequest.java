package lj.model.weixin;

import lj.model.client.ClientInfo;
import lj.model.survey.SurveyQuestionnaire;

import java.util.List;

/**
 * @author nichols
 * @date 2023/6/9 16:47
 */
public class WxRequest {
    private ClientInfo clientInfo;
    private List<SurveyQuestionnaire> surveyQuestionnaires;
    private Long surveyId;
    private String locationCode;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public List<SurveyQuestionnaire> getSurveyQuestionnaires() {
        return surveyQuestionnaires;
    }


    public void setSurveyQuestionnaires(List<SurveyQuestionnaire> surveyQuestionnaires) {
        this.surveyQuestionnaires = surveyQuestionnaires;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public WxRequest() {
    }

    public WxRequest(ClientInfo clientInfo, List<SurveyQuestionnaire> surveyQuestionnaires) {
        this.clientInfo = clientInfo;
        this.surveyQuestionnaires = surveyQuestionnaires;
    }

    public WxRequest(ClientInfo clientInfo, List<SurveyQuestionnaire> surveyQuestionnaires, Long surveyId, String locationCode) {
        this.clientInfo = clientInfo;
        this.surveyQuestionnaires = surveyQuestionnaires;
        this.surveyId = surveyId;
        this.locationCode = locationCode;
    }

    @Override
    public String toString() {
        return "WxRequest{" +
                "clientInfo=" + clientInfo +
                ", surveyQuestionnaires=" + surveyQuestionnaires +
                '}';
    }
}
