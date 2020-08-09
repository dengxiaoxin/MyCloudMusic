package com.jacky.mycloudmusic.networkapi;

import com.jacky.mycloudmusic.domain.Ad;
import com.jacky.mycloudmusic.domain.BaseModel;
import com.jacky.mycloudmusic.domain.Session;
import com.jacky.mycloudmusic.domain.Sheet;
import com.jacky.mycloudmusic.domain.Song;
import com.jacky.mycloudmusic.domain.User;
import com.jacky.mycloudmusic.domain.Video;
import com.jacky.mycloudmusic.domain.response.DetailResponse;
import com.jacky.mycloudmusic.domain.response.ListResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RequestAPI {

    /**
     * 歌单列表
     */
    @GET("v1/sheets")
    Observable<ListResponse<Sheet>> requestSheetList();

    /**
     * 歌单详情
     */
    @GET("v1/sheets/{id}")
    Observable<DetailResponse<Sheet>> requestSheetDetail(@Path("id") String id);

    /**
     * 登录
     */
    @POST("v1/sessions")
    Observable<DetailResponse<Session>> login(@Body User user);

    /**
     * 注册
     */
    @POST("v1/users")
    Observable<DetailResponse<BaseModel>> register(@Body User user);

    /**
     * 用户详情
     */
    @GET("v1/users/{id}")
    Observable<DetailResponse<User>> userDetail(@Path("id") String id, @QueryMap Map<String, String> data);

    /**
     * 单曲
     */
    @GET("v1/songs")
    Observable<ListResponse<Song>> songs();

    /**
     * 主界面广告列表
     */
    @GET("v1/ads")
    Observable<ListResponse<Ad>> ads();

    /**
     * 收藏歌单
     */
    @FormUrlEncoded
    @POST("v1/collections")
    Observable<Response<Void>> collect(@Field("sheet_id") String id);

    /**
     * 取消收藏歌单
     */
    @DELETE("v1/collections/{id}")
    Observable<Response<Void>> deleteCollect(@Path("id") String id);

    /**
     * 视频列表
     */
    @GET("v1/videos")
    Observable<ListResponse<Video>> videos();

    /**
     * 视频详情
     */
    @GET("v1/videos/{id}")
    Observable<DetailResponse<Video>> videoDetail(@Path("id") String id);
}
