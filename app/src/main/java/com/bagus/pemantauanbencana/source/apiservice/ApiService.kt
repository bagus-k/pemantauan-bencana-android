package com.bagus.pemantauanbencana.source.apiservice

import com.bagus.pemantauanbencana.source.remote.response.DisasterDataResponse
import com.bagus.pemantauanbencana.source.remote.response.UserDataResponse
import com.bagus.pemantauanbencana.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("get_all_disaster.php")
    fun getAllDisaster(
    ): Call<DisasterDataResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun getUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<UserDataResponse>

    @GET("filter_disaster.php")
    fun getFilterDisaster(
        @Query("days") days: String,
        @Query("disaster[]") disaster: ArrayList<String>
    ): Call<DisasterDataResponse>

    @GET("get_detail.php")
    fun getDetailDisaster(
        @Query("idlogs") idLogs: String,
    ): Call<DisasterDataResponse>
}