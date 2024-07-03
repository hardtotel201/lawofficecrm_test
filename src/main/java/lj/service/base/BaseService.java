package lj.service.base;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import lj.global.AppConst;
import lj.global.AppVar;
import lj.util.DbUtils;
import lj.util.MySqlUtils;
import lj.util.OracleUtils;
import lj.util.StringUtils;

@Service
public class BaseService {
	
	
	@Autowired
	Environment environment;

	/**
	 * 获得微服务信息
	 * @return
	 */
	public String getServerInfo() {
		String ip = "";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String port = environment.getProperty("server.port");
		return ip + ":" + port;
	}
	
	/**
	 * 获得当前时间
	 * @return
	 */
	public Date getNowTime()
	{
		
		JdbcTemplate jdbcTemplate=AppVar.context.getBean("jdbcTemplate", JdbcTemplate.class);
		String sql = null;
		if (AppConst.DATABSE_TYPE.equals(DbUtils.DATABASE_TYPE_MYSQL) == true)
			sql = MySqlUtils.SQL_NEW_TIME;
		else if(AppConst.DATABSE_TYPE.equals(DbUtils.DATABASE_TYPE_ORACLE)==true)
			sql=OracleUtils.SQL_NEW_TIME;
		else
			return new Date();
		SqlRowSet idRow = jdbcTemplate.queryForRowSet(sql);
		if (idRow == null || idRow.next() == false)
			return new Date();
		Date date = new Date(idRow.getDate(DbUtils.COLUMN_NOW_TIME).getTime());
		return date;
	}


}
