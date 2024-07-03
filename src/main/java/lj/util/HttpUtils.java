package lj.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	private final static int PACKAGE_SIZE=4*1024;    //4K
	private final static int NORMAL_SIZE=10*1024*1024; //10M
	private final static int MAX_SIZE=100*1024*1024; //100M
	
	/**
	 * HTTP GET字节流
	 * @param url
	 * @param headers
	 * @return
	 */
	public static byte[] doGetByteArray(String url, Map<String, String> headers) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet requestGet = new HttpGet(url);
		if (headers != null)
			for (Map.Entry<String, String> entry : headers.entrySet())
				requestGet.addHeader(entry.getKey(), entry.getValue());
		try(CloseableHttpResponse response =client.execute(requestGet);
			InputStream is=response.getEntity().getContent();){
			byte[] packet=new byte[PACKAGE_SIZE];
			byte[] data=new byte[NORMAL_SIZE];
			int count=0,length=-1;
			while((length=is.read(packet))!=-1)
			{
				System.arraycopy(packet, 0, data, count,length);
				count=count+length;
			}
			byte[] retData=new byte[count];
			System.arraycopy(data, 0, retData, 0,count);
			return retData;
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url, Map<String, String> headers) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet requestGet = new HttpGet(url);
		if (headers != null)
			for (Map.Entry<String, String> entry : headers.entrySet())
				requestGet.addHeader(entry.getKey(), entry.getValue());
		CloseableHttpResponse response = null;
		try {
			response = client.execute(requestGet);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			String str = FileUtils.inputStreamtoString(is);
			is.close();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (Exception inEx) {
					inEx.printStackTrace();
				}
			if (client != null)
				try {
					client.close();
				} catch (Exception inEx) {
					inEx.printStackTrace();
				}
		}
	}

	/**
	 * 发送post请求(UrlEncodedFormEntity)
	 * 
	 * @param url
	 */
	public static String doPost(String url, Map<String, String> headers, Map<String, String> postParams) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		if (headers != null)
			for (Map.Entry<String, String> entry : headers.entrySet())
				httpPost.addHeader(entry.getKey(), entry.getValue());
		List<NameValuePair> entityParams = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : postParams.entrySet())
			entityParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

		CloseableHttpResponse response = null;
		try {
			HttpEntity requestEntity = new UrlEncodedFormEntity(entityParams);
			httpPost.setEntity(requestEntity);
			response = client.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			final int statusCode = response.getStatusLine().getStatusCode();
			String str="";
			if(statusCode==200)
				str=EntityUtils.toString(responseEntity);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (Exception inEx) {
					inEx.printStackTrace();
				}
			if (client != null)
				try {
					client.close();
				} catch (Exception inEx) {
					inEx.printStackTrace();
				}
		}
	}
	
	
	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param filePath
	 * @return
	 */
	public static boolean doDownLoad(String url, String filePath) {
		try(OutputStream os=new FileOutputStream(filePath)){
			boolean ret=doDownLoad(url,os);
			return ret;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	
	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param filePath
	 * @return
	 */
	public static boolean doDownLoad(String url,OutputStream os) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet requestGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(requestGet);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) > 0) {
				// System.out.println("len:"+len);
				os.write(buffer, 0, len);
			}
			is.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (Exception inEx) {
					inEx.printStackTrace();
				}
			if (client != null)
				try {
					client.close();
				} catch (Exception inEx) {
					inEx.printStackTrace();
				}
		}
	}

	/**
	 * 发送REST GET请求
	 * 
	 * @param url
	 * @return
	 */
	public static String doGetRest(String url) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		String ret = doGet(url, headers);
		return ret;
	}

	/**
	 * 
	 * @param url
	 * @param obj
	 * @return
	 */
	public static String doPostRest(String url, Object obj) {
		String strJson = JsonUtils.objectToJson(obj);
		return doPostRest(url,strJson);
	}
	
	
	public static String doPostRest(String url, Object obj,Map<String, String> headers) {
		String strJson = JsonUtils.objectToJson(obj);
		return doPostRest(url,strJson,headers);
	}
	
	
	/**
	 * 
	 * @param url
	 * @param obj
	 * @return
	 */
	public static String doPostRest(String url,String strJson) {
		return doPostRest(url,strJson,new HashMap<String,String>());
	}
	
	
	
	
	/**
	 * 
	 * @param url
	 * @param obj
	 * @return
	 */
	public static String doPostRest(String url,String strJson,Map<String, String> headers) {
		CloseableHttpResponse response=null;
		CloseableHttpClient httpClient=HttpClients.createDefault();
		try {
			HttpPost postRequest = new HttpPost(url);
			if (headers != null)
				for (Map.Entry<String, String> entry : headers.entrySet())
					postRequest.addHeader(entry.getKey(), entry.getValue());
			StringEntity input = new StringEntity(strJson,"utf-8");
			input.setContentType("application/json");
			postRequest.setEntity(input);
			response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			HttpEntity responseEntity = response.getEntity();
			InputStream is = responseEntity.getContent();
			String strRest = FileUtils.inputStreamtoString(is);
			is.close();
			return strRest;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (Exception inEx) {
					inEx.printStackTrace();
				}
			if (httpClient != null)
				try {
					httpClient.close();
				} catch (Exception inEx) {
					inEx.printStackTrace();
				}
		}
	}
	
	
	/**
	 * 提交数据返回头部
	 * @param url
	 * @param postParams
	 * @return
	 */
	public static  Header[] doPostJsonReturnHeader(String url, String strJson,String headerName)
	{
		CloseableHttpResponse response=null;
		CloseableHttpClient httpClient=HttpClients.createDefault();
		try {
			HttpPost postRequest = new HttpPost(url);
			StringEntity input = new StringEntity(strJson,"utf-8");
			input.setContentType("application/json");
			postRequest.setEntity(input);
			response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			return response.getHeaders(headerName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (Exception inEx) {
					inEx.printStackTrace();
				}
			if (httpClient != null)
				try {
					httpClient.close();
				} catch (Exception inEx) {
					inEx.printStackTrace();
				}
		}
	}


}
