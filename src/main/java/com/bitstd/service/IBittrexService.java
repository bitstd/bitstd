package com.bitstd.service;

import com.bitstd.model.ExInfoBean;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/8/18
 */

public interface IBittrexService {
	ExInfoBean getBittrexIndex(String type);
}
