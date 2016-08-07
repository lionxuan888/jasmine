package com.tian.designpattern.observer;

import java.util.Observer;

/**
 * Created by Administrator on 2016/8/4.
 */
public class Client {

    public static void main(String[] args) {

        NewsPaperSubject subject = new NewsPaperSubject();
        Observer observer = new Reader();
        subject.addObserver(observer);
        subject.updateContent("奥运会开幕!");
        System.out.println("成功!!");
    }
}
