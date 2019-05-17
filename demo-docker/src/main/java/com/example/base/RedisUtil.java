package com.example.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * lusq
 * 2019/5/17 14:24
 */
public class RedisUtil<T> {
    @SuppressWarnings("rawtypes")
    @Autowired
    @Qualifier("redisTemplate")
    public RedisTemplate redisTemplate;

    //判断是否有key
    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * =====================================================================
     */
    //返回存储在键中的列表的指定元素。偏移开始和停止是基于零的索引，其中0是列表的第一个元素（列表的头部），1是下一个元素
    public List rangeList(String key, Integer start, Integer end){
        return  redisTemplate.opsForList().range(key,start,end);
    }

    public Boolean deleteByKey(String key){
       return redisTemplate.delete(key);
    }


    public Long leftPushList(String key,Object val){
        return  redisTemplate.opsForList().leftPush(key,val.toString());
    }

    public Long leftPushListAll(String key,List<T>list){
        return redisTemplate.opsForList().leftPushAll(key,list);
    }

    public Long rightPushList(String key,Object val){
        return redisTemplate.opsForList().rightPush(key,val);
    }

    public Long rightPushListAll(String key,List<T>list){
        return redisTemplate.opsForList().rightPushAll(key,list);
    }

    //只有存在key对应的列表才能将这个value值插入到key所对应的列表中
    public void rightPushIfPresent(String key,Object val){
        redisTemplate.opsForList().rightPushIfPresent(key,val);
    }

    //剪切list。下标开始为0
    public void trimList(String key,Integer start,Integer end){
        redisTemplate.opsForList().trim(key,start,end);
    }

    public Long size(String key){
        return redisTemplate.opsForList().size(key);
    }





    /**
     * ===================================================================================
     */
    //设置键的字符串值并返回其旧值
    public Object getAndSetVal(String key,Object val){
        return redisTemplate.opsForValue().getAndSet(key,val);
    }
    //为多个键分别取出它们的值
    public List<T>multiGet(List<String>key){
        return redisTemplate.opsForValue().multiGet(key);
    }
    //取出值并加val
    public Long increment(String key,Long val){
        return redisTemplate.opsForValue().increment(key,val);
    }
    //如果有key 则val追加。如果key无，则将val赋值
    public Integer append(String key,String  val){
        return  redisTemplate.opsForValue().append(key,val);
    }

    //获取并截取值
    public String get(String key,Integer start,Integer end){
        return redisTemplate.opsForValue().get(key,start,end);
    }

    public void set(String key,String val){
        redisTemplate.opsForValue().set(key,val);
    }









}
