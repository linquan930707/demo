package com.example.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Date: 2019/3/21 11:54
 * @Auther: 佳山
 */
@Service
public class RedisCacheUtil<T> {
    @SuppressWarnings("rawtypes")
    @Autowired
    @Qualifier("redisTemplate")
    public RedisTemplate redisTemplate;

    /**
     * 根据单个key值删除缓存
     */
    @SuppressWarnings("unchecked")
    public void delCacheObject(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 根据多个key值删除缓存
     */
    @SuppressWarnings("unchecked")
    public void delCacheObjects(Collection<Object> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    @SuppressWarnings({"unchecked", "hiding"})
    public <T> ValueOperations<String, T> setCacheObject(String key, T value, long time, TimeUnit timeUnit) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value, time, timeUnit);
        return operation;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    @SuppressWarnings({"unchecked", "hiding"})
    public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    @SuppressWarnings({"unchecked", "hiding"})
    public <T> ValueOperations<String, T> setCacheObject(String key, T value, long timeout) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    @SuppressWarnings({"unchecked", "hiding"})
    public <T> T getCacheObject(
            String key/* ,ValueOperations<String,T> operation */) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    @SuppressWarnings({"unchecked", "hiding", "rawtypes"})
    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperation.rightPush(key, dataList.get(i));
            }
        }

        return listOperation;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    @SuppressWarnings({"unchecked", "hiding"})
    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList<T>();
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for (int i = 0; i < size; i++) {
            dataList.add((T) listOperation.leftPop(key));
        }

        return dataList;
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    @SuppressWarnings({"unchecked", "hiding"})
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        /*
         * T[] t = (T[]) dataSet.toArray(); setOperation.add(t);
         */
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<T> getCacheSet(
            String key/* ,BoundSetOperations<String,T> operation */) {
        Set<T> dataSet = new HashSet<T>();
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);

        Long size = operation.size();
        for (int i = 0; i < size; i++) {
            dataSet.add(operation.pop());
        }
        return dataSet;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    @SuppressWarnings({"unchecked", "hiding", "rawtypes"})
    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<String, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue().toString());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    @SuppressWarnings({"unchecked", "hiding"})
    public <T> Map<String, T> getCacheMap(
            String key/* ,HashOperations<String,String,T> hashOperation */) {
        Map<String, T> map = redisTemplate.opsForHash().entries(key);
        /* Map<String, T> map = hashOperation.entries(key); */
        return map;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    @SuppressWarnings({"unchecked", "hiding", "rawtypes"})
    public <T> HashOperations<String, Integer, T> setCacheIntegerMap(String key, Map<Integer, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<Integer, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    @SuppressWarnings({"unchecked", "hiding"})
    public <T> Map<Integer, T> getCacheIntegerMap(
            String key/* ,HashOperations<String,String,T> hashOperation */) {
        Map<Integer, T> map = redisTemplate.opsForHash().entries(key);
        /* Map<String, T> map = hashOperation.entries(key); */
        return map;
    }

    /**
     * 设置缓存过期时间
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public void setExpires(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }




    public String setIfAbsent(String key ,String val){
        return redisTemplate.opsForValue().setIfAbsent(key,val)==true?"true":"false";
    }
}
