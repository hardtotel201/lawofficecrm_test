package lj;

import java.io.File;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lj.controller.GlobalInterceptor;
import lj.global.AppFun;
import lj.global.AppVar;
import lj.service.weixin.GetWxAccessTokenThread;

@SpringBootApplication
@ComponentScan(basePackages = "lj")
public class LawOfficeCrmStart implements WebMvcConfigurer {

	public static void main(String[] args) {
		// 1-加载
		AppFun.preLoad(LawOfficeCrmStart.class,args);
		//2-开始定时线程
		//2.1-获取微信小程序token线程，token有效期7200s
		GetWxAccessTokenThread thread1 = AppVar.context.getBean(GetWxAccessTokenThread.class);
		thread1.start();
	}

	/**
	 * 加载全局拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration ir = registry.addInterceptor(new GlobalInterceptor());
		ir.addPathPatterns("/**");
	}
	
	/**
	 * 自定义系统参数：上传路径
	 */
	@Value("${lj.model.sys.staticconfig.uploadDir}")
	private String uploadDir;

	/**
	 * 追加静态资源定义
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String filePath=this.uploadDir+File.separator;
		System.out.println("WebStarter.addResourceHandlers add resource path:"+filePath);
		registry.addResourceHandler("/upload/**").addResourceLocations("file:"+filePath);
	}
	
	@Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
