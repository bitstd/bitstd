package com.bitstd.cache;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/1/17
 */
class RedisCacheWithCluster extends RedisCache {
    RedisCacheWithCluster() {
    }

    public boolean existsCache(String key) {
        return jedisCluster.exists(key).booleanValue();
    }

    public boolean existsHashCache(String key, String field) {
        return jedisCluster.hexists(key, field).booleanValue();
    }

    public void addCache(String key, String value) {
        jedisCluster.set(key, value);
    }

    public void addCache(String key, String field, String value) {
        jedisCluster.hset(key, field, value);
    }

    public void addCacheWithExpire(String key, String value, int seconds) {
        jedisCluster.setex(key, seconds, value);
    }

    public void addCacheForObject(String key, Object value) {
        jedisCluster.set(key.getBytes(), SerializeUtil.serialize(value));
    }

    public void addCacheForObject(String key, Object value,int seconds) {
        jedisCluster.setex(key.getBytes(),seconds, SerializeUtil.serialize(value));
    }

    public void addCache(String key, Map<String, Object> map) {
        if (map != null && map.size() > 0) {
            Map<String, String> m = new HashMap();
            Set<String> sets = map.keySet();
            Iterator it = sets.iterator();

            while(it.hasNext()) {
                String k = (String)it.next();
                Object o = map.get(k);
                if (o != null) {
                    if (o instanceof String) {
                        m.put(k, (String)o);
                    } else if (!(o instanceof Collection)) {
                        m.put(k, String.valueOf(o));
                    } else {
                        m.put(k, null);
                    }
                } else {
                    m.put(k, null);
                }
            }

            jedisCluster.hmset(key, m);
        }

    }

    public String getCache(String key) {
        return jedisCluster.get(key);
    }

    public Object getCacheForObject(String key){
        byte[] value = jedisCluster.get(key.getBytes());
        return SerializeUtil. unserialize(value);
    }

    public String getCache(String key, String field) {
        return jedisCluster.hget(key, field);
    }

    public List<String> getCache(String key, String... fields) {
        return jedisCluster.hmget(key, fields);
    }

    public Map<String, String> getHashAllFieldCache(String key) {
        return jedisCluster.hgetAll(key);
    }

    public Set<String> getAllKeys() {
        Set<String> keysSet = new HashSet();
        Collection<JedisPool> poolCollection = jedisCluster.getClusterNodes().values();
        Iterator iterator = poolCollection.iterator();

        while(iterator.hasNext()) {
            JedisPool jedisPool = (JedisPool)iterator.next();
            Jedis jedis = null;

            try {
                jedis = jedisPool.getResource();
                keysSet.addAll(jedis.keys("*"));
            } catch (Exception var10) {
                throw new RuntimeException(var10);
            } finally {
                jedisPool.returnResourceObject(jedis);
            }
        }

        return keysSet;
    }

    public Long increaseBy(String key, long offset) {
        return jedisCluster.incrBy(key, offset);
    }

    public Double increaseBy(String key, double offset) {
        return jedisCluster.incrByFloat(key, offset);
    }

    public Long decreaseBy(String key, long offset) {
        return jedisCluster.decrBy(key, offset);
    }

    public Long increase(String key) {
        return jedisCluster.incr(key);
    }

    public Long decrease(String key) {
        return jedisCluster.decr(key);
    }

    public void expireKey(String key, int seconds) {
        jedisCluster.expire(key, seconds);
    }

    public void delCache(String... keys) {
        if (keys != null && keys.length > 0) {
            String[] arr$ = keys;
            int len$ = keys.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String k = arr$[i$];
                jedisCluster.del(k);
            }
        }

    }

    public void delCacheObject(String key) {
        jedisCluster.del(key.getBytes());
    }

    public void delHashCache(String key, String... fields) {
        jedisCluster.hdel(key, fields);
    }

    public void publish(String channel, String message) {
        Collection<JedisPool> poolCollection = jedisCluster.getClusterNodes().values();
        Iterator<JedisPool> iterator = poolCollection.iterator();
        if (iterator.hasNext()) {
            JedisPool jedisPool = (JedisPool)iterator.next();
            Jedis jedis = null;

            try {
                jedis = jedisPool.getResource();
                jedis.publish(channel, message);
            } catch (Exception var11) {
                throw new RuntimeException(var11);
            } finally {
                jedisPool.returnResourceObject(jedis);
            }
        }

    }

    public void publish(byte[] channel, byte[] message) {
        Collection<JedisPool> poolCollection = jedisCluster.getClusterNodes().values();
        Iterator<JedisPool> iterator = poolCollection.iterator();
        if (iterator.hasNext()) {
            JedisPool jedisPool = (JedisPool)iterator.next();
            Jedis jedis = null;

            try {
                jedis = jedisPool.getResource();
                jedis.publish(channel, message);
            } catch (Exception var11) {
                throw new RuntimeException(var11);
            } finally {
                jedisPool.returnResourceObject(jedis);
            }
        }

    }

    public void subscribe(JedisPubSub jedisPubSub, String channel) {
        Collection<JedisPool> poolCollection = jedisCluster.getClusterNodes().values();
        Iterator iterator = poolCollection.iterator();

        while(iterator.hasNext()) {
            JedisPool jedisPool = (JedisPool)iterator.next();
            Jedis jedis = null;

            try {
                jedis = jedisPool.getResource();
                jedis.subscribe(jedisPubSub, new String[]{channel});
            } catch (Exception var11) {
                throw new RuntimeException(var11);
            } finally {
                jedisPool.returnResourceObject(jedis);
            }
        }

    }

    public void closeCacheClient() {
        try {
            jedisCluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
