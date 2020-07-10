package com.jacky.mycloudmusic.networkapi;

import com.jacky.mycloudmusic.domain.BaseModel;
import com.jacky.mycloudmusic.domain.Session;
import com.jacky.mycloudmusic.domain.Sheet;
import com.jacky.mycloudmusic.domain.User;
import com.jacky.mycloudmusic.domain.response.DetailResponse;
import com.jacky.mycloudmusic.domain.response.ListResponse;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.LogUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPI {
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
}
