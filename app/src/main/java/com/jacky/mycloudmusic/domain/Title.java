package com.jacky.mycloudmusic.domain;

import com.jacky.mycloudmusic.util.Constant;

/**
 * 主界面标题
 */
public class Title extends BaseMultiItemEntity {
    private String title;

    public Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 返回Item类型
     * <p>
     * 对应现在使用的BaseRecyclerViewAdapterHelper框架
     * 如果要显示多类型这就是固定写法
     */
    @Override
    public int getItemType() {
        return Constant.TYPE_TITLE;
    }
}
