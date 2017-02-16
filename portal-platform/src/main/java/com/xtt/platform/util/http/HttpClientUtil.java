package com.xtt.platform.util.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.xtt.platform.util.lang.StringUtil;

/**
 * 请求第三方接口
 * 
 * @author wolf-yansl
 *
 */
public class HttpClientUtil {

    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    // 默认链接超时（ms）
    public static final int DEFAULT_CONNECT_TIMEOUT = 30000;
    // 默认请求获取数据的超时时间（ms）
    public static final int DEFAULT_SOCKET_TIMEOUT = -1;
    // 默认connect Manager获取Connection 超时时间（ms）
    public static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = -1;

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
            httpGet.setConfig(getRequestConfig(null, null, null));
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
        return post(uri, params, null);

    }

    /**
     * post 带Cookie请求
     * 
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static HttpClientResultUtil post(String url, Map<String, String> params, CookieStore cookie) {
        HttpClientResultUtil result = new HttpClientResultUtil();

        try {
            CloseableHttpClient httpClient;
            if (cookie != null) {
                httpClient = HttpClients.custom().setDefaultCookieStore(cookie).build();
            } else {
                httpClient = HttpClients.createDefault();
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(getRequestConfig(null, null, null));
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
            httpPost.setConfig(getRequestConfig(null, null, null));
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
        return postJson(uri, params, null);
    }

    public static HttpClientResultUtil postJson(String uri, String params, CookieStore cookieStore) {
        HttpClientResultUtil result = new HttpClientResultUtil();
        try {
            CloseableHttpClient httpClient;
            if (cookieStore != null) {
                httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            } else {
                httpClient = HttpClients.createDefault();
            }
            HttpPost httpPost = new HttpPost(uri);
            StringEntity s = new StringEntity(params, ContentType.APPLICATION_JSON);
            s.setContentEncoding("UTF-8");
            httpPost.setEntity(s);

            httpPost.addHeader("Accept", "application/json");
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
     * 创建cookieStore
     * 
     * @Title: createCookieStore
     * @return
     *
     */
    public static CookieStore createCookieStore(String name, String value, String domain, String path) {
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie(name, value);
        cookie.setVersion(0);
        cookie.setDomain(domain);
        cookie.setPath(StringUtil.isBlank(path) ? "/" : path);
        cookieStore.addCookie(cookie);
        return cookieStore;
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

    /**
     * 获取请求配置
     * 
     * @Title: getRequestConfig
     * @param connectTimeOut
     *            设置连接超时时间，单位毫秒
     * @param socketTimeout
     *            请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用
     * @param connectionRequestTimeout
     *            设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
     * @return
     *
     */
    private static RequestConfig getRequestConfig(Integer connectTimeOut, Integer socketTimeout, Integer connectionRequestTimeout) {
        connectTimeOut = connectTimeOut == null ? DEFAULT_CONNECT_TIMEOUT : connectTimeOut;
        socketTimeout = socketTimeout == null ? DEFAULT_SOCKET_TIMEOUT : socketTimeout;
        connectionRequestTimeout = connectionRequestTimeout == null ? DEFAULT_CONNECTION_REQUEST_TIMEOUT : connectionRequestTimeout;
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeOut).setConnectionRequestTimeout(connectionRequestTimeout)
                        .setSocketTimeout(socketTimeout).build();
        return requestConfig;
    }
}
