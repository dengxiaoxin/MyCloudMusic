package com.jacky.mycloudmusic.networkapi;

import android.text.TextUtils;

import com.jacky.mycloudmusic.AppContext;
import com.jacky.mycloudmusic.domain.Ad;
import com.jacky.mycloudmusic.domain.BaseModel;
import com.jacky.mycloudmusic.domain.Session;
import com.jacky.mycloudmusic.domain.Sheet;
import com.jacky.mycloudmusic.domain.Song;
import com.jacky.mycloudmusic.domain.User;
import com.jacky.mycloudmusic.domain.Video;
import com.jacky.mycloudmusic.domain.response.DetailResponse;
import com.jacky.mycloudmusic.domain.response.ListResponse;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.LogUtil;
import com.jacky.mycloudmusic.util.PreferencesUtil;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPI {
    private static final String TAG = "======RetrofitAPI";
    /**
     * RetrofitAPI单例字段
     */
    private static RetrofitAPI instance;

    /**
     * RequestAPI单例
     */
    private final RequestAPI requestAPI;

    /**
     * 私有构造方法
     */
    private RetrofitAPI() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //传递公共请求参数,通过OkHttp拦截器在请求头上添加公共的用户登录信息
        Interceptor netInterceptor = chain -> {
            //获取到偏好设置工具类
            PreferencesUtil sp = PreferencesUtil.getInstance(AppContext.getInstance());

            //获取到request
            Request request = chain.request();

            if (sp.isAlreadyLogin()) {
                //获取出用户Id和token
                String user = sp.getUserId();
                String session = sp.getSession();

                //打印日志方便调试
                LogUtil.d(TAG, "RetrofitAPI user:" + user + "," + session);

                //将用户id和token设置到请求头
                request = request.newBuilder()
                        .addHeader("User", user)
                        .addHeader("Authorization", session)
                        .build();
            }

            //继续执行网络请求
            return chain.proceed(request);
        };

        builder.addNetworkInterceptor(netInterceptor);

        if (LogUtil.isDebug) {
            //调试模式
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            //设置日志等级
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            builder.addInterceptor(interceptor);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(Constant.ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        requestAPI = retrofit.create(RequestAPI.class);
    }

    /**
     * 返回当前对象的唯一实例
     *
     * 单例设计模式
     * 由于移动端很少有高并发
     * 所以这个就是简单判断
     *
     */
    public static RetrofitAPI getInstance() {
        if (instance == null) {
            instance = new RetrofitAPI();
        }
        return instance;
    }

    /**
     * 歌单列表
     */
    public Observable<ListResponse<Sheet>> requestSheetList() {
        return requestAPI.requestSheetList()
                //固定写法
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 歌单详情
     */
    public Observable<DetailResponse<Sheet>> requestSheetDetail(String id) {
        return requestAPI.requestSheetDetail(id)
                //固定写法
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 登录
     */
    public Observable<DetailResponse<Session>> login(User user) {
        return requestAPI.login(user)
                //固定写法
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 注册
     */
    public Observable<DetailResponse<BaseModel>> register(User user) {
        return requestAPI.register(user)
                //固定写法
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用户详情
     * 参数id和nickname
     */
    public Observable<DetailResponse<User>> userDetail(String id, String nickname) {

        //添加查询参数
        HashMap<String, String> data = new HashMap<>();

        if (!TextUtils.isEmpty(nickname)) {
            //如果昵称不为空才添加
            data.put(Constant.NICKNAME, nickname);
        }

        return requestAPI.userDetail(id, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用户详情
     * 参数只有id
     */
    public Observable<DetailResponse<User>> userDetail(String id) {
        return userDetail(id, null);
    }

    /**
     * 单曲
     */
    public Observable<ListResponse<Song>> songs() {
        return requestAPI.songs()
                //固定写法
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 主界面广告列表
     */
    public Observable<ListResponse<Ad>> ads() {
        return requestAPI.ads()
                //固定写法
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 收藏歌单
     */
    public Observable<Response<Void>> collect(String id) {
        return requestAPI.collect(id)
                //固定写法
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 取消收藏歌单
     */
    public Observable<Response<Void>> deleteCollect(String id) {
        return requestAPI.deleteCollect(id)
                //固定写法
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 视频列表
     */
    public Observable<ListResponse<Video>> videos() {
        return requestAPI.videos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 视频详情
     */
    public Observable<DetailResponse<Video>> videoDetail(String id) {
        return requestAPI.videoDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
