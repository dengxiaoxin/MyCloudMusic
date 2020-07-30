package com.jacky.mycloudmusic.util;

import com.jacky.mycloudmusic.listener.Consumer;

import java.util.List;

/**
 * 列表工具类
 */
public class ListUtil {
    /**
     * 遍历每一个listener
     *
     * @param data   需遍历的列表
     * @param action 对每个对象所要进行的处理方法
     * @param <T>    对象类型
     */
    public static <T> void eachListener(List<T> data, Consumer<T> action) {
        for (T listener : data
        ) {
            //action处理列表中每一个对象
            action.accept(listener);
        }
    }
}
