package lj.util;

import java.io.File;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lj.dao.base.MetaDataDao;
import lj.global.AppVar;


public class GeneratorUtils {

	private final static String TEMPLATE_CLASSENTITY = "EntityClass.ftl";
	private final static String TEMPLATE_CLASSENTITY_CS = "EntityClassCs.ftl";
	private final static String TEMPLATE_CLASSENTITY_QT = "EntityClassQt.ftl";

	private final static String TEMPLATE_DAO_INTERFACE = "EntityDaoInterface.ftl";
	private final static String TEMPLATE_DAO_INTERFACE_ORACLE = "EntityDaoInterfaceOracle.ftl";
	
	private final static String TEMPLATE_MAPPER_INTERFACE="EntityMapperInterface.ftl";
	private final static String TEMPLATE_MAPPER_XML="EntityMapperXml.ftl";

	private final static String TEMPLATE_DAO = "EntityDao.ftl";
	private final static String TEMPLATE_DAO_ORACLE = "EntityDaoOracle.ftl";

	private final static String TEMPLAGE_PAGE_LIST = "PageList.ftl";
	private final static String TEMPLATE_SERVICE = "ServiceClass.ftl";
	private final static String TEMPLATE_SERVICE_QT_H   = "ServiceClassQtH.ftl";
	private final static String TEMPLATE_SERVICE_QT_CPP = "ServiceClassQtCpp.ftl";
	private final static String TEMPLATE_SERVICE_ORACLE = "ServiceClassOracle.ftl";

	private final static String TEMPLAGE_CONTROLLER_CLASS = "ControllerClass.ftl";
	public final static String PATH_GENERATOR = "generator";
	public final static String PATH_GENERATOR_JAVA = PATH_GENERATOR + File.separator + "java";
	public final static String PATH_GENERATOR_CS = PATH_GENERATOR + File.separator + "cs";
	public final static String PATH_GENERATOR_QT = PATH_GENERATOR + File.separator + "qt";
	private final static String SPLIT_STR = ";";
	private final static Logger logger = LoggerFactory.getLogger(GeneratorUtils.class);

	private GeneratorUtils() {
	}


	

	/***
	 * 产生实体类代码
	 * 
	 * @param tableName
	 * @param entityClassPath
	 * @return
	 */
	public static void generatorEntityClass(MetaDataDao metaDataDao,String packageName, String tableName) {
		String generatDir =AppVar.appPath  + PATH_GENERATOR_JAVA;
		System.out.println("generatorEntityClass generatDir:"+generatDir);
		File dirGenerateDir = new File(generatDir);
		if (dirGenerateDir.exists() == false)
			if (dirGenerateDir.mkdirs() == false)
				return;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("packageName", packageName);
		map.put("className", StringUtils.toTypeName(tableName));
		List<BeanMeta> fields = generateFields(metaDataDao,tableName);
		map.put("fields", fields);
		String outputPath =generatDir
		    + File.separator + StringUtils.toTypeName(tableName) + ".java";
		System.out.println("generatorEntityClass output path:"+outputPath);
		FreeMarkerUtils.generateFile(TEMPLATE_CLASSENTITY, outputPath, map);

	}

	/**
	 * 删除C#的模型类
	 * 
	 * @param packageName
	 * @param tableName
	 * @param generatDir
	 */
	public static void generatorEntityClassCs(MetaDataDao metaDataDao,String parentCateogry, String tableName, String indexName,
			String generatDir) {
		File dirGenerateDir = new File(generatDir);
		if (dirGenerateDir.exists() == false)
			if (dirGenerateDir.mkdirs() == false)
				return;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentCateogry", parentCateogry);
		map.put("className", StringUtils.toTypeName(tableName));
		map.put("indexName", indexName);
		List<BeanMeta> fields = generateFields(metaDataDao,tableName);
		// Java类型转CS类型
		for (BeanMeta bean : fields)
			if (bean.getBeanType().equals("Long") == true)
				bean.setBeanType("long");
			else if (bean.getBeanType().equals("Date") == true)
				bean.setBeanType("DateTime");
		map.put("fields", fields);
		String outputPath = generatDir + File.separator + StringUtils.toTypeName(tableName) + ".cs";

		// System.out.println("generatorEntityClass output path:"+outputPath);
		FreeMarkerUtils.generateFile(TEMPLATE_CLASSENTITY_CS, outputPath, map);

	}
	
