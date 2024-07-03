package lj.generator;

import java.io.File;
import java.sql.Connection;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.ClassUtils;

import lj.dao.base.MetaDataDao;
import lj.global.AppFun;
import lj.global.AppVar;
import lj.util.GeneratorUtils;
import lj.util.LogUtils;
import lj.util.StringUtils;

/**
 * Spring MVC代码产生器
 * 
 * @author samlv
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "lj")
public class MvcGenerator {
	public static void copy(String parentCateogry, String tableName) throws Exception {
		// 1-预加载
		AppVar.appPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
		// 3-需要设置信息

		String srcPath = "src" + File.separator + "main" + File.separator + "java";
		String webPath = "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "WEB-INF"
				+ File.separator + "jsp";
		String modelClassPath = "lj" + File.separator + "model" + File.separator + parentCateogry;
		String mapperInterfacePath = "lj" + File.separator + "mapper" + File.separator + parentCateogry;
		String serviceClassPath = "lj" + File.separator + "service" + File.separator + parentCateogry;
		String controllerClassPath = "lj" + File.separator + "controller" + File.separator + parentCateogry;

		// 1-模型类
		String generatDir = AppVar.appPath + GeneratorUtils.PATH_GENERATOR_JAVA;
		System.out.println("generatorEntityClass generatDir:" + generatDir);
		String sourcePath = generatDir + File.separator + StringUtils.toTypeName(tableName) + ".java";
		File sourceFile = new File(sourcePath);
		if (sourceFile.exists() == false) {
			System.out.println("模型类不存在");
			return;
		}
		String destPath = AppVar.appPath + ".." + File.separator + ".." + File.separator + srcPath + File.separator
				+ modelClassPath;
		System.out.println(destPath);
		File destDir = new File(destPath);
		FileUtils.copyFileToDirectory(sourceFile, destDir, true);
		// 2-mapper
		String interfaceName = StringUtils.toTypeName(tableName) + "Mapper";
		sourcePath = generatDir + File.separator + StringUtils.toTypeName(interfaceName) + ".java";
		sourceFile = new File(sourcePath);
		if (sourceFile.exists() == false) {
			System.out.println("mapper类不存在");
			return;
		}
		destPath = AppVar.appPath + ".." + File.separator + ".." + File.separator + srcPath + File.separator
				+ mapperInterfacePath;
		System.out.println(destPath);
		destDir = new File(destPath);
		FileUtils.copyFileToDirectory(sourceFile, destDir, true);
		// 3-xml
		sourcePath = generatDir + File.separator + StringUtils.toTypeName(tableName) + "Mapper.xml";
		sourceFile = new File(sourcePath);
		if (sourceFile.exists() == false) {
			System.out.println("mapper xml不存在");
			return;
		}
		destPath = AppVar.appPath + ".." + File.separator + ".." + File.separator + srcPath + File.separator
				+ mapperInterfacePath;
		System.out.println(destPath);
		destDir = new File(destPath);
		FileUtils.copyFileToDirectory(sourceFile, destDir, true);
		// 4-service
		sourcePath = generatDir + File.separator + StringUtils.toTypeName(tableName) + "Service.java";
		sourceFile = new File(sourcePath);
		if (sourceFile.exists() == false) {
			System.out.println("mapper类不存在");
			return;
		}
		destPath = AppVar.appPath + ".." + File.separator + ".." + File.separator + srcPath + File.separator
				+ serviceClassPath;
		System.out.println(destPath);
		destDir = new File(destPath);
		FileUtils.copyFileToDirectory(sourceFile, destDir, true);
		// 5-controller
		sourcePath = generatDir + File.separator + StringUtils.toTypeName(tableName) + "Controller.java";
		sourceFile = new File(sourcePath);
		if (sourceFile.exists() == false) {
			System.out.println("Controller类不存在");
			return;
		}
		destPath = AppVar.appPath + ".." + File.separator + ".." + File.separator + srcPath + File.separator
				+ controllerClassPath;
		System.out.println(destPath);
		destDir = new File(destPath);
		FileUtils.copyFileToDirectory(sourceFile, destDir, true);
		// 6-page
		sourcePath = AppVar.appPath + File.separator + GeneratorUtils.PATH_GENERATOR + File.separator
				+ StringUtils.toCamelCase(tableName) + "Page.jsp";
		sourceFile = new File(sourcePath);
		if (sourceFile.exists() == false) {
			System.out.println("page不存在");
			return;
		}
		destPath = AppVar.appPath + ".." + File.separator + ".." + File.separator + webPath + File.separator
				+ parentCateogry;
		System.out.println(destPath);
		destDir = new File(destPath);
		FileUtils.copyFileToDirectory(sourceFile, destDir, true);
	}

	public static void main(String[] args) throws Exception {
		
		// 1-预加载
		AppVar.appPath=ClassUtils.getDefaultClassLoader().getResource("").getPath();
		LogUtils.logInfoAndConsole("代码生成程序路径:"+AppVar.appPath);
		AppVar.context=SpringApplication.run(MvcGenerator.class, args);
		
		// 2-开始应用程序
		SqlSessionFactory session = AppVar.context.getBean(SqlSessionFactory.class);
		MetaDataDao metaDataDao = AppVar.context.getBean(MetaDataDao.class);
		Connection conn = metaDataDao.getConnection();
		// 3-需要设置信息
		String databaseName="tissuemanage"; //数据库名称
		String parentCategory = "task"; // 包名称
		String tableName = "DehydrateExec"; // 表名称(实体名称)
		String indexName = "dehydrateExecId"; // 主键(对象标识)
		String modelClassPath = "lj.model." + parentCategory;
		String mapperInterfacePath = "lj.mapper." + parentCategory;
		String serviceClassPath = "lj.service." + parentCategory;
		String controllerClassPath = "lj.controller." + parentCategory;
		String requestUrl = "../" + parentCategory + "/" + StringUtils.toVarName(tableName);
//		GeneratorUtils.generatorEntityClass(metaDataDao, modelClassPath, tableName);
//		GeneratorUtils.generatorMapperInterface(metaDataDao, mapperInterfacePath, tableName, modelClassPath);
//		GeneratorUtils.generatorMapperXml(metaDataDao, mapperInterfacePath, tableName, indexName);
//		GeneratorUtils.generateServiceCode(metaDataDao, tableName, indexName, mapperInterfacePath, serviceClassPath,
//				modelClassPath);
//		GeneratorUtils.generateControllerClass(metaDataDao, modelClassPath, serviceClassPath, controllerClassPath,
//				tableName, indexName, parentCateogry);
//		GeneratorUtils.generatePageList(metaDataDao, tableName, indexName, requestUrl);

		// 4-生成C#模型
//		String csPath = AppVar.appPath + File.separator + GeneratorUtils.PATH_GENERATOR_CS;
//		String nameSpace = "Model." + StringUtils.toTypeName(parentCateogry);
//		GeneratorUtils.generatorEntityClassCs(metaDataDao, nameSpace, tableName, indexName, csPath);
		
		//6-生成Qt代码
		String qtPath = AppVar.appPath + File.separator + GeneratorUtils.PATH_GENERATOR_QT;
		GeneratorUtils.generatorEntityCodeQt(metaDataDao, parentCategory, databaseName,tableName, indexName, qtPath);
		GeneratorUtils.generateServiceCodeQt(metaDataDao, parentCategory, databaseName,tableName, indexName, qtPath);

		// 9-拷贝
		//copy(parentCateogry, tableName);
		LogUtils.logErrorAndConsole("生成代码成功");
	}

}
