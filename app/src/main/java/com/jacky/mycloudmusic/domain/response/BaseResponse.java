package com.jacky.mycloudmusic.domain.response;

public class BaseResponse {

    /**
     * 状态码
     *
     * 只有发生了错误才会有
     */
    private Integer status;

    /**
     * 出错的提示信息
     *
     * 发生了错误不一定有
     */
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
