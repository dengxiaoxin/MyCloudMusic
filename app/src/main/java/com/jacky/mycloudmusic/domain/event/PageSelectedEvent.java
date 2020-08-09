package com.jacky.mycloudmusic.domain.event;

/**
 * 登录成功事件
 */
public class PageSelectedEvent {
    private int position;

    public PageSelectedEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
