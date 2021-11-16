package com.exmobile.employeefamilyandroid.event;

/**
 * @author thanatos
 * @create 2016-01-05
 **/
public class Events<T> {

    public enum EventEnum {
        DELIVER_LOGIN,DELIVER_YEAR,DELIVER_VOTE
    }

    public EventEnum what;
    public T message;

    public static <O> Events<O> just(O t) {
        Events<O> events = new Events<>();
        events.message = t;
        return events;
    }

    public <T> T getMessage() {
        return (T) message;
    }

}
