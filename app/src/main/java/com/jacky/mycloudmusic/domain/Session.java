package com.jacky.mycloudmusic.domain;

import org.jetbrains.annotations.NotNull;

/**
 * 登录后模型
 * 用来在登录完成后，解析返回信息
 * 因为不需要向外传递，所以不需要继承Serializable
 */
public class Session {
    /**
     * 这两个字段名称不能更改，因为该服务器端返回的字段就是这样的
     * 用户Id
     */
    private String user;

    /**
     * 登录后的Session
     */
    private String session;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @NotNull
    @Override
    public String toString() {
        return "Session{" +
                "user='" + user + '\'' +
                ", session='" + session + '\'' +
                '}';
    }
}
