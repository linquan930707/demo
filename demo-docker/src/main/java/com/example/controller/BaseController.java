package com.example.controller;

import com.example.base.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.util.calendar.CalendarUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * lusq
 * 2019/5/16 15:46
 */

@RestController
@RequestMapping("/basecontroller")
public class BaseController {



    @Autowired
    private RedisCacheUtil redisutil;

    @Autowired
    @Qualifier("redisTemplate")
    public RedisTemplate redisTemplate;


/*    @Autowired
    @Qualifier("redisTemplate")
    public RedisTemplate<String,String> redisTemplate;*/

    @GetMapping("/1")
    public String getRedisVal(){
        String key = "hhh1";
        String val = "12121";
     //   redisutil.setCacheObject("hhh","23ddddd");

        redisTemplate.opsForValue().set("123","456");
        redisTemplate.expire("123",1000, TimeUnit.MILLISECONDS);
     /*   try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    //    return redisTemplate.opsForValue().setIfAbsent("123","456")==true?"true":"false";
      //  return  redisutil.setIfAbsent(key,val);
       /* return (String) redisutil.getCacheObject("hhh");*/



        return redisTemplate.opsForValue().increment("incrementKey",1).toString();

    }

    @GetMapping("/2")
    public List demo2(){

        List<Object>obj = new ArrayList<>();
        obj.add("aaa");
        obj.add("bbb");
        obj.add("ccc");
        redisTemplate.opsForList().leftPushAll("listone",obj);
        redisTemplate.opsForList().remove("listone",1,"bbb");
        return  redisTemplate.opsForList().range("listone",3,3);
    }
    @GetMapping("/3")
    public  Set demo3(){
        Set<ZSetOperations.TypedTuple<String>> sets = new HashSet<>();
        ZSetOperations.TypedTuple<String> aaa = new DefaultTypedTuple<String>("aaa",new Random().nextDouble());
        ZSetOperations.TypedTuple<String> bbb = new DefaultTypedTuple<String>("bbb",new Random().nextDouble());
        sets.add(aaa);
        sets.add(bbb);
        redisTemplate.opsForZSet().add("zsetRedis",sets);


        Set<ZSetOperations.TypedTuple<Object>>zst =  redisTemplate.opsForZSet().reverseRange("zsetRedis",0,2);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = zst.iterator();
        while (iterator.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
        }

        return null;





    }




}
