package com.bitstd.dao.aggregation;

import java.util.Calendar;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 5/7/18
 */

public class TimeUtil {

    public static Long adjustTime(Long mills, Aggregation aggregation) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        calendar.set(Calendar.MILLISECOND, 0);

        switch (aggregation.getType()) {

            case s:
                return calendar.getTimeInMillis();
            case mi:
                calendar.set(Calendar.SECOND, 0);
                int m = calendar.get(Calendar.MINUTE) / aggregation.getValue() * aggregation.getValue();
                calendar.set(Calendar.MINUTE, m);
                return calendar.getTimeInMillis();
            case h:
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                int h = calendar.get(Calendar.HOUR_OF_DAY) / aggregation.getValue() * aggregation.getValue();
                calendar.set(Calendar.HOUR_OF_DAY, h);
                return calendar.getTimeInMillis();
            case d:
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                return calendar.getTimeInMillis();
            case w:
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - calendar.get(Calendar.DAY_OF_WEEK));
                return calendar.getTimeInMillis();
            case mo:
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.DATE, 1);
                return calendar.getTimeInMillis();
            case y:
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.DATE, 1);
                calendar.set(Calendar.MONTH, 0);
                return calendar.getTimeInMillis();
        }
        throw new RuntimeException("Unsupported aggregation type:" + aggregation.getType().name());
    }

}
