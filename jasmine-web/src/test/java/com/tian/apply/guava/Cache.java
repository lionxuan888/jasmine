package com.tian.apply.guava;

import com.google.common.base.Suppliers;
import com.google.common.cache.*;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiaoxuan.jin on 2016/11/25.
 */
public class Cache {


    @Test
    public void testGuavaCache() throws Exception {
        LoadingCache<String, String> graphs = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .removalListener(new RemovalListener<String, String>() {
                    public void onRemoval(RemovalNotification<String, String> notification) {
                        System.out.println("remove listener: key: " + notification.getKey() + "   " + notification.getValue());
                    }
                })
                .build(new CacheLoader<String, String>() {
                            public String load(String key)  {
                                System.out.println("真实调用：key:" + key);
                                return "hello world";
                            }
                        });
        String he = graphs.get("你好");
        System.out.println("返回值： " + he);
        String secondH = graphs.get("你好");
        System.out.println("返回值： " + secondH);
        TimeUnit.SECONDS.sleep(7);
        String thirdH = graphs.get("你好");
        System.out.println("程序退出");


        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    public String load(String key) throws Exception {
                        return null;
                    }
                });
    }
}
