package com.tian.apply.designpattern.observer;

import java.util.Observable;

/**
 * Created by Administrator on 2016/8/4.
 */
public class NewsPaperSubject extends Observable{

    public String content;

    public void  updateContent(String content) {
        this.content = content;
        synchronized (content) {

        }
        this.notifyObservers("哈哈");
    }

    public String getContent() {
        return content;
    }
}
