package lj.model.lawcase;



import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


//@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CaseInfoDownload {
    @ExcelProperty("案件类型")
    @ColumnWidth(20)
    private String	caseType;

    @ExcelProperty("案件程序")
    @ColumnWidth(20)
    private String caseProgram;

    @ExcelProperty("案由")
    @ColumnWidth(20)
    private String	caseSource;

    @ExcelProperty("委托人")
    @ColumnWidth(20)
    private String	clientName;

    @ExcelProperty("其他当事人")
    @ColumnWidth(20)
    private String	otherClient;

    @ExcelProperty("案件阶段")
    @ColumnWidth(20)
    private String	casePhrase;

    @ExcelProperty("待办事项")
    @ColumnWidth(20)
    private String	predealMatters;

    @ExcelProperty("办理人员")
    @ColumnWidth(20)
    private String	lawyerName;

    @ExcelProperty("受理单位")
    @ColumnWidth(20)
    private String	dealUnit;

    @ExcelProperty("案号")
    @ColumnWidth(20)
    private String	caseCode;

    @ExcelProperty("承办法官")
    @ColumnWidth(20)
    private String	dealJudge;

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseProgram() {
		return caseProgram;
	}

	public void setCaseProgram(String caseProgram) {
		this.caseProgram = caseProgram;
	}

	public String getCaseSource() {
		return caseSource;
	}

	public void setCaseSource(String caseSource) {
		this.caseSource = caseSource;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getOtherClient() {
		return otherClient;
	}

	public void setOtherClient(String otherClient) {
		this.otherClient = otherClient;
	}

	public String getCasePhrase() {
		return casePhrase;
	}

	public void setCasePhrase(String casePhrase) {
		this.casePhrase = casePhrase;
	}

	public String getPredealMatters() {
		return predealMatters;
	}

	public void setPredealMatters(String predealMatters) {
		this.predealMatters = predealMatters;
	}

	public String getLawyerName() {
		return lawyerName;
	}

	public void setLawyerName(String lawyerName) {
		this.lawyerName = lawyerName;
	}

	public String getDealUnit() {
		return dealUnit;
	}

	public void setDealUnit(String dealUnit) {
		this.dealUnit = dealUnit;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getDealJudge() {
		return dealJudge;
	}

	public void setDealJudge(String dealJudge) {
		this.dealJudge = dealJudge;
	}


}
