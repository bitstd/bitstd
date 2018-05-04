package com.bitstd.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;

import com.bitstd.model.IndexBean;
import com.bitstd.service.IIndexMarketService;
import com.bitstd.utils.Constants;
import com.bitstd.utils.HttpUtilManager;
import com.bitstd.utils.Tools;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 4/9/18
 */
public class IndexMarketServiceImpl implements IIndexMarketService {
	private String doRequest() throws HttpException, IOException {
		HttpUtilManager httpUtil = HttpUtilManager.getInstance();
		return httpUtil.requestHttpGet(Constants.INDEXMARKET_API, "", "gb2312");
	}

	@Override
	public List<IndexBean> getIndexMarket() {
		List<IndexBean> indexBeans = new ArrayList<IndexBean>();
		try {
			String content = doRequest();
			String regexStr = "ixic=\"(.*?)\";";
			String nasdaq = Tools.getRegexContent(regexStr, content);
			String[] nasdaqStr = nasdaq.split(",");
			if (nasdaqStr != null && nasdaqStr.length == 28) {
				IndexBean nasdaqBean = new IndexBean();
				nasdaqBean.setCurrentIndex(nasdaqStr[1]);
				nasdaqBean.setRiseAndfall(nasdaqStr[2]);
				nasdaqBean.setRisefallIndex(nasdaqStr[4]);
				nasdaqBean.setOpeningIndex(nasdaqStr[5]);
				nasdaqBean.setHighIndex(nasdaqStr[6]);
				nasdaqBean.setLowIndex(nasdaqStr[7]);
				nasdaqBean.setWeekHighIndex(nasdaqStr[8]);
				nasdaqBean.setWeeklowIndex(nasdaqStr[9]);
				nasdaqBean.setClosingIndex(nasdaqStr[26]);
				nasdaqBean.setTime(nasdaqStr[25]);
				nasdaqBean.setType("2");
				nasdaqBean.IndexBeanToPrint();
				indexBeans.add(nasdaqBean);
			}

			regexStr = "dji=\"(.*?)\";";
			String djia = Tools.getRegexContent(regexStr, content);
			String[] djiaStr = djia.split(",");
			if (djiaStr != null && djiaStr.length == 28) {
				IndexBean djiaBean = new IndexBean();
				djiaBean.setCurrentIndex(djiaStr[1]);
				djiaBean.setRiseAndfall(djiaStr[2]);
				djiaBean.setRisefallIndex(djiaStr[4]);
				djiaBean.setOpeningIndex(djiaStr[5]);
				djiaBean.setHighIndex(djiaStr[6]);
				djiaBean.setLowIndex(djiaStr[7]);
				djiaBean.setWeekHighIndex(djiaStr[8]);
				djiaBean.setWeeklowIndex(djiaStr[9]);
				djiaBean.setClosingIndex(djiaStr[26]);
				djiaBean.setTime(djiaStr[25]);
				djiaBean.setType("0");
				djiaBean.IndexBeanToPrint();
				indexBeans.add(djiaBean);
			}

			regexStr = "inx=\"(.*?)\";";
			String sp500 = Tools.getRegexContent(regexStr, content);
			String[] sp500Str = sp500.split(",");
			if (sp500Str != null && sp500Str.length == 28) {
				IndexBean sp500Bean = new IndexBean();
				sp500Bean.setCurrentIndex(sp500Str[1]);
				sp500Bean.setRiseAndfall(sp500Str[2]);
				sp500Bean.setRisefallIndex(sp500Str[4]);
				sp500Bean.setOpeningIndex(sp500Str[5]);
				sp500Bean.setHighIndex(sp500Str[6]);
				sp500Bean.setLowIndex(sp500Str[7]);
				sp500Bean.setWeekHighIndex(sp500Str[8]);
				sp500Bean.setWeeklowIndex(sp500Str[9]);
				sp500Bean.setClosingIndex(sp500Str[26]);
				sp500Bean.setTime(sp500Str[25]);
				sp500Bean.setType("1");
				sp500Bean.IndexBeanToPrint();
				indexBeans.add(sp500Bean);
			}

		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return indexBeans;
	}
}