	/**
	 * 产生QT实体diamond
	 * @param metaDataDao
	 * @param nameSpace
	 * @param tableName
	 * @param indexName
	 * @param generatDir
	 */
	public static void generatorEntityCodeQt(MetaDataDao metaDataDao,String parentCategory, String databaseName,String tableName, String indexName,
			String generatDir) {
		File dirGenerateDir = new File(generatDir+File.separator
				+"model"+File.separator+parentCategory);
		if (dirGenerateDir.exists() == false)
			if (dirGenerateDir.mkdirs() == false)
				return;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentCategory", parentCategory);
		map.put("className", StringUtils.toTypeName(tableName));
		map.put("classNameUpper", tableName.toUpperCase());
		String tableComment=MySqlUtils.getTableComment(metaDataDao.getConnection(), databaseName, tableName);
		map.put("classComment", tableComment);
		map.put("indexName", indexName);
		List<BeanMeta> fields = generateFields(metaDataDao,tableName);
		// Java类型转Qt类型
		for (BeanMeta bean : fields)
			if (bean.getBeanType().equals("Long") || bean.getBeanType().equals("Integer") )
				bean.setBeanType("int");
			else if (bean.getBeanType().equals("Double") == true)
		    	bean.setBeanType("double");
			else if (bean.getBeanType().equals("String") == true)
				bean.setBeanType("QString");
			else if (bean.getBeanType().equals("Date") == true)
				bean.setBeanType("QDateTime");
		map.put("fields", fields);
		String outputPath = generatDir + File.separator +"model"+File.separator 
				+parentCategory+File.separator+tableName.toLowerCase() + ".h";

		// System.out.println("generatorEntityClass output path:"+outputPath);
		FreeMarkerUtils.generateFile(TEMPLATE_CLASSENTITY_QT, outputPath, map);
	}

	/**
	 * 产生表/视图的字段信息
	 * 
	 * @param entityName
	 * @return
	 */
	private static List<BeanMeta> generateFields(MetaDataDao metaDataDao,String entityName) {
		List<BeanMeta> fields = new ArrayList<BeanMeta>();
		System.out.println("GeneratorUtils.generateFields entityName:"+entityName);
		ResultSetMetaData meta = metaDataDao.getTableOrViewMetaData(entityName);
		try {
			for (int i = 0; i < meta.getColumnCount(); i++) {
				String columnName = meta.getColumnName(i + 1);
				
				int jdbcType = meta.getColumnType(i + 1);
				int precision = meta.getPrecision(i + 1);
				int scale = meta.getScale(i + 1);
				String columnTypeName = meta.getColumnTypeName(i + 1);
				Class fieldClass = DbUtils.jdbcTypeToJavaClass(jdbcType, scale);
				String getterName = "get" + StringUtils.toTypeName(columnName);
				String setterName = "set" + StringUtils.toTypeName(columnName);
				String myBaitsType=DbUtils.jdbcTypeToMyBatisType(jdbcType,scale);
				BeanMeta fieldMeta = new BeanMeta(fieldClass.getSimpleName(), StringUtils.toVarName(columnName),StringUtils.toVarName(columnName),
							StringUtils.toTypeName(columnName), getterName, setterName,myBaitsType);
				fields.add(fieldMeta);
			}
			if(AppVar.databaseType.equals(DbUtils.DATABASE_TYPE_MYSQL))
				MySqlUtils.updateMetaData(metaDataDao.getConnection(), entityName, fields);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("generatorEntityClass:" + e);
		}
		if (AppVar.databaseType.equals(DbUtils.DATABASE_TYPE_ORACLE) == true) {
			if (OracleUtils.updateMetaData(metaDataDao.getConnection(), entityName, fields) == false)
				System.out.println("无法找到Oracle数据实体的注释信息,生成代码失败!");
		}
		else if (AppVar.databaseType.equals(DbUtils.DATABASE_TYPE_SQLSERVER) == true) {
			if (SqlServerUtils.updateMetaData(metaDataDao.getConnection(), entityName, fields) == false)
				System.out.println("无法找到Sql Server数据实体的注释信息,生成代码失败!");
		}
		for(BeanMeta bean:fields)
		{
			System.out.println("generateFields columnName:" + bean.getBeanName() +",columnTitle:" + bean.getBeanTitle()
                    + ",columnTypeName:" + bean.getBeanType()+",myBaitsType:"+bean.getMyBatisType());
		}
		return fields;
	}

