package com.jacky.mycloudmusic.listener;

import com.jacky.mycloudmusic.activity.BaseCommonActivity;
import com.jacky.mycloudmusic.domain.response.BaseResponse;
import com.jacky.mycloudmusic.util.HttpUtil;
import com.jacky.mycloudmusic.util.LoadingUtil;
import com.jacky.mycloudmusic.util.LogUtil;

import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * 网络请求Observer
 * 实现自动处理网络请求错误
 */
public abstract class HttpObserver<T> extends ObserverClass<T> {
    private static final String TAG = "HttpObserver";

    private final BaseCommonActivity activity;
    private final boolean isShowLoading;

    public HttpObserver() {
        activity = null;
        isShowLoading = false;
    }

    /**
     * 可以控制是否显示加载框构造方法
     * @param activity 加载框显示的上下文
     * @param isShowLoading 是否显示加载框
     */
    public HttpObserver(BaseCommonActivity activity, boolean isShowLoading) {
        this.activity = activity;
        this.isShowLoading = isShowLoading;
    }

    /**
     * 请求成功
     */
    public abstract void onSucceeded(T t);

    /**
     * 请求失败
     * @return 使用默认异常处理方式返回false，自定义处理方式返回true
     */
    public boolean onFailed(T t, Throwable e) {
        return false;
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);

        if (isShowLoading) {
            //显示加载提示框
            LoadingUtil.showLoading(activity);
        }
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);

        LogUtil.d(TAG, "onNext:" + t);

        if (isShowLoading) {
            //隐藏加载提示框
            LoadingUtil.hideLoading();
        }

        //判断请求是否正常
        if (isSucceeded(t)) {
            //请求正常
            onSucceeded(t);
        } else {
            //有状态码
            //表示请求出错(业务异常)
            requestErrorHandler(t, null);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);

        LogUtil.d(TAG, "onError:" + e.getLocalizedMessage());

        if (isShowLoading) {
            //隐藏加载提示框
            LoadingUtil.hideLoading();
        }

        requestErrorHandler(null, e);
    }

    private boolean isSucceeded(T t) {
        if (t instanceof Response) {
            //retrofit里面的响应对象

            //获取响应对象
            Response response = (Response) t;

            //获取响应码
            int code = response.code();

            //判断响应码
            if (code >= 200 && code <= 299) {
                //网络请求正常
                return true;
            }

        } else if (t instanceof BaseResponse) {
            //判断具体的业务请求
            BaseResponse response = (BaseResponse) t;

            //没有状态码表示请求成功
            //这是服务端的规定
            return response.getStatus() == null;
        }

        return false;
    }

    /**
     * 处理请求错误
     */
    private void requestErrorHandler(T data, Throwable error) {
        //如果onFailed返回false，默认异常处理方式；
        //反之，则重写onFailed，自定义处理方式
        if (!onFailed(data, error)) {
            HttpUtil.requestErrorHandler(data, error);
        }
    }
}
