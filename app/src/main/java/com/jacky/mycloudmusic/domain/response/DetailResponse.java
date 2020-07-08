package com.jacky.mycloudmusic.domain.response;

/**
 * 详情网络请求解析类
 */
public class DetailResponse<T> extends BaseResponse {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
