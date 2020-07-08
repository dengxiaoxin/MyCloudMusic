package com.jacky.mycloudmusic.domain.response;

import java.util.List;

/**
 * 详情网络请求解析类
 */
public class ListResponse<T> extends BaseResponse {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
