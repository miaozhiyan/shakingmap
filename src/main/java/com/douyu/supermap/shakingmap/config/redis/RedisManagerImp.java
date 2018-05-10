package com.douyu.supermap.shakingmap.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisManagerImp implements RedisManager{
    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init(){
        redisTemplate.setValueSerializer( new StringRedisSerializer());
    }

    public void set(String key, String value){
        if (key==null){
            throw new RuntimeException();
        }
        redisTemplate.opsForValue().set(key,value);
    }

    public void set(String key ,String value ,long timestamp){
        if (key==null){
            throw new RuntimeException();
        }
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,timestamp, TimeUnit.MILLISECONDS);
    }

    public String get(String key){
        return (String)redisTemplate.opsForValue().get(key);
    }

    public String get(String key,long start,long end){
        return redisTemplate.opsForValue().get(key,start,end);
    }

    public List get(Collection collection){
        return redisTemplate.opsForValue().multiGet(collection);
    }

    public void set(Map map){
        redisTemplate.opsForValue().multiSet(map);
    }

    public void hset(String redisKey,String hashKey,String value){
        if (redisKey == null){
            throw new RuntimeException();
        }
        redisTemplate.opsForHash().put(redisKey,hashKey,value);
    }

    public void hset(String redisKey,String hashKey,String value,long timestamp){
        if (redisKey == null){
            throw new RuntimeException();
        }
        redisTemplate.opsForHash().put(redisKey,hashKey,value);
        redisTemplate.expire(redisKey,timestamp,TimeUnit.MILLISECONDS);
    }

    public String hget(String redisKey,String hashKey){
        if (redisKey == null){
            throw new RuntimeException();
        }
        return (String)redisTemplate.opsForHash().get(redisKey,hashKey);
    }

    public void incre(String key){
        redisTemplate.opsForValue().increment(key,1L);
    }

}


