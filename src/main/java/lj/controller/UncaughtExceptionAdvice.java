package lj.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lj.exception.user.AuthorizationException;
import lj.global.AppConst;
import lj.util.LogUtils;

/***
 * 处理全局异常（所有Controller）
 * 
 * @author samlv
 *
 */
@ControllerAdvice
public class UncaughtExceptionAdvice {
	@ExceptionHandler
	public ModelAndView exceptionHandler(Exception e) {
		e.printStackTrace();
		LogUtils.logError("UncaughtExceptionAdvice.exceptionHandler:",e);
		ModelAndView mv=null;
		if(e instanceof AuthorizationException){
		     mv = new ModelAndView(AppConst.URL_LOGIN);
		}else{
			mv = new ModelAndView("error");
		}
		mv.addObject("exception", e);
		return mv;
	}
}
