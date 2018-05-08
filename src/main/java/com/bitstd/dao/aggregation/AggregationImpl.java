package com.bitstd.dao.aggregation;

import java.util.HashMap;
import java.util.Map;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 5/7/18
 */

public class AggregationImpl {

	/*
	 * type ： 0 IndexTableName 1 FuturesTableName
	 */
	public static Map<String, Long> aggregationImpl(int type) {
		Map<String, Long> aggregations = new HashMap<String, Long>();
		Long mills = System.currentTimeMillis();
		for (Aggregation aggregation : Aggregation.getDefinedAggregation()) {
			Long time = TimeUtil.adjustTime(mills, aggregation);
			String tablename = "";
			if (type == 0) {
				tablename = TableUtil.getIndexTableName(aggregation.getType(), aggregation.getValue());
			} else {
				tablename = TableUtil.getFuturesTableName(aggregation.getType(), aggregation.getValue());
			}
			aggregations.put(tablename, time);
		}
		return aggregations;
	}

	public static void main(String[] args) {
		AggregationImpl.aggregationImpl(1);
	}
}
