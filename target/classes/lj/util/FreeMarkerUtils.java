package lj.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import lj.global.AppVar;

public class FreeMarkerUtils {
	private final static String PATH_TEMPLATE = "template";
	private static Configuration cfg = null;

	static {
		createConfiguration();
	}

	/***
	 * 禁止调用构造方法
	 */
	private FreeMarkerUtils() {

	}

	/***
	 * 创建配置文件
	 * 
	 * @return
	 */
	private static Configuration createConfiguration() {
		// 1-设置编码格式
		cfg = new Configuration();
		cfg.setEncoding(Locale.getDefault(), FileUtils.ENCODING_UTF8);
		// 2-指定模板文件的数据源，这里是一个文件目录
		File templatePath = null;
		if (StringUtils.isNullOrEmpty(AppVar.appPath) == true)
			templatePath = new File(PATH_TEMPLATE);
		else
			templatePath = new File(AppVar.appPath + File.separator + PATH_TEMPLATE);
		try {
			cfg.setDirectoryForTemplateLoading(templatePath);
			cfg.setObjectWrapper(new DefaultObjectWrapper()); // 这条语句可省略
			return cfg;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.logError("FreeMarkerUtils.getConfiguration:" + e);
			return null;
		}
	}
	
	/**
	 * 产生字符串
	 * @param templateFileName
	 * @param obj
	 * @return
	 */
	public static String generateString(String templateFileName, Object obj) {
		try (StringWriter buffer = new StringWriter();
			 Writer writer = new BufferedWriter(buffer);) {
			// 1-加载模板文件
			Template template = cfg.getTemplate(templateFileName);
			// 2-生成输出文件
			template.process(obj, writer);
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.logError("FreeMarkerUtils.generateString:" + e);
			return null;
		}
	}

	public static void generateFile(String templateFileName, File outputFile, Map<String, Object> map) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));) {
			// 1-加载模板文件
			Template template = cfg.getTemplate(templateFileName);
			// 3-生成输出文件
			template.process(map, writer);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.logError("FreeMarkerUtils.generateString:" + e);
		}
	}

	/***
	 * 根据配置文件输出字符串
	 * 
	 * @param templateFileName
	 * @return
	 */
	public static void generateFile(String templateFileName, String outputPath, Map<String, Object> map) {
		File outputFile = new File(outputPath);
		if (outputFile.exists() == false)
			try {
				outputFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		generateFile(templateFileName, outputFile, map);
	}

	
}
