package com.tian.apply.designpattern.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2016/8/4.
 */
public class Reader implements Observer {

    public void update(Observable o, Object arg) {
        NewsPaperSubject subject = (NewsPaperSubject)o;
        String content = subject.getContent();
        System.out.println("我读到了内容, " + content);
    }
}
