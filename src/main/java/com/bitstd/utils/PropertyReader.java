package com.bitstd.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/10/17
 */
public class PropertyReader {
    private static final Map<String, Map<String, String>> cache = new ConcurrentHashMap();

    public PropertyReader() {
    }

    public static String get(String key, String fileName) {
        if (cache.containsKey(fileName)) {
            Map<String, String> map = (Map)cache.get(fileName);
            return (String)map.get(key);
        } else {
            Map<String, String> map = new HashMap();
            
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
    		InputStream in = cl.getResourceAsStream(fileName);
    		Properties prop = new Properties();
//            Properties prop = new Properties();
//            InputStream in = PropertyReader.class.getResourceAsStream(fileName);

            try {
                if (in != null) {
                    prop.load(in);
                }
            } catch (Exception var10) {
                var10.printStackTrace();
            }

            Set<Entry<Object, Object>> set = prop.entrySet();
            Iterator it = set.iterator();

            while(it.hasNext()) {
                Entry<Object, Object> entry = (Entry)it.next();
                String k = entry.getKey().toString();
                String v = entry.getValue() == null ? null : entry.getValue().toString();
                map.put(k, v);
            }

            cache.put(fileName, map);
            return (String)map.get(key);
        }
    }
}
