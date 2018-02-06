package com.bitstd.service;

import java.util.List;

import com.bitstd.model.SupplyBean;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/6/18
 */

public interface ISupplyService {
	List<SupplyBean> getSupplyInfo(String[] symbols);
}
