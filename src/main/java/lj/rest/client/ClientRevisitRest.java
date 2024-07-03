package lj.rest.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lj.rest.BaseRest;

@Controller
@RequestMapping("rest/clientrevisit")
public class ClientRevisitRest  extends BaseRest{
	
	@RequestMapping(value = { "/test" }, method = RequestMethod.GET)
	public @ResponseBody String sayHellow() {
		return "hellow";
	}
}
