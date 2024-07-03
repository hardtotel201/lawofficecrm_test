package lj.dao.base;


import java.io.File;

import lj.util.XmlUtils;

/***
 * 基于XML的DAO基类对象
 * @author samlv
 *
 * @param <T>
 */
public class XMLBaseDao<T> {
	/**
	 * 配置文件路径
	 */
	private String configFilePath;
	
	/***
	 * 构造方法
	 * @param configFilePath
	 */
	public XMLBaseDao(String configFilePath){
		this.configFilePath=configFilePath;
	}
	
	public XMLBaseDao(File configFile)
	{
		this.configFilePath=configFile.getAbsolutePath();
	}
	


	public T loadFromFile() {
		T config = (T) XmlUtils.loadFromFile(configFilePath);
		return config;
	}


	public int saveToFile(T t) {
		try {
			XmlUtils.saveToFile(configFilePath, t);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
