package com.tradoon.bookMall.service;

import java.util.List;

/**
 * author:tradoon
 * desciption:
 * date:2022/02/11/
 */
public interface RedisService {
    //增
     void set(String key,Object value);
    //增+过期时间
    void set(String key,Object value,Long time);
    //删
    void del(String key);
    //批量删除
    void del(List<String> keys);
    //查
    Object get(String key);

    void update(String key,Object value);
}
