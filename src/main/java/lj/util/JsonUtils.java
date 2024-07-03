package lj.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
	public final static String TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
	public final static String TIME_FORMAT_T="yyyy-MM-dd'T'HH:mm:ss";
	
	private static Gson gson=null;
	private JsonUtils(){}
	static{
		gson = new GsonBuilder().serializeSpecialFloatingPointValues().setDateFormat(TIME_FORMAT).create();
	}
	
	public static String objectToJson(Object obj){
		String str = gson.toJson(obj, obj.getClass());
		return str;
	}
	
	public static String objectToJson(Object obj,String timeFormat){
		Gson gson = new GsonBuilder().setDateFormat(timeFormat).create();
		String str = gson.toJson(obj, obj.getClass());
		return str;
	}
	


	public static <T> T jsonToObject(String str,Class<T> cls){
		T obj=gson.fromJson(str,cls);
		return obj;
	}
	
	public static <T> T jsonToObject(String str,Class<T> cls,String timeFormat){
		Gson gson = new GsonBuilder().setDateFormat(timeFormat).create();
		T obj=gson.fromJson(str,cls);
		return obj;
	}

	public static <T> String arrayToJson(T[] arr) {
		gson = new GsonBuilder().serializeSpecialFloatingPointValues().setDateFormat(TIME_FORMAT).serializeNulls().create();
		return gson.toJson(arr);
	}
}
