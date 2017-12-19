package com.bitstd.utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpException;


/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/19/17
 */
public class Tools {

	/**
	 * @param type:usdkrw
	 *            def:1068
	 */
	public static double getUSDRate(String type, double def) {
		double rate = def;
		long time = System.currentTimeMillis();
		String url = "http://hq.sinajs.cn/rn=" + time + "list=fx_s" + type;
		try {
			HttpUtilManager httpUtil = HttpUtilManager.getInstance();
			String content = httpUtil.requestHttpGet(url, "");
			String rateRegex = "hq_str_fx_s" + type + "=\".*?,(.*?),.*?\"";
			Pattern pattern = Pattern.compile(rateRegex, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(content);
			if (matcher.find()) {
				String strrate = matcher.group(1);
				rate = Double.parseDouble(strrate);
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rate;
	}
}
