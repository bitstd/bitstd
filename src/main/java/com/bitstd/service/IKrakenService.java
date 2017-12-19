package com.bitstd.service;

import com.bitstd.model.ExInfoBean;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/11/17
 */
public interface IKrakenService {

	ExInfoBean getKrakenIndex(String type);

}
