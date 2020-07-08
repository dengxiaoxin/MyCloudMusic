package com.jacky.mycloudmusic.networkapi;

import com.jacky.mycloudmusic.domain.Sheet;
import com.jacky.mycloudmusic.domain.response.DetailResponse;
import com.jacky.mycloudmusic.domain.response.ListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
}
