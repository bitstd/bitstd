package com.bitstd.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.JedisPubSub;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/1/17
 */
public class RedisEmptyCache extends RedisCache {
    public RedisEmptyCache() {
    }

    public boolean existsCache(String key) {
        return false;
    }

    public boolean existsHashCache(String key, String field) {
        return false;
    }

    public void addCache(String key, String value) {
    }

    public void addCache(String key, String field, String value) {
    }

    public void addCacheWithExpire(String key, String value, int seconds) {
    }

    public void addCache(String key, Map<String, Object> map) {
    }

    public void addCacheForObject(String key,Object o){
    }

    public void addCacheForObject(String key, Object o, int seconds) {
    }

    public String getCache(String key) {
        return null;
    }

    public Object getCacheForObject(String key){
        return null ;
    }

    public String getCache(String key, String field) {
        return null;
    }

    public List<String> getCache(String key, String... fields) {
        return null;
    }

    public Map<String, String> getHashAllFieldCache(String key) {
        return null;
    }

    public Set<String> getAllKeys() {
        return null;
    }

    public Long increaseBy(String key, long offset) {
        return null;
    }

    public Double increaseBy(String key, double offset) {
        return null;
    }

    public Long decreaseBy(String key, long offset) {
        return null;
    }

    public Long increase(String key) {
        return null;
    }

    public Long decrease(String key) {
        return null;
    }

    public void expireKey(String key, int seconds) {
    }

    public void delCache(String... keys) {
    }

    public void delCacheObject(String var1){
    }

    public void delHashCache(String key, String... fields) {
    }

    public void publish(String channel, String message) {
    }

    public void publish(byte[] channel, byte[] message) {
    }

    public void subscribe(JedisPubSub jedisPubSub, String channel) {
    }

    public void closeCacheClient() {
    }
}