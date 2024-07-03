package lj.global;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;

import lj.model.sys.StaticConfig;
import lj.model.user.VerifyingCode;
import lj.model.user.VerifyingCount;
import lj.util.DbUtils;

/**
 * 应用程序变量
 * 
 * @author samlv
 *
 */
public class AppVar {
	
	// 应用程序路径
	public static String appPath = null;
	
	public static String databaseType=DbUtils.DATABASE_TYPE_MYSQL;

	// 应用程序上下文
	public static ApplicationContext context=null;

    //系统静态配置(只读)
	public static StaticConfig staticConfig=null;
	
	//系统动态配置(系统参数)
	public static String systemName =null;
	public static String companyName=null;
	public static String defaultPassword=null;
	public static long   latestSurveryId=-1;         //最新调研标识
	
	//所有验证信息<手机号码，验证信息>
	public static ConcurrentHashMap<String, VerifyingCode> mobileVerifyingCodeMap=new  ConcurrentHashMap<String,VerifyingCode>();
	//所有验证数量
	public static ConcurrentHashMap<String,VerifyingCount> mobileVerifyingCountMap=new ConcurrentHashMap<String,VerifyingCount>();
		
}
