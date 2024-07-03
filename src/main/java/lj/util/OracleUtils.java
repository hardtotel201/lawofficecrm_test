package lj.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class OracleUtils {
	public final static String SQL_NEW_TIME = "select sysdate as nowTime  from dual";
	public final static String ORACLE_DATETIME_FORMAT = "YYYY-MM-DD HH24:MI:SS";
	public final static String FORMAT_ORACLE_DAY = "yyyy-mm-dd";
	public final static String FORMAT_ORACLE_MONTH = "yyyy-mm";
	public final static String FORMAT_ORACLE_YEAR = "yyyy";

	/**
	 * 增加注释信息到元数据
	 * 
	 * @param conn
	 * @param entityName
	 * @param fields
	 */
	public static boolean updateMetaData(Connection conn, String entityName, List<BeanMeta> fields) {

		if (conn == null)
			return false;
		Statement st = null;
		ResultSet rs = null;
		try {
			String sql = "select * from user_col_comments where TABLE_NAME='" + entityName.toUpperCase() + "'";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next() == true) {
				int index = indexOfInsensitive(fields, rs.getString("COLUMN_NAME"));
				if (index < 0)
					return false;
				else {
					String columnName = rs.getString("COMMENTS");
					String getterName = "get" + StringUtils.toTypeName(columnName);
					String setterName = "set" + StringUtils.toTypeName(columnName);
					if (StringUtils.isNullOrEmpty(columnName) == true)
						return false;
					else {
						fields.get(index).setBeanName(columnName);
						fields.get(index).setBeanNameFB(StringUtils.toTypeName(columnName));
						fields.get(index).setGetterName(getterName);
						fields.get(index).setSetterName(setterName);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.logError("addCommentToMetaData:" + e);
			return false;
		} finally {
			DbUtils.close(rs, st, conn);
		}
		return true;
	}

	/**
	 * 
	 * @param list
	 * @param target
	 * @return
	 */
	private static int indexOfInsensitive(List<BeanMeta> fields, String targetFieldName) {
		for (int i = 0; i < fields.size(); ++i) {
			String fieldName = fields.get(i).getBeanName();
			if (fieldName.toUpperCase().equals(targetFieldName.toUpperCase()) == true)
				return i;
		}
		return -1;
	}

	/**
	 * 将时间转成Oracle SQL参数
	 * @param dt
	 * @return
	 */
	public static String toOracleSqlDateTime(Date dt) {
		String str = "to_date";
		str = str + "('" + StringUtils.timeToString(dt, StringUtils.DATETIME_FORMAT) + "',";
		str = str + "'" + ORACLE_DATETIME_FORMAT + "')";
		return str;
	}
}
