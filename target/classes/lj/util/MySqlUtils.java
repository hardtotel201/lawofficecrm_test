package lj.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class MySqlUtils {
	
	public final static String SQL_NEW_ID="select LAST_INSERT_ID() as newId";
	
	public final static String SQL_NEW_TIME="select now() as nowTime";

	/**
	 * 增加注释信息到元数据
	 * @param conn
	 * @param entityName
	 * @param fields
	 * @return
	 */
	public static boolean updateMetaData(Connection conn, String entityName, List<BeanMeta> fields) {
		if (conn == null)
			return false;
		Statement st = null;
		ResultSet rs = null;
		try {
			String sql = "show full columns from "+entityName;
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next() == true) {
				String columnName =rs.getString("Field");
				String columnTitle=rs.getString("Comment");
				fields.forEach(obj->{
					if(obj.getBeanName().equalsIgnoreCase(columnName))
					{
						obj.setBeanTitle(columnTitle);
						//return;
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DbUtils.close(rs, st, conn);
		}
		return true;
	}
	
	/**
	 * 获得实体注释
	 * @param conn
	 * @param databaseName
	 * @param entityName
	 * @return
	 */
	public static String getTableComment(Connection conn,String databaseName,String entityName) {
		String sql="select TABLE_COMMENT from information_schema.TABLES where TABLE_SCHEMA='"+databaseName
				+"' and TABLE_NAME='"+entityName+"'";
		try(Statement st=conn.createStatement();
		    ResultSet rs=st.executeQuery(sql);){
			if (rs.next() == true) {
				String tableComment=rs.getString("TABLE_COMMENT");
				return tableComment;
			}
			else
				return "";
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	
}
