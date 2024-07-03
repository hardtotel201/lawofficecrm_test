package lj.service.sum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lj.mapper.sum.SumClientMapper;
import lj.model.client.ClientInfo;
import lj.model.sum.SumByString;
import lj.service.base.BaseService;
import lj.util.StringUtils;

@Service
public class SumClientService extends BaseService {
	
	@Autowired
	private SumClientMapper sumClientMapper=null;
	
	
	public List<SumByString> sumClients(ClientInfo reqModel) {
		List<SumByString> objs=this.sumClientMapper.sumClientsByMonth(reqModel);
		Calendar calenda=Calendar.getInstance();
		
		List<SumByString> retObjs=new ArrayList<SumByString>();
		for(calenda.setTime(reqModel.getRegisterTimeBegin());
				calenda.getTime().getTime()<=reqModel.getRegisterTimeEnd().getTime();calenda.add(Calendar.MONTH, 1)) {
			String timeStr=StringUtils.timeToString(calenda.getTime(), "YYYY-MM");
			SumByString temp=null;
			for(SumByString obj:objs)
				if(obj.getSumTitle().equals(timeStr)) {
					temp=obj;
					break;
				}
			if(temp==null) {
				SumByString obj=new SumByString();
				obj.setSumTitle(timeStr);
				retObjs.add(obj);
			}
			else
				retObjs.add(temp);
		}
		System.out.println("SumClientService.sumClients() return:"+lj.util.JsonUtils.objectToJson(retObjs));
		return retObjs;
	}

}
