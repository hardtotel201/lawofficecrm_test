package lj.model.sys;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 系统静态配置
 * @author samlv
 *
 */
@Component
@PropertySource("classpath:static-config.properties")
@ConfigurationProperties(prefix="lj.model.sys.staticconfig")
public class StaticConfig {
	private String uploadDir;             //系统上传目录
	private String defaultPassword;       //系统默认密码
	private String systemName;            //系统名称
	private String companyName;           //公司名称

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
