package lj.service.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lj.model.sys.ParamInfo;
import lj.service.base.BaseService;
import lj.service.sys.ParamInfoService;

@Service
public class WechatService extends BaseService{
	
	@Autowired
	private ParamInfoService paramInfoService;
	

	/**
	 * 查询地址名称列表
	 * @return
	 */
	public List<String> getLocationNames(){
		ParamInfo param=this.paramInfoService.findByParamName(ParamInfoService.PARAMNAME_SURVEY_LOCATIONS);
		String[] locations=param.getParamValue().split(";");
		List<String> names=new ArrayList<String>();
		for(String location:locations) {
			String[] strs=location.split(",");
			names.add(strs[0]);
		}
		return names;
	}
	
	public List<String> getLocationCodes(){
		ParamInfo param=this.paramInfoService.findByParamName(ParamInfoService.PARAMNAME_SURVEY_LOCATIONS);
		String[] locations=param.getParamValue().split(";");
		List<String> codes=new ArrayList<String>();
		for(String location:locations) {
			String[] strs=location.split(",");
			codes.add(strs[1]);
		}
		return codes;
	}
	
	/**
	 * 根据地址编码查询地址名称
	 * @param locationCode
	 * @return
	 */
	public String findLocationName(String locationCode){
		ParamInfo param=this.paramInfoService.findByParamName(ParamInfoService.PARAMNAME_SURVEY_LOCATIONS);
		String[] locations=param.getParamValue().split(";");
		for(String location:locations) {
			String[] strs=location.split(",");
			if(strs[1].equals(locationCode))
				return strs[0];
		}
		return "";
	}
	
	/**
	 * 根据地址名称查询地址编码
	 * @param locationName
	 * @return
	 */
	public String findLocationCode(String locationName){
		ParamInfo param=this.paramInfoService.findByParamName(ParamInfoService.PARAMNAME_SURVEY_LOCATIONS);
		String[] locations=param.getParamValue().split(";");
		for(String location:locations) {
			String[] strs=location.split(",");
			if(strs[0].equals(locationName))
				return strs[1];
		}
		return null;
	}
}
