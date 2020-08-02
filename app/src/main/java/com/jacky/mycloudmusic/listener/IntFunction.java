package com.jacky.mycloudmusic.listener;

public interface IntFunction<R> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(int value);
}
