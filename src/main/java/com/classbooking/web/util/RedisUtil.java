package com.classbooking.web.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisUtil {

    private JedisPool jedisPool;

    public void setJedisPool(JedisPool jedisPool){
        this.jedisPool=jedisPool;
    }

    /**
     * 增加
     * @param key
     * @param value
     */
    public void set(String key,String value){
        Jedis jedis = this.jedisPool.getResource();
        jedis.set(key,value);
        this.jedisPool.returnResource(jedis);
    }

    /**
     * 增加 带超时时间
     * @param key
     * @param value
     * @param seconds
     */
    public void setex(String key,String value,int seconds){
        Jedis jedis = this.jedisPool.getResource();
        jedis.setex(key,seconds,value);
        this.jedisPool.returnResource(jedis);
    }

    /**
     * 获取
     * @param key
     * @return
     */
    public String get(String key){
        Jedis jedis = this.jedisPool.getResource();
        String value = jedis.get(key);
        this.jedisPool.returnResource(jedis);
        return value;
    }

    /**
     * 查看某个键是否存在
     * @param key
     * @return
     */
    public boolean exists(String key){
        Jedis jedis = this.jedisPool.getResource();
        Boolean exists = jedis.exists(key);
        this.jedisPool.returnResource(jedis);
        return exists;
    }

    /**
     * 查看超时时间
     * @param key
     * @return
     */
    public Long ttl(String key){
        Jedis jedis = this.jedisPool.getResource();
        Long ttl = jedis.ttl(key);
        this.jedisPool.returnResource(jedis);
        return ttl;
    }

    /**
     * 删除某个key
     * @param key
     */
    public void del(String key){
        Jedis jedis = this.jedisPool.getResource();
        jedis.del(key);
        this.jedisPool.returnResource(jedis);
    }
}
