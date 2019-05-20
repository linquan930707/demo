package com.example.dmeo;

import java.util.UUID;

/**
 * lusq
 * 2019/5/20 10:13
 */
public class demo {

    public static void main(String[] args) {


      String uuid =  UUID.randomUUID().toString().replace("-","");
        System.out.println(uuid);
        System.out.println(uuid.length());
    }
}
