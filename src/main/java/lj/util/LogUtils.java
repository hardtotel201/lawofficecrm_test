package lj.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
	public static String FILE_LOG_CONFIG="log4j2.xml";
	private static Logger logInfo = null;
	private static Logger logError = null;
	static {
		//1-设置日志配置文件
	    System.setProperty("log4j.configurationFile", LogUtils.FILE_LOG_CONFIG);
		//2-获得日志对象	
		logInfo = LogManager.getLogger("lj.info");
		logError = LogManager.getLogger("lj.error");
		System.out.println("日志对象初始化成功....");
	}
	// 禁止实例对象
	private LogUtils() {
	}
	

	
	/**
	 * 获得LogInfo
	 * @return
	 */
	public static Logger getLogInfo()
	{
		return logInfo;
	}
	
	/***
	 * 信息日志并且Debug
	 * @param msg
	 */
	public static void logInfoAndConsole(String msg) {
		logInfo(msg);
		System.out.println(msg);
	}

	/***
	 * 信息日志
	 * @param msg
	 */
	public static void logInfo(String msg) {
		logInfo.info(msg);
	}
	
	
	/***
	 * 错误日志
	 * @param msg
	 */
	public static void logErrorAndConsole(String msg) {
		logError(msg);
		System.out.println(msg);
	}
	

	/***
	 * 错误日志
	 * @param msg
	 */
	public static void logError(String msg) {
		logError.error(msg);
	}
	
	/**
	 * 日志异常信息
	 * @param tag
	 * @param e
	 */
	public static void logError(String tag,Exception e){
		logError.error(tag+":"+e);
	}
	
	/**
	 * 日志异常信息
	 * @param tag
	 * @param e
	 */
	public static void logException(String tag,Throwable e){
		logError.error(tag+":"+e);
	}

}
