package com.example.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

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
     * ===================================List==================================
     */
    //返回存储在键中的列表的指定元素。start-开始下标，end-结束的下标
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
     *
     * @param key
     * @param index 第几个
     * @param val  删除的值
     */
    public void remove(String key,Long index,String val){
        redisTemplate.opsForList().remove(key,index,val);
    }

    /**
     * 从左边取出第一个值，key列表删除该值
     * @param key
     * @return
     */
    public Object leftPop(String key){
        return redisTemplate.opsForList().leftPop(key);
    }
    //用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回。
    public Object rightPopAndLeftPush(String sourceKey,String targetKey){
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,targetKey);
    }




    /**
     * =======================================String============================================
     */
    //设置键的字符串值并返回其旧值
    public Object getAndSetVal(String key,Object val){
        return redisTemplate.opsForValue().getAndSet(key,val);
    }
    //为多个键分别取出它们的值
    public List<T>multiGet(List<String>key){
        return redisTemplate.opsForValue().multiGet(key);
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

    //在原来的值的基础上加val并返回加之后的值
    public Long increment(String key,Long val){
        return redisTemplate.opsForValue().increment(key,val);
    }



    //=========================================Hash==================================================

    public void putHash(String k1,String k2,String val){
        redisTemplate.opsForHash().put(k1,k2,val);
    }

    public void putHashAll(String key, HashMap map){
        redisTemplate.opsForHash().putAll(key,map);
    }

    public void delete(String key,String...hashKey){
        redisTemplate.opsForHash().delete(key,hashKey);
    }
    //获取整个hash的值
    public Map entries(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    public Object getKey(String k1,String k2){
        return redisTemplate.opsForHash().get(k1,k2);
    }

    //获取key中指定hash字段的值 返回list
    public List multiGet(String key, Collection collection){
        return  redisTemplate.opsForHash().multiGet(key,collection);
    }

    public Long incrementHashVal(String k1,String k2,Long v){
        return redisTemplate.opsForHash().increment(k1,k2,v);
    }

    //put / putAll
    public void putAllHash(String key,Map map){
        redisTemplate.opsForHash().putAll(key,map);
    }

    //当hash不存在时候设置值返回true,当不存在时候不设置值返回false
    public Boolean putIfAbsent(String k1,String k2,Map map){
        return redisTemplate.opsForHash().putIfAbsent(k1,k2,map);
    }


    //=================================SET=============================================

    public Long add(String k,Object...v){
        return redisTemplate.opsForSet().add(k,v);
    }

    public Long remove(String k,Object...v){
        return redisTemplate.opsForSet().remove(k,v);
    }
    //移除并返回集合中的一个随机元素
    public Object pop(String k){
        return redisTemplate.opsForSet().pop(k);
    }
    public Boolean isMember(String k,Object o){
        return redisTemplate.opsForSet().isMember(k,o);
    }
    //返回两个key 中的交集 (K key, K otherKey)/ (K key, Collection<K> otherKeys);
    //交集intersectAndStore(K key, Collection<K> otherKeys, K destKey);
    //---合集 Set<V> union(K key, K otherKey);/Set<V> union(K key, Collection<K> otherKeys);/Long unionAndStore(K key, K otherKey, K destKey);/ unionAndStore(K key, Collection<K> otherKeys, K destKey);
    public Set intersect(String k1,String k2){
        return redisTemplate.opsForSet().intersect(k1,k2);
    }



    /////===========================================ZSet======================================================

    public void reverseRange(String k1,Long start,Long end){
        redisTemplate.opsForZSet().reverseRange(k1,start,end);
    }








}
