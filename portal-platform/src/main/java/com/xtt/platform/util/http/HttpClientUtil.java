package com.xtt.platform.util.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

/**
 * 请求第三方接口
 * 
 * @author wolf-yansl
 *
 */
public class HttpClientUtil {

	public static final String HTTP_GET = "GET";
	public static final String HTTP_POST = "POST";

	/**
	 * get请求
	 * 
	 * @param uri
	 *            参数绑定uri后面
	 * @return
	 */
	public static HttpClientResultUtil get(String uri) {
		HttpClientResultUtil result = new HttpClientResultUtil();

		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

			int status = httpResponse.getStatusLine().getStatusCode();
			HttpEntity entityResult = httpResponse.getEntity();
			String context = "";
			if (entityResult != null) {
				context = EntityUtils.toString(entityResult);
			}
			result.setStatus(status);
			result.setContext(context);
		} catch (Exception e) {
			e.printStackTrace();
			result.setExceptionMessage(e.getMessage());
		}

		return result;
	}

	/**
	 * post 无参数请求
	 * 
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResultUtil post(String uri) throws Exception {
		return post(uri, null);
	}

	/**
	 * post 带参数请求
	 * 
	 * @param uri
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResultUtil post(String uri, Map<String, String> params) {
		HttpClientResultUtil result = new HttpClientResultUtil();

		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> parameters = getHttpRequestParams(params);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

			int status = httpResponse.getStatusLine().getStatusCode();
			HttpEntity entityResult = httpResponse.getEntity();
			String context = "";
			if (entityResult != null) {
				context = EntityUtils.toString(entityResult);
			}
			result.setStatus(status);
			result.setContext(context);
		} catch (Exception e) {
			e.printStackTrace();
			result.setExceptionMessage(e.getMessage());
		}

		return result;

	}

	/**
	 * post 带参数请求
	 * 
	 * @param uri
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResultUtil postb(String uri, Map<String, StringBuffer> params) {
		HttpClientResultUtil result = new HttpClientResultUtil();

		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> parameters = getHttpRequestParamsb(params);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

			int status = httpResponse.getStatusLine().getStatusCode();
			HttpEntity entityResult = httpResponse.getEntity();
			String context = "";
			if (entityResult != null) {
				context = EntityUtils.toString(entityResult);
			}
			result.setStatus(status);
			result.setContext(context);
		} catch (Exception e) {
			e.printStackTrace();
			result.setExceptionMessage(e.getMessage());
		}

		return result;

	}

	public static HttpClientResultUtil postJson(String uri, String params) {
		HttpClientResultUtil result = new HttpClientResultUtil();
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();

			HttpPost httpPost = new HttpPost(uri);
			StringEntity s = new StringEntity(params);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			httpPost.setEntity(s);

			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

			int status = httpResponse.getStatusLine().getStatusCode();
			HttpEntity entityResult = httpResponse.getEntity();
			String context = "";
			if (entityResult != null) {
				context = EntityUtils.toString(entityResult);
			}
			result.setStatus(status);
			result.setContext(context);
		} catch (Exception e) {
			e.printStackTrace();
			result.setExceptionMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 设置请求参数
	 * 
	 * @param paramsMap
	 * @throws Exception
	 */
	private static List<NameValuePair> getHttpRequestParams(Map<String, String> paramsMap) throws Exception {
		List<NameValuePair> nameValues = null;

		if (paramsMap != null && paramsMap.size() > 0) {
			nameValues = new ArrayList<NameValuePair>();

			Set<String> keys = paramsMap.keySet();
			for (String key : keys) {
				NameValuePair nameValue = new BasicNameValuePair(key, paramsMap.get(key));
				nameValues.add(nameValue);
			}
		}
		return nameValues;

	}

	/**
	 * 设置请求参数
	 * 
	 * @param paramsMap
	 * @throws Exception
	 */
	private static List<NameValuePair> getHttpRequestParamsb(Map<String, StringBuffer> paramsMap) throws Exception {
		List<NameValuePair> nameValues = null;

		if (paramsMap != null && paramsMap.size() > 0) {
			nameValues = new ArrayList<NameValuePair>();

			Set<String> keys = paramsMap.keySet();
			for (String key : keys) {
				NameValuePair nameValue = new BasicNameValuePair(key, paramsMap.get(key).toString());
				nameValues.add(nameValue);
			}
		}
		return nameValues;

	}
}
