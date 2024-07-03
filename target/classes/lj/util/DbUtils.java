package lj.util;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import lj.util.query.DateCondition;
import lj.util.query.DoubleCondition;
import lj.util.query.IntCondition;
import lj.util.query.LongCondition;

public class DbUtils {
	public final static String DATABASE_TYPE_MYSQL = "mysql";
	public final static String DATABASE_TYPE_SQLSERVER = "sqlServer";
	public final static String DATABASE_TYPE_ORACLE = "oracle";

	public static Class JAVA_TYPE_LONG = Long.class;
	public static Class JAVA_TYPE_INT = Integer.class;
	public static Class JAVA_TYPE_DOUBLE = Double.class;
	public static Class JAVA_TYPE_STRING = String.class;
	public static Class JAVA_TYPE_DATE = Date.class;
	public final static String QUERY_POSTFIX_BEGIN = "Begin";
	public final static String QUERY_POSTFIX_END = "End";

	public final static String COLUMN_NEW_ID = "newId";
	public final static String COLUMN_NOW_TIME = "nowTime";

	// 禁止实例对象
	private DbUtils() {
	}

	/**
	 * jdbc type->java class
	 * 
	 * @param jdbcType
	 * @return
	 */
	public static Class jdbcTypeToJavaClass(int jdbcType,int scale) {
		if (jdbcType == Types.INTEGER)
			return Integer.class;
		else if (jdbcType == Types.TINYINT || jdbcType == Types.BIGINT)
			return Long.class;
		else if (jdbcType == Types.DECIMAL || jdbcType == Types.DOUBLE)
			return Double.class;
		else if(jdbcType==Types.NUMERIC) {
			if(scale>0)
				return Double.class;
			else
				return Long.class;
		}
		else if (jdbcType == Types.DATE || jdbcType == Types.TIMESTAMP)
			return Date.class;
		else
			return String.class;
	}
	
	/**
	 * JDBC->MyBatis类型
	 * @param jdbcType
	 * @param scale
	 * @return
	 */
	public static String jdbcTypeToMyBatisType(int jdbcType,int scale) {
		if (jdbcType == Types.INTEGER)
			return "NUMERIC";
		else if (jdbcType == Types.TINYINT || jdbcType == Types.BIGINT)
			return "NUMERIC";
		else if (jdbcType == Types.DECIMAL || jdbcType == Types.DOUBLE)
			return "NUMERIC";
		else if(jdbcType==Types.NUMERIC) {
			if(scale>0)
				return "NUMERIC";
			else
				return "NUMERIC";
		}
		else if (jdbcType == Types.DATE || jdbcType == Types.TIMESTAMP)
			return "TIMESTAMP";
		else
			return "VARCHAR";
	}
	
	/**
	 * 关闭数据集/语句/连接对象
	 * @param rs
	 * @param st
	 * @param conn
	 */
	public static void close(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据参数名称和参数值生产SQL条件语句
	 * 
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public static String generateConditionSql(String paramName, Object paramValue) {

		String str = StringUtils.STR_EMPTY;
		// 字符串查询条件
		if (paramValue instanceof String) {
			String con = (String) paramValue;
			if (StringUtils.isNullOrEmpty(con) == false)
				str = str + paramName + " like :" + paramName;
		}
		// 整数查询条件
		else if (paramValue instanceof IntCondition) {
			IntCondition con = (IntCondition) paramValue;
			if (con.beginValue != null)
				str = str + paramName + ">=:" + paramName + QUERY_POSTFIX_BEGIN;
			if (con.endValue != null) {
				if (con.beginValue != null)
					str = str + " and ";
				str = str + paramName + "<:" + paramName + QUERY_POSTFIX_END;
			}
		}
		// 长整数查询条件
		else if (paramValue instanceof LongCondition) {
			LongCondition con = (LongCondition) paramValue;
			if (con.beginValue != null)
				str = str + paramName + ">=:" + paramName + QUERY_POSTFIX_BEGIN;
			if (con.endValue != null) {
				if (con.beginValue != null)
					str = str + " and ";
				str = str + paramName + "<:" + paramName + QUERY_POSTFIX_END;
			}
		}
		// 浮点数查询条件
		else if (paramValue instanceof DoubleCondition) {
			DoubleCondition con = (DoubleCondition) paramValue;
			if (con.beginValue != null)
				str = str + paramName + ">=:" + paramName + QUERY_POSTFIX_BEGIN;
			if (con.endValue != null) {
				if (con.beginValue != null)
					str = str + " and ";
				str = str + paramName + "<:" + paramName + QUERY_POSTFIX_END;
			}
		}
		// 时间查询条件
		else if (paramValue instanceof DateCondition) {
			DateCondition con = (DateCondition) paramValue;
			if (con.beginValue != null)
				str = str + paramName + ">=:" + paramName + QUERY_POSTFIX_BEGIN;
			if (con.endValue != null) {
				if (con.beginValue != null)
					str = str + " and ";
				str = str + paramName + "<:" + paramName + QUERY_POSTFIX_END;
			}
		}
		// 数组
		else if (paramValue instanceof Object[]) {
			Object[] ary = (Object[]) paramValue;
			if (ary.length > 0)
				str = str + paramName + " in (:" + paramName + " )";
		}
		// 列表
		else if (paramValue instanceof List) {
			List list = (List) paramValue;
			if (list.size() > 0)
				str = str + paramName + " in (:" + paramName + " )";
		} else {
			str = str + paramName + "=:" + paramName;
		}
		return str;
	}

}
