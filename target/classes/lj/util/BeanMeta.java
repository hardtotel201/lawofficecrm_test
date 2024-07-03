package lj.util;

/***
 * MetaData of Bean
 * @author samlv
 *
 */
public class BeanMeta {
	private String beanType;    //类型
	private String beanName;    //名称
	private String beanTitle;   //标题
	private String beanNameFB;  //首字母大写
	private String getterName;  //getter方法
	private String setterName;  //setter方法
	private String myBatisType; //MyBatis类型
	
	public String getBeanType() {
		return beanType;
	}
	public void setBeanType(String beanType) {
		this.beanType = beanType;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getBeanTitle() {
		return beanTitle;
	}
	public void setBeanTitle(String beanTitle) {
		this.beanTitle = beanTitle;
	}
	public String getBeanNameFB() {
		return beanNameFB;
	}
	public void setBeanNameFB(String beanNameFB) {
		this.beanNameFB = beanNameFB;
	}
	public String getGetterName() {
		return getterName;
	}
	public void setGetterName(String getterName) {
		this.getterName = getterName;
	}
	public String getSetterName() {
		return setterName;
	}
	public void setSetterName(String setterName) {
		this.setterName = setterName;
	}
	public String getMyBatisType() {
		return myBatisType;
	}
	public void setMyBatisType(String myBatisType) {
		this.myBatisType = myBatisType;
	}
	
	public BeanMeta(String beanType, String beanName,String beanTitle, String beanNameFB, 
			String getterName, String setterName,String myBatisType) {
		super();
		this.beanType = beanType;
		this.beanName = beanName;
		this.beanTitle= beanTitle;
		this.beanNameFB = beanNameFB;
		this.getterName = getterName;
		this.setterName = setterName;
		this.myBatisType=myBatisType;
	}
	
	public BeanMeta() {
		super();
	}
	

}
