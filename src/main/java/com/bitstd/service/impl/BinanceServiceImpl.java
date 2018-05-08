package com.bitstd.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bitstd.model.AvgInfoBean;
import com.bitstd.model.ExInfoBean;
import com.bitstd.service.IBinanceService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;
import com.bitstd.utils.StringUtil;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/8/18
 */

public class BinanceServiceImpl implements IBinanceService{
	
	HostnameVerifier hv = new HostnameVerifier() {  
        public boolean verify(String urlHostName, SSLSession session) {  
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "  
                               + session.getPeerHost());  
            return true;  
        }  
    };  
      
    private static void trustAllHttpsCertificates() throws Exception {  
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];  
        javax.net.ssl.TrustManager tm = new miTM();  
        trustAllCerts[0] = tm;  
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext  
                .getInstance("SSL");  
        sc.init(null, trustAllCerts, null);  
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc  
                .getSocketFactory());  
    }  
  
    static class miTM implements javax.net.ssl.TrustManager,  
            javax.net.ssl.X509TrustManager {  
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
            return null;  
        }  
  
        public boolean isServerTrusted(  
                java.security.cert.X509Certificate[] certs) {  
            return true;  
        }  
  
        public boolean isClientTrusted(  
                java.security.cert.X509Certificate[] certs) {  
            return true;  
        }  
  
        public void checkServerTrusted(  
                java.security.cert.X509Certificate[] certs, String authType)  
                throws java.security.cert.CertificateException {  
            return;  
        }  
  
        public void checkClientTrusted(  
                java.security.cert.X509Certificate[] certs, String authType)  
                throws java.security.cert.CertificateException {  
            return;  
        }  
    }  

	private String doRequest(String type) throws HttpException, IOException {
		String content = "";
		try {
			trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(hv); 
			HashMap<String, String> paramMap = new HashMap<>();
			paramMap.put("symbol", type);
			String params = StringUtil.createLinkString(paramMap);
			HttpUtilManager httpUtil = HttpUtilManager.getInstance();
			URL url = new URL(Constants.BINANCE_API+"?"+params);
			content = httpUtil.retrieveResponseFromServer(url);
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return content;
	}
	
	private ExInfoBean getBinanceBtcPrice(){
		ExInfoBean eb = new ExInfoBean();
		try {
			String content = doRequest("BTCUSDT");
			JSONObject jsonObj = JSON.parseObject(content);
			double price = jsonObj.getDoubleValue("lastPrice");
			double volume = jsonObj.getDoubleValue("volume");
			if(price>0 && volume>0){
				eb.setPrice(price);
				eb.setVolume(volume);
				eb.ExBeanToPrint("Binance");
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return eb;
	}
	
	@Override
	public synchronized ExInfoBean getBinanceIndex(String type) {
		ExInfoBean eb = new ExInfoBean();
		if ("".equals(type) || type == null) {
			return eb;
		}
		try {
			String content = doRequest(type);
			if(content!=null){
				JSONObject jsonObj = JSON.parseObject(content);
				double price = jsonObj.getDoubleValue("lastPrice");
				double volume = jsonObj.getDoubleValue("volume");
				if(price>0 && volume>0){
					if(!type.contains("USDT")){
						ExInfoBean bean = getBinanceBtcPrice();
						double btcprice = bean.getPrice();
						price = new BigDecimal(price).multiply(new BigDecimal(btcprice)).doubleValue();
					}
					eb.setPrice(price);
					eb.setVolume(volume);
					eb.ExBeanToPrint(type + " Binance");
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return eb;
	}
}
