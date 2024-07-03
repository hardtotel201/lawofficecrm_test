package lj.global;

import java.io.File;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.util.ClassUtils;

//import lj.dao.sys.SysConfigDao;
import lj.model.sys.StaticConfig;
import lj.service.sys.ParamInfoService;
//import lj.model.sys.SysConfig;
import lj.util.DbUtils;
import lj.util.LogUtils;
import lj.util.StringUtils;

/**
 * 应用程序全局函数
 * @author samlv
 *
 */
public class AppFun {
	public final static String INCOMEMETHOD_WEIXIN="微信";
	
	/**
	 * 预装载
	 * @param appClass
	 * @param args
	 * @return
	 */
	public static int preLoad(Class appClass,String[] args) {
		//1-初始化应用程序路径
		AppVar.appPath=ClassUtils.getDefaultClassLoader().getResource("").getPath();
		LogUtils.logInfoAndConsole("用户服务应用程序路径:"+AppVar.appPath);
		AppVar.context=SpringApplication.run(appClass, args);
		
		
		//2-读入静态配置参数
		//2.1-检查上传目录
		AppVar.staticConfig=AppVar.context.getBean(StaticConfig.class);
		String uploadPath=AppVar.staticConfig.getUploadDir();
		File uploadDir=new File(uploadPath);
		if(uploadDir.exists()==false) {
			boolean isSucess=uploadDir.mkdirs();
			if(isSucess==false)
				LogUtils.logErrorAndConsole("创建上传目录失败:"+uploadPath);
			
		}
		
		//3-读入动态可修改参数
		ParamInfoService paramInfoService=AppVar.context.getBean(ParamInfoService.class);
		if(paramInfoService.reloadParamInfos()<0) {
			LogUtils.logErrorAndConsole("应用程序参数加载失败");
			return -1;
		}
		
		//4-加共享信息到Redis
//		UserInfoService userInfoService=AppVar.context.getBean(UserInfoService.class);
//		userInfoService.loadAllToRedis();
		
		
		//9-开始应用程序
		LogUtils.logInfoAndConsole("应用程序加载成功--------------------------------------------------------------------");
		return 1;
	}
	
	/**
	 * 退出系统
	 * @return
	 */
	public String exitSystem(){
		
		return StringUtils.STR_EMPTY;
	}
	
	
	/**
	 * 产生随机验证码
	 * 
	 * @return
	 */
	public static String generateIdentifyingCode(int numOfChars) {
		String str = StringUtils.STR_EMPTY;
		Random random = new Random();
		String optionalCodes =AppConst.IDENTIFIER_CODE_CHARS;
		for (int i = 0; i < numOfChars; i++)
			str = str + optionalCodes.charAt(random.nextInt(optionalCodes.length()));
		return str;
	}
	
	public static String getUploadPath() {
		String filePath=AppVar.appPath+"upload";
		return filePath;
	}
	
	/**
	 * 返回支付方式数组
	 * @return
	 */
	public static String[] getIncomeMethods() {
		String[] strs=new String[] {INCOMEMETHOD_WEIXIN};
		return strs;
	}
	
	
}