	/**
	 * 产生查询字段的JavaBean信息
	 * 
	 * @param queryFields
	 * @return
	 */
	private static List<BeanMeta> getQueryFields(MetaDataDao metaDataDao,String tableName, String[] queryFields) {
		List<BeanMeta> optionalFields = generateFields(metaDataDao,tableName);
		List<BeanMeta> list = new ArrayList<BeanMeta>();
		for (String fieldName : queryFields)
			for (BeanMeta bean : optionalFields)
				if (bean.getBeanName().equals(fieldName) == true) {
					list.add(bean);
					break;
				}
		return list;
	}



	/***
	 * 
	 * @param packageName
	 * @param interfaceName
	 * @param modelClassPath
	 * @param modelClassName
	 */
	public static void generatorDaoInterface(MetaDataDao metaDataDao,String packageName, String tableName, String modelClassPath) {
		
		String generatePath = AppVar.appPath + File.separator + PATH_GENERATOR_JAVA;
		
		File dirGenerateDir = new File(generatePath);
		if (dirGenerateDir.exists() == false)
			if (dirGenerateDir.mkdirs() == false)
				return;

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("packageName", packageName);
		String interfaceName = "I" + StringUtils.toTypeName(tableName) + "Dao";
		variables.put("interfaceName", interfaceName);
		variables.put("modelClassPath", modelClassPath);
		variables.put("modelClassName", StringUtils.toTypeName(tableName));
		variables.put("queryFields", getQueryFields(metaDataDao,tableName, new String[0]));
		String outputPath = generatePath + File.separator + StringUtils.toTypeName(interfaceName) + ".java";
		if (AppVar.databaseType.equals(DbUtils.DATABASE_TYPE_MYSQL))
			FreeMarkerUtils.generateFile(TEMPLATE_DAO_INTERFACE, outputPath, variables);
		else if (AppVar.databaseType.equals(DbUtils.DATABASE_TYPE_ORACLE))
			FreeMarkerUtils.generateFile(TEMPLATE_DAO_INTERFACE_ORACLE, outputPath, variables);
	}

	public static void generatorDaoClass(MetaDataDao metaDataDao,String packageName, String tableName, String modelClassPath,
			String indexName) {
		String outputPath = AppVar.appPath + File.separator + PATH_GENERATOR_JAVA;
		generatorDaoClass(metaDataDao,packageName, tableName, modelClassPath, indexName, new String[0], outputPath);
	}

