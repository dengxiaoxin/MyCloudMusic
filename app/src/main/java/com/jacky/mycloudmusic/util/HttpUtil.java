package com.jacky.mycloudmusic.util;

import android.text.TextUtils;

import com.jacky.mycloudmusic.AppContext;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.domain.response.BaseResponse;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;
import retrofit2.Response;

public class HttpUtil {

    /**
     * 网络请求错误处理
     */
    public static void requestErrorHandler(Object data, Throwable error) {
        if (error != null) {
            //先判断网络异常
            //判断错误类型
            if (error instanceof UnknownHostException) {
                ToastUtil.errorShortToast(R.string.error_network_unknown_host);
            } else if (error instanceof ConnectException) {
                ToastUtil.errorShortToast(R.string.error_network_connect);
            } else if (error instanceof SocketTimeoutException) {
                ToastUtil.errorShortToast(R.string.error_network_timeout);
            } else if (error instanceof HttpException) {
                HttpException exception = (HttpException) error;

                //获取响应码
                int code = exception.code();

                handleHttpError(code);
            } else {
                ToastUtil.errorShortToast(R.string.error_network_unknown);
            }
        } else {
            //再判断业务异常
            if (data instanceof Response) {
                //retrofit里面的对象

                //获取响应对象
                Response response = (Response) data;

                //获取响应码
                int code = response.code();

                //判断响应码
                if (code >= 200 && code <= 299) {
                    //网络请求正常
                } else {
                    handleHttpError(code);
                }
            } else if (data instanceof BaseResponse) {
                BaseResponse response = (BaseResponse) data;

                String message = response.getMessage();
                if (TextUtils.isEmpty(message)) {
                    //没返回错误信息提示
                    ToastUtil.errorShortToast(R.string.error_network_unknown);
                } else {
                    ToastUtil.errorShortToast(message);
                }
            }
        }
    }

    /**
     * 处理Http错误
     *
     * @param code
     */
    private static void handleHttpError(int code) {
        if (code == 401) {
            ToastUtil.errorShortToast(R.string.error_network_not_auth);
            AppContext.getInstance().logout();
        } else if (code == 403) {
            ToastUtil.errorShortToast(R.string.error_network_not_permission);
        } else if (code == 404) {
            ToastUtil.errorShortToast(R.string.error_network_not_found);
        } else if (code >= 500) {
            ToastUtil.errorShortToast(R.string.error_network_server);
        } else {
            ToastUtil.errorShortToast(R.string.error_network_unknown);
        }
    }
}
