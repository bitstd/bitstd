package com.bitstd.dao.aggregation;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 5/7/18
 */

public class TableUtil {
    private static final String INDEXTABLE = "STD_INDEXHIS";
    private static final String FUTURESTABLE = "STD_FUTURESHIS";
    private static final String SPOTINDEXTABLE = "STD_SPOTINDEXHIS";

    /**
     * BitSTD index ：STD_INDEXHIS_MI15 15min
     * @param type
     * @param value
     * @return
     */
    public static String getIndexTableName(AggregationType type, int value) {
        return INDEXTABLE + "_" + type.name().toUpperCase() + value;
    }

    /**
     * BitSTD futures index ：STD_FUTURESHIS_H1 1hour
     * @param type
     * @param value
     * @return
     */
    public static String getFuturesTableName(AggregationType type, int value) {
        return FUTURESTABLE + "_" + type.name().toUpperCase() + value;
    }
    
    /**
     * Spot index ：STD_SPOTINDEXHIS_H1 1hour
     * @param type
     * @param value
     * @return
     */
    public static String getSpotIndexTableName(AggregationType type, int value) {
        return SPOTINDEXTABLE + "_" + type.name().toUpperCase() + value;
    }
}
