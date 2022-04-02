package com.tradoon.bookMall.service.impl;

import com.tradoon.bookMall.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void set(String key, Object value, Long time) {
        redisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
    }

    @Override
    public void del(String key) {
    redisTemplate.delete(key);
    }

    @Override
    public void del(List<String> keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public Object get(String key) {
       return  redisTemplate.opsForValue().get(key);
    }

    @Override
    public void update(String key, Object value) {
//        del(key);
        set(key,value);
    }
}
