package com.douyu.supermap.shakingmap.config.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RedisManager {

    public void set(String key,String value);

    public void set(String key,String value,long timestamp);

    public String get(String key);

    public String get(String key,long start,long end);

    public List get(Collection collection);

    public void set(Map map);

    public void hset(String redisKey,String hashKey,String value);

    public void hset(String redisKey,String hashKey,String value,long timestamp);

    public String hget(String redisKey,String hashKey);

    public void incre(String key);
}
