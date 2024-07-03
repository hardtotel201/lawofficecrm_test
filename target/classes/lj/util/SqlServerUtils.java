package lj.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class SqlServerUtils {

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
			String sql = "select syscolumns.name as columnName,syscolumns.colid,sys.extended_properties.* from sys.extended_properties \r\n" + 
					"left join syscolumns on syscolumns.id=sys.extended_properties.major_id and syscolumns.colid=minor_id\r\n" + 
					"where major_id = object_id ('"+entityName+"') and minor_id>0;";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next() == true) {
				String columnName =rs.getString("columnName");
				String columnTitle=rs.getString("value");
				fields.forEach(obj->{
					if(obj.getBeanName().equalsIgnoreCase(columnName))
						obj.setBeanTitle(columnTitle);
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
	
}
