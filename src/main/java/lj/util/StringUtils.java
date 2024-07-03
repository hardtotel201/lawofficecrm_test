package lj.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/*
 * 字符串工具类
 */
public class StringUtils {
	public static String STR_EMPTY = "";
	public static String STR_UNDERLINE = "_";
	public static String STR_CHANGELINE = "\r\n";
	public static String STR_TRUE = "true";
	public static String STR_FALSE = "false";
	public static String STR_CHN_MAN = "男";
	public static String STR_CHN_WOMAN = "女";
	public static String STR_CHN_TRUE = "是";
	public static String STR_CHN_FALSE = "否";

	// 日期格式
	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static String DATE_FORMAT_SMALL = "yy-MM-dd";
	public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String DATETIME_FORMAT_JSON = "yyyy-MM-dd'T'HH:mm:ss";
	public static String DATETIME_FORMAT_STR = "yyyyMMddHHmmssFFF";
	public static String DATETIME_FORMAT_STR_SHORT="yyyyMMddHHmmss";
	public static String TIME_FORMAT = "HH:mm:ss";
	
	// 文件名称格式
	public static String DATETIME_FORMAT_FILENAME = "yyyyMMddHHmmssFFF";

	/*
	 * 获得底层换行符
	 */
	public static String getLineSeparator() {
		String str = System.getProperty("line.separator");
		return str;
	}

	/*
	 * 字符串==null || equals("")
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.equals(STR_EMPTY);
	}

	/*
	 * 格式化时间字符串
	 */
	public static String timeToString(Date date) {
		String str = timeToString(date, DATETIME_FORMAT_STR);
		return str;
	}

	/*
	 * 格式化时间字符串
	 */
	public static String timeToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String abcValue = sdf.format(date);
		return abcValue;
	}

	/***
	 * 获得随机的十六进制字符串
	 * 
	 * @return
	 */
	public static String getRandomHexString() {
		String str = Long.toHexString(System.currentTimeMillis());
		return str;
	}

	/***
	 * 转成首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String toCamelCase(String str) {
		if (StringUtils.isNullOrEmpty(str) == true)
			return str;
		String temp = str.substring(0, 1).toLowerCase() + str.substring(1);
		return temp;
	}

	/***
	 * 转成Big Camel-case字符
	 * 
	 * @param str
	 * @return
	 */
	public static String toBigCamelCase(String str) {
		if (StringUtils.isNullOrEmpty(str) == true)
			return str;
		String temp = str.substring(0, 1).toUpperCase() + str.substring(1);
		return temp;
	}

	/***
	 * 转成变量名
	 * 
	 * @param str
	 * @return
	 */
	public static String toVarName(String str) {
		String strRet = toCamelCase(str);
		return strRet;
	}

	public static String toTypeName(String str) {
		String strRet = toBigCamelCase(str);
		return strRet;
	}

	/***
	 * 返回常量名称
	 * 
	 * @param str
	 * @return
	 */
	public static String toConstName(String str) {
		// 1-length<=0
		if (StringUtils.isNullOrEmpty(str) == true)
			return str;
		// 2-length=1
		if (str.length() == 1)
			return str.toUpperCase();
		// 3-length>1
		String strRet = str.substring(0, 1).toUpperCase();
		for (int i = 1; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (Character.isUpperCase(ch) || Character.isDigit(ch))
				strRet = strRet + Character.toUpperCase(ch) + StringUtils.STR_UNDERLINE;
			else
				strRet = strRet + Character.toUpperCase(ch);
		}
		return strRet;
	}

	/**
	 * 时间格式化输出
	 * 
	 * @param date
	 * @return
	 */
	public static final String dateToFileName(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT_FILENAME);
		String str = format.format(date);
		return str;
	}

	/**
	 * 时间格式化输出
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static final String dateToFileName(String pattern, Date date) {
		String str=dateToStr(date,pattern);
		return str;
	}
	
	public static final String dateToStr(Date date,String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String str = format.format(date);
		return str;
	}

	/**
	 * String->Date
	 * 
	 * @param str
	 * @return
	 */
	public static Date strToDate(String str) {
		Date date=strToDate(str,DATETIME_FORMAT) ;
		return date;
	}

	public static Date strToDate(String str,String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		try {
			Date date = format.parse(str);
			return date;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 数组连接运算
	 * @param <T>
	 * @param objs
	 * @param separator
	 * @return
	 */
	public static <T> String join(T[] objs,String separator)
	{
		String str="";
		for(Object obj:objs)
		{
			if(StringUtils.isNullOrEmpty(str)==false)
				str=str+separator;
			str=str+obj.toString();
		}
		return str;
	}

	// Generate a string of random digits

	/**
	 * 生成指定长度的随机字符串
	 * @param strLength 随机字符串长度
	 * @return 生成的字符串
	 */
	public static String generateRandomString(int strLength) {
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		String result = "";
		while (result.length() < strLength) {
			int index = random.nextInt(characters.length());
			char c = characters.charAt(index);
			result += c;
		}
		return result;
	}
}
