package com.douyu.supermap.shakingmap.config.redis;

import com.douyu.superman.shakingmap.parent.ShakingmapApplicationTests;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisManagerImpTest extends ShakingmapApplicationTests{
    @Autowired
    private RedisManagerImp redisManagerImp;

    @Test
    public void test1(){
        redisManagerImp.set("s","abc");
    }

    @Test
    public void test2(){
        String s = redisManagerImp.get("s");
        System.out.println(s);
    }

    @Test
    public void test3(){
        redisManagerImp.set("a","aaa",80000);
    }

    @Test
    public void test4(){
        Map map = new HashMap();
        map.put("q1","q11");
        map.put("q2","q22");
        map.put("q3","q33");
        redisManagerImp.set(map);
    }

    @Test
    public void test5(){
        List list = Lists.newArrayList("q1","q3","s","a");
        List res = redisManagerImp.get(list);
        System.out.println(res);
    }

    @Test
    public void test6(){
        String value = redisManagerImp.get("q1",1,400);
        String value2 = redisManagerImp.get("a",1,50);
        String value3 =  redisManagerImp.get("a",100,500);
        System.out.println("1--"+value);
        System.out.println("2--"+value2);
        System.out.println("3--"+value3);
    }

    @Test
    public void test7(){
        redisManagerImp.hset("hash1","key1","value1",80000);
    }

    @Test
    public void test8(){
        String value = redisManagerImp.hget("hash1","key1");
        System.out.println(value);
    }
}