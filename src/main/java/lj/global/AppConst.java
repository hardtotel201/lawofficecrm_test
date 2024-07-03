package lj.global;

import java.io.File;

/***
 * 平台常量
 * 
 * @author samlv
 *
 */
public class AppConst {
	//数据库类型
	public final static String DATABSE_TYPE="mysql";

	// 验证码候选字符
	public final static String IDENTIFIER_CODE_CHARS = "0123456789";
	// 验证码长度
	public final static int IDENTIFIER_CODE_LENGTH = 4;

	// 分页大小
	public final static int PAGE_SIZE = 10;

	// 配置文件路径
	public final static String PATH_CONFIG = "WEB-INF" + File.separator + "config";
	// 上传文件路径
	public final static String PATH_UPLOAD = "resources"+File.separator+"upload";
	// 用户上传目录
	public final static String PATH_USER = PATH_UPLOAD + File.separator + "user";
	// 用户头像上传目录
	public final static String PATH_USER_IMAGE = PATH_USER + File.separator + "image";

	

	// 下载文件路径
	public final static String PATH_DOWNLOAD = "download";

	/**
	 * session变量
	 */
	public final static String SESSION_USERINFO = "userInfo";
	
	/**
	 * 重要URL
	 */
	public final static String URL_LOGIN="login";
	public final static String URL_MAIN="main";
	public final static String URL_DOWNLOAD="download";
	

	public final static int VERIFYING_HOUR_USER=5;  //一个用户验证数量(小时)
	public final static int VERIFYING_HOUR_ALL =200;//所有用户验证数量(小时)
	
	
}
