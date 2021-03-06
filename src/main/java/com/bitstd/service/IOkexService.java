package com.bitstd.service;

import com.bitstd.model.ExInfoBean;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/8/18
 */

public interface IOkexService {
	ExInfoBean getOkexIndex(String type);
	ExInfoBean getOkexFuturesIndex(String type,String contract);
}
