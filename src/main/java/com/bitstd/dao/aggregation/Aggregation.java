package com.bitstd.dao.aggregation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 5/7/18
 */

public class Aggregation {
    private int value;
    private AggregationType type;
    private Aggregation dependency;
    private static final List<Aggregation> aggregationList = new ArrayList<>();
    private static final Map<AggregationType, List<Aggregation>> aggregationMap = new HashMap<>();

    static {

        final Aggregation second1 = new Aggregation(AggregationType.s, 1);
        final Aggregation minute15 = new Aggregation(AggregationType.mi, 15, second1);
        final Aggregation minute30 = new Aggregation(AggregationType.mi, 30, second1);
        final Aggregation hour1 = new Aggregation(AggregationType.h, 1, second1);
        final Aggregation hour2 = new Aggregation(AggregationType.h, 2, second1);
        final Aggregation hour4 = new Aggregation(AggregationType.h, 4, second1);
        final Aggregation hour6 = new Aggregation(AggregationType.h, 6, second1);
        final Aggregation hour12 = new Aggregation(AggregationType.h, 12, second1);
        final Aggregation day1 = new Aggregation(AggregationType.d, 1, second1);
        final Aggregation week1 = new Aggregation(AggregationType.w, 1, second1);
        final Aggregation month1 = new Aggregation(AggregationType.mo, 1, second1);

        //aggregationList.add(second1);
        aggregationList.add(minute15);
        aggregationList.add(minute30);
        aggregationList.add(hour1);
        aggregationList.add(hour2);
        aggregationList.add(hour4);
        aggregationList.add(hour6);
        aggregationList.add(hour12);
        aggregationList.add(day1);
        aggregationList.add(week1);
        aggregationList.add(month1);

        //resolveMap(AggregationType.s, second1);
        resolveMap(AggregationType.mi, minute15);
        resolveMap(AggregationType.mi, minute30);
        resolveMap(AggregationType.h, hour1);
        resolveMap(AggregationType.h, hour2);
        resolveMap(AggregationType.h, hour4);
        resolveMap(AggregationType.h, hour6);
        resolveMap(AggregationType.h, hour12);
        resolveMap(AggregationType.d, day1);
        resolveMap(AggregationType.w, week1);
        resolveMap(AggregationType.mo, month1);
    }

    public Aggregation() {

    }

    public Aggregation(AggregationType type, int value) {
        this.type = type;
        this.value = value;
    }

    public Aggregation(AggregationType type, int value, Aggregation dependency) {
        this.type = type;
        this.value = value;
        this.dependency = dependency;
    }

    public AggregationType getType() {
        return type;
    }

    public void setType(AggregationType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Aggregation getDependency() {
        return dependency;
    }

    public void setDependency(Aggregation dependency) {
        this.dependency = dependency;
    }

    public static List<Aggregation> getDefinedAggregation() {
        return aggregationList;
    }

    public static boolean containsValue(AggregationType type, int expectValue) {
        List<Aggregation> list = aggregationMap.get(type);
        if (list != null) {
            for (Aggregation aggregation : list) {
                if (aggregation.getValue() == expectValue) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void resolveMap(AggregationType type, Aggregation aggregation) {
        List<Aggregation> list = aggregationMap.get(type);
        if (list == null) {
            list = new ArrayList<>();
            aggregationMap.put(type, list);
        }
        list.add(aggregation);
    }
    
   
}
