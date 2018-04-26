package com.bitstd.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import com.alibaba.fastjson.JSON;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/10/17
 */
public class HttpUtilManager {

	private static HttpUtilManager instance = new HttpUtilManager();
	private static HttpClient client;
	private static long startTime = System.currentTimeMillis();
	public static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	private static ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {

		public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
			long keepAlive = super.getKeepAliveDuration(response, context);

			if (keepAlive == -1) {
				keepAlive = 5000;
			}
			return keepAlive;
		}

	};

	private HttpUtilManager() {
		client = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(keepAliveStrat).build();
	}

	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000)
			.setConnectionRequestTimeout(20000).build();

	public static HttpUtilManager getInstance() {
		return instance;
	}

	public HttpClient getHttpClient() {
		return client;
	}

	private HttpPost httpPostMethod(String url, String authorization) {
		HttpPost method = new HttpPost(url);
		method.setHeader("Content-Type", "application/json");
		method.setHeader("authorization", authorization);
		return method;
	}

	private HttpRequestBase httpGetMethod(String url, String authorization) {
		HttpGet method = new HttpGet(url);
		method.setHeader("Content-Type", "application/json");
		method.setHeader("authorization", authorization);
		return method;
	}

	public String requestHttpGet(String url_prex, String type) throws HttpException, IOException {
		String url = url_prex + type;
		HttpRequestBase method = this.httpGetMethod(url, "");
		method.setConfig(requestConfig);
		long start = System.currentTimeMillis();
		HttpResponse response = client.execute(method);
		long end = System.currentTimeMillis();
		Logger.getGlobal().log(Level.INFO, String.valueOf(end - start));
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			return "";
		}
		InputStream is = null;
		String responseData = "";
		try {
			is = entity.getContent();
			responseData = IOUtils.toString(is, "UTF-8");
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return responseData;
	}

	public String requestHttpGet(String url_prex, String url, Map<String, String> paramMap, String authorization)
			throws HttpException, IOException {

		url = url_prex + url + "?" + StringUtil.createLinkString(paramMap);
		if (paramMap == null) {
			paramMap = new HashMap<>();
		}
		
		HttpRequestBase method = this.httpGetMethod(url, authorization);
		method.setConfig(requestConfig);
		long start = System.currentTimeMillis();
		HttpResponse response = client.execute(method);
		long end = System.currentTimeMillis();
		Logger.getGlobal().log(Level.INFO, String.valueOf(end - start));
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			return "";
		}
		InputStream is = null;
		String responseData = "";
		try {
			is = entity.getContent();
			responseData = IOUtils.toString(is, "UTF-8");
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return responseData;
	}
	
	public String requestHttpGet(String url_prex, String type,String code) throws HttpException, IOException {
		String url = url_prex + type;
		HttpRequestBase method = this.httpGetMethod(url, "");
		method.setConfig(requestConfig);
		long start = System.currentTimeMillis();
		HttpResponse response = client.execute(method);
		long end = System.currentTimeMillis();
		Logger.getGlobal().log(Level.INFO, String.valueOf(end - start));
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			return "";
		}
		InputStream is = null;
		String responseData = "";
		try {
			is = entity.getContent();
			responseData = IOUtils.toString(is, code);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return responseData;
	}

	public String requestHttpPost(String url_prex, String url, Map<String, String> params, String authorization)
			throws HttpException, IOException {

		url = url_prex + url;
		HttpPost method = this.httpPostMethod(url, authorization);
		String paramsstr = JSON.toJSONString(params);
		StringEntity sendEntity = new StringEntity(paramsstr);
		method.setEntity(sendEntity);
		method.setConfig(requestConfig);
		HttpEntity httpEntity = method.getEntity();
		for (int i = 0; i < method.getAllHeaders().length; i++) {
			Header header = method.getAllHeaders()[i];
		}

		HttpResponse response = client.execute(method);
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			return "";
		}
		InputStream is = null;
		String responseData = "";
		try {
			is = entity.getContent();
			responseData = IOUtils.toString(is, "UTF-8");
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return responseData;
	}
	
	public String retrieveResponseFromServer(URL validationUrl) {  
        HttpURLConnection connection = null;  
        try {  
            connection = (HttpURLConnection) validationUrl.openConnection();  
            connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            connection.setRequestMethod("GET");
            final BufferedReader in = new BufferedReader(new InputStreamReader(  
                    connection.getInputStream()));  
            String line;  
            final StringBuffer stringBuffer = new StringBuffer(255);  
  
            synchronized (stringBuffer) {  
                while ((line = in.readLine()) != null) {  
                    stringBuffer.append(line);  
                    stringBuffer.append("\n");  
                }  
                return stringBuffer.toString();  
            }  
  
        } catch (final IOException e) {
        	e.printStackTrace();
            return null;  
        } catch (final Exception e1){
        	e1.printStackTrace();
            return null;  
        }finally {  
            if (connection != null) {  
                connection.disconnect();  
            }  
        }  
    }  
}