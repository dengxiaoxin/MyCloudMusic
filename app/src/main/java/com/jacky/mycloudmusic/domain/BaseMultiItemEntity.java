package com.jacky.mycloudmusic.domain;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 通用多类型Item数据模型
 */
public abstract class BaseMultiItemEntity extends BaseModel implements MultiItemEntity {
    /**
     * 1个Item占用多少列
     * 在当前项目中，默认1个Item占3个
     */
    public int getSpanSize() {
        return 3;
    }
}
