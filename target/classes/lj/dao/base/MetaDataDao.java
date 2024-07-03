package lj.dao.base;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lj.global.AppConst;
import lj.util.DbUtils;
import lj.util.LogUtils;
import lj.util.StringUtils;

/**
 * 数据库元数据数据访问类
 * @author samlv
 *
 */
@Repository
public class MetaDataDao {
	@Autowired
    private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 获得底层JDBC连接
	 * @return
	 */
	public Connection getConnection() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
        Connection  conn= sqlSession.getConnection();
		return conn;
	}
	
	/**
	 * 查询数据表或者视图元数组
	 * 
	 * @param tableName
	 * @return
	 */
	public ResultSetMetaData getTableOrViewMetaData(String tableName) {
		if (StringUtils.isNullOrEmpty(AppConst.DATABSE_TYPE) == true)
			return null;
		String sql = StringUtils.STR_EMPTY;
		if (AppConst.DATABSE_TYPE.equals(DbUtils.DATABASE_TYPE_MYSQL) == true)
			sql = "select * from " + tableName + " limit 0,0";
		else if (AppConst.DATABSE_TYPE.equals(DbUtils.DATABASE_TYPE_SQLSERVER) == true)
			sql = "select top 0 * from " + tableName;
		else if (AppConst.DATABSE_TYPE.equals(DbUtils.DATABASE_TYPE_ORACLE) == true)
			sql = "select * from " + tableName + " where rownum<1";
		else
			return null;
		Connection conn = this.getConnection();
		if (conn == null)
			return null;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			return rs.getMetaData();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.logError("getResultSetMetaData:" + e);
			return null;
		} finally {
			try {
				// conn.close();
			} catch (Exception innerEx) {
				innerEx.printStackTrace();
				LogUtils.logError("getResultSetMetaData:" + innerEx);
			}
		}
	}
	
	

}
