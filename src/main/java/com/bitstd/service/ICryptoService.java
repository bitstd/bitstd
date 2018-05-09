package com.bitstd.service;

import java.util.List;

import com.bitstd.model.CryptoInfoBean;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 5/9/18
 */

public interface ICryptoService {
	List<CryptoInfoBean> getCryptoInfo(List<String> listings);
}
