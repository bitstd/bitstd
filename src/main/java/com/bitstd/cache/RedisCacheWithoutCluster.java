package com.bitstd.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/1/17
 */
class RedisCacheWithoutCluster extends RedisCache {
    RedisCacheWithoutCluster() {
    }

    public boolean existsCache(String key) {
        Jedis jedis = null;

        boolean var3;
        try {
            jedis = pool.getResource();
            var3 = jedis.exists(key).booleanValue();
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var3;
    }

    public boolean existsHashCache(String key, String field) {
        Jedis jedis = null;

        boolean var4;
        try {
            jedis = pool.getResource();
            var4 = jedis.hexists(key, field).booleanValue();
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var4;
    }

    public void addCache(String key, String value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.set(key, value);
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            pool.returnResourceObject(jedis);
        }

    }

    public void addCache(String key, String field, String value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.hset(key, field, value);
        } catch (Exception var9) {
            throw new RuntimeException(var9);
        } finally {
            pool.returnResourceObject(jedis);
        }

    }

    public void addCacheWithExpire(String key, String value, int seconds) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.setex(key, seconds, value);
        } catch (Exception var9) {
            throw new RuntimeException(var9);
        } finally {
            pool.returnResourceObject(jedis);
        }

    }

    public void addCacheForObject(String key, Object value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.set(key.getBytes(), SerializeUtil.serialize(value));
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            pool.returnResourceObject(jedis);
        }

    }

    public void addCacheForObject(String key, Object value, int seconds) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.setex(key.getBytes(),seconds, SerializeUtil.serialize(value));
        } catch (Exception var9) {
            throw new RuntimeException(var9);
        } finally {
            pool.returnResourceObject(jedis);
        }

    }

    public void addCache(String key, Map<String, Object> map) {
        if (map != null && map.size() > 0) {
            Jedis jedis = null;

            try {
                jedis = pool.getResource();
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

                jedis.hmset(key, m);
            } catch (Exception var12) {
                throw new RuntimeException(var12);
            } finally {
                pool.returnResourceObject(jedis);
            }
        }

    }

    public String getCache(String key) {
        Jedis jedis = null;

        String var3;
        try {
            jedis = pool.getResource();
            var3 = jedis.get(key);
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var3;
    }

    public Object getCacheForObject(String key) {
        Jedis jedis = null;

        Object var3;
        try {
            jedis = pool.getResource();
            byte[] value = jedis.get(key.getBytes());
            var3 = SerializeUtil. unserialize(value);
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var3;
    }

    public String getCache(String key, String field) {
        Jedis jedis = null;

        String var4;
        try {
            jedis = pool.getResource();
            var4 = jedis.hget(key, field);
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var4;
    }

    public List<String> getCache(String key, String... fields) {
        Jedis jedis = null;

        List var4;
        try {
            jedis = pool.getResource();
            var4 = jedis.hmget(key, fields);
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var4;
    }

    public Map<String, String> getHashAllFieldCache(String key) {
        Jedis jedis = null;

        Map var3;
        try {
            jedis = pool.getResource();
            var3 = jedis.hgetAll(key);
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var3;
    }

    public Set<String> getAllKeys() {
        Jedis jedis = null;

        Set var2;
        try {
            jedis = pool.getResource();
            var2 = jedis.keys("*");
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var2;
    }

    public Long increaseBy(String key, long offset) {
        Jedis jedis = null;

        Long var5;
        try {
            jedis = pool.getResource();
            var5 = jedis.incrBy(key, offset);
        } catch (Exception var9) {
            throw new RuntimeException(var9);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var5;
    }

    public Double increaseBy(String key, double offset) {
        Jedis jedis = null;

        Double var5;
        try {
            jedis = pool.getResource();
            var5 = jedis.incrByFloat(key, offset);
        } catch (Exception var9) {
            throw new RuntimeException(var9);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var5;
    }

    public Long decreaseBy(String key, long offset) {
        Jedis jedis = null;

        Long var5;
        try {
            jedis = pool.getResource();
            var5 = jedis.decrBy(key, offset);
        } catch (Exception var9) {
            throw new RuntimeException(var9);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var5;
    }

    public Long increase(String key) {
        Jedis jedis = null;

        Long var3;
        try {
            jedis = pool.getResource();
            var3 = jedis.incr(key);
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var3;
    }

    public Long decrease(String key) {
        Jedis jedis = null;

        Long var3;
        try {
            jedis = pool.getResource();
            var3 = jedis.decr(key);
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        } finally {
            pool.returnResourceObject(jedis);
        }

        return var3;
    }

    public void expireKey(String key, int seconds) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.expire(key, seconds);
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            pool.returnResourceObject(jedis);
        }

    }

    public void delCache(String... keys) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.del(keys);
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        } finally {
            pool.returnResourceObject(jedis);
        }

    }

    public void delCacheObject(String key) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.del(key.getBytes());
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        } finally {
            pool.returnResourceObject(jedis);
        }
    }

    public void delHashCache(String key, String... fields) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.hdel(key, fields);
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            pool.returnResourceObject(jedis);
        }

    }

    public void publish(String channel, String message) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.publish(channel, message);
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            pool.returnResourceObject(jedis);
        }

    }

    public void publish(byte[] channel, byte[] message) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.publish(channel, message);
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            pool.returnResourceObject(jedis);
        }

    }

    public void subscribe(JedisPubSub jedisPubSub, String channel) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.subscribe(jedisPubSub, new String[]{channel});
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            pool.returnResourceObject(jedis);
        }

    }

    public void closeCacheClient() {
        pool.close();
    }
}
