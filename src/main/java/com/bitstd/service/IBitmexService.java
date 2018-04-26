package com.bitstd.service;

import com.bitstd.model.ExInfoBean;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 4/26/18
 */

public interface IBitmexService {
	ExInfoBean getBitmexFuturesIndex(String type);
}
