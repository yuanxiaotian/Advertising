package com.cangmaomao.advertising

import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.*
import retrofit2.http.FormUrlEncoded


interface ApiConfig {

    //登录
    @GET("login")
    fun login(@QueryMap() map: Map<String, String>): Observable<Login>

    //获取基本数据
    @POST("Map/Monit/GetMonitTreeDataAjax")
    fun getOrgTreeAjax(): Observable<CarInfo>

    //获取车辆位置
    @POST
    fun carLocation(@Url url: String, @QueryMap() map: Map<String, String>): Observable<CarLocation>


}