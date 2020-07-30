package com.jacky.mycloudmusic.listener;

/**
 * 消费者接口(函数式接口)
 * 代表接受一个输入参数并且无返回的操作，相当于消费者
 */
public interface Consumer<T> {

    void accept(T t);

}