	public static void generatorDaoClass(MetaDataDao metaDataDao,String packageName, String tableName, String modelClassPath, String indexName,
			String[] queryFields, String generatePath) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("packageName", packageName);
		String interfaceName = "I" + StringUtils.toTypeName(tableName) + "Dao";
		String className = StringUtils.toTypeName(tableName) + "Dao";
		variables.put("className", className);
		variables.put("modelClassPath", modelClassPath);
		variables.put("modelClassName", StringUtils.toTypeName(tableName));
		variables.put("daoInterfaceName", interfaceName);
		variables.put("indexName", indexName);
		variables.put("indexNameGetter", "get" + StringUtils.toTypeName(indexName));
		variables.put("queryFields", getQueryFields(metaDataDao,tableName, queryFields));
		List<BeanMeta> fields = generateFields(metaDataDao,tableName);
		for (BeanMeta temp : fields)
			if (temp.getBeanName().toUpperCase().equals(indexName.toUpperCase()) == true) {
				fields.remove(temp);
				break;
			}
		variables.put("fields", fields);
		String outputPath = generatePath + File.separator + StringUtils.toTypeName(className) + ".java";
		if (AppVar.databaseType.equals(DbUtils.DATABASE_TYPE_MYSQL))
			FreeMarkerUtils.generateFile(TEMPLATE_DAO, outputPath, variables);
		else if (AppVar.databaseType.equals(DbUtils.DATABASE_TYPE_ORACLE))
			FreeMarkerUtils.generateFile(TEMPLATE_DAO_ORACLE, outputPath, variables);
	}


    /**
     * 产生服务代码
     * @param metaDataDao
     * @param tableName
     * @param indexName
     * @param mapperInterfacePath
     * @param serviceClassPath
     * @param modelClassPath
     */
	public static void generateServiceCode(MetaDataDao metaDataDao,String tableName, String indexName,String mapperInterfacePath, String serviceClassPath,
			String modelClassPath ) {
		
		String generatePath = AppVar.appPath + File.separator + PATH_GENERATOR_JAVA;
		File dirGenerateDir = new File(generatePath);
		if (dirGenerateDir.exists() == false)
			if (dirGenerateDir.mkdirs() == false)
				return;
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("modelClassName", StringUtils.toTypeName(tableName));
		variables.put("modelClassNameFS", StringUtils.toVarName(tableName));
		variables.put("mapperInterfacePath", mapperInterfacePath);
		variables.put("serviceClassPath", serviceClassPath);
		variables.put("modelClassPath", modelClassPath);
		variables.put("indexName", indexName);
		variables.put("indexNameGetter", "get" + StringUtils.toTypeName(indexName));
		variables.put("queryFields", getQueryFields(metaDataDao,tableName, new String[0]));
		String outputPath = generatePath + File.separator + StringUtils.toTypeName(tableName) + "Service.java";
		FreeMarkerUtils.generateFile(TEMPLATE_SERVICE, outputPath, variables);
	}
	
	/**
	 * 产生QT服务代码
	 * @param metaDataDao
	 * @param nameSpace
	 * @param tableName
	 * @param indexName
	 * @param generatDir
	 */
	public static void generateServiceCodeQt(MetaDataDao metaDataDao,String parentCategory, String databaseName,String tableName, String indexName,
			String generatDir) {
		File dirGenerateDir = new File(generatDir+File.separator
				+"service"+File.separator+parentCategory);
		if (dirGenerateDir.exists() == false)
			if (dirGenerateDir.mkdirs() == false)
				return;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentCategory", parentCategory);
		map.put("className", StringUtils.toTypeName(tableName));
		map.put("classNameUpper", tableName.toUpperCase());
		map.put("classNameLower", tableName.toLowerCase());
		String tableComment=MySqlUtils.getTableComment(metaDataDao.getConnection(), databaseName, tableName);
		map.put("classComment", tableComment);
		map.put("indexName", indexName);
		List<BeanMeta> fields = generateFields(metaDataDao,tableName);
		// Java类型转Qt类型
		for (BeanMeta bean : fields)
			if (bean.getBeanType().equals("Long") || bean.getBeanType().equals("Integer") )
				bean.setBeanType("int");
			else if (bean.getBeanType().equals("Double") == true)
		    	bean.setBeanType("double");
			else if (bean.getBeanType().equals("String") == true)
				bean.setBeanType("QString");
			else if (bean.getBeanType().equals("Date") == true)
				bean.setBeanType("QDateTime");
		map.put("fields", fields);
		String outputPath = generatDir + File.separator +"service"+File.separator 
				+parentCategory+File.separator+tableName.toLowerCase() + "service.h";
		FreeMarkerUtils.generateFile(TEMPLATE_SERVICE_QT_H, outputPath, map);
		outputPath = generatDir + File.separator +"service"+File.separator 
				+parentCategory+File.separator+tableName.toLowerCase() + "service.cpp";
		FreeMarkerUtils.generateFile(TEMPLATE_SERVICE_QT_CPP, outputPath, map);
	}

	public static void generateControllerClass(MetaDataDao metaDataDao,String modelClassPath, String serviceClassPath,
			String controllerClassPath, String tableName, String indexName, String parentContextUrl) {

		String outputPath = AppVar.appPath + File.separator + PATH_GENERATOR_JAVA;
		generateControllerClass(metaDataDao,modelClassPath, serviceClassPath, controllerClassPath, tableName, indexName,
				parentContextUrl, new String[0], outputPath);
	}

	/**
	 * 
	 * @param modelClassPath
	 * @param serviceClassPath
	 * @param controllerClassPath
	 * @param tableName
	 * @param indexName
	 * @param parentContextUrl
	 * @param queryFields
	 * @param generatePath
	 */
	public static void generateControllerClass(MetaDataDao metaDataDao,String modelClassPath, String serviceClassPath,
			String controllerClassPath, String tableName, String indexName, String parentContextUrl,
			String[] queryFields, String generatePath) {
		File dirGenerateDir = new File(generatePath);
		if (dirGenerateDir.exists() == false)
			if (dirGenerateDir.mkdirs() == false)
				return;
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("controllerClassPath", controllerClassPath);
		variables.put("serviceClassPath", serviceClassPath);
		variables.put("modelClassPath", modelClassPath);
		variables.put("modelClassName", StringUtils.toTypeName(tableName));
		variables.put("indexName", indexName);
		variables.put("modelClassNameFS", StringUtils.toVarName(tableName));
		variables.put("parentContextUrl", parentContextUrl);
		variables.put("queryFields", getQueryFields(metaDataDao,tableName, queryFields));
		String outputPath = generatePath + File.separator + StringUtils.toTypeName(tableName) + "Controller.java";
		FreeMarkerUtils.generateFile(TEMPLAGE_CONTROLLER_CLASS, outputPath, variables);
	}


	public static void generatePageList(MetaDataDao metaDataDao,String tableName, String indexName, String contextUrl) {
		String generatePath = AppVar.appPath + File.separator + PATH_GENERATOR;
		File dirGenerateDir = new File(generatePath);
		if (dirGenerateDir.exists() == false)
			if (dirGenerateDir.mkdirs() == false)
				return;
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("indexName", indexName);
		variables.put("indexNameFB", StringUtils.toTypeName(indexName));
		variables.put("modelClassName", StringUtils.toTypeName(tableName));
		variables.put("modelClassNameFS", StringUtils.toVarName(tableName));

		variables.put("getAllUrl", contextUrl + "/");
		variables.put("getUrl", contextUrl + "/");
		variables.put("putUrl", contextUrl + "/");
		variables.put("postUrl", contextUrl + "/");
		variables.put("deleteUrl", contextUrl + "/");
		variables.put("queryFields", getQueryFields(metaDataDao,tableName, new String[0]));
		List<BeanMeta> fields = generateFields(metaDataDao,tableName);
		for (BeanMeta temp : fields)
			if (temp.getBeanName().toUpperCase().equals(indexName.toUpperCase()) == true) {
				fields.remove(temp);
				break;
			}
		variables.put("fields", fields);
		String outputPath = generatePath + File.separator + StringUtils.toCamelCase(tableName) + "Page.jsp";
		FreeMarkerUtils.generateFile(TEMPLAGE_PAGE_LIST, outputPath, variables);
	}
	
	
	/***
	 * 查询Mapper接口
	 * @param packageName
	 * @param interfaceName
	 * @param modelClassPath
	 * @param modelClassName
	 */
	public static void generatorMapperInterface(MetaDataDao metaDataDao,String packageName, String tableName, String modelClassPath) {
        String generatePath = AppVar.appPath + File.separator + PATH_GENERATOR_JAVA;
		
		File dirGenerateDir = new File(generatePath);
		if (dirGenerateDir.exists() == false)
			if (dirGenerateDir.mkdirs() == false)
				return;
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("packageName", packageName);
		String interfaceName = StringUtils.toTypeName(tableName) + "Mapper";
		variables.put("interfaceName", interfaceName);
		variables.put("modelClassPath", modelClassPath);
		variables.put("modelClassName", StringUtils.toTypeName(tableName));
		variables.put("queryFields", getQueryFields(metaDataDao,tableName, new String[0]));
		String outputPath = generatePath + File.separator + StringUtils.toTypeName(interfaceName) + ".java";
		FreeMarkerUtils.generateFile(TEMPLATE_MAPPER_INTERFACE, outputPath, variables);
	}
	
	/***
	 * 产生实体类代码
	 * 
	 * @param tableName
	 * @param entityClassPath
	 * @return
	 */
	public static void generatorMapperXml(MetaDataDao metaDataDao,String mapperInterfacePath, String tableName,String indexName) {
		String generatDir =AppVar.appPath + File.separator + PATH_GENERATOR_JAVA;
		System.out.println("generatorMapperXml generatDir:"+generatDir);
		File dirGenerateDir = new File(generatDir);
		if (dirGenerateDir.exists() == false)
			if (dirGenerateDir.mkdirs() == false)
				return;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mapperInterfacePath", mapperInterfacePath);
		map.put("className", StringUtils.toTypeName(tableName));
		map.put("modelClassName", StringUtils.toTypeName(tableName));
		map.put("indexName", indexName);
		List<BeanMeta> fields = generateFields(metaDataDao,tableName);
		map.put("fields", fields);
		String outputPath =generatDir
		    + File.separator + StringUtils.toTypeName(tableName) + "Mapper.xml";
		System.out.println("generatorMapperInterface output path:"+outputPath);
		FreeMarkerUtils.generateFile(TEMPLATE_MAPPER_XML, outputPath, map);

	}
	

}
