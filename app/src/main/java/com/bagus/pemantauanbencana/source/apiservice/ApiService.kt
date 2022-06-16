package com.bagus.pemantauanbencana.source.apiservice

import com.bagus.pemantauanbencana.source.remote.response.*
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

    @GET("get_all_disaster_types.php")
    fun getAllDisasterTypes(
    ): Call<DisasterTypesDataResponse>

    @GET("get_all_east_java_regencies.php")
    fun getAllEastJavaRegencies(
    ): Call<ProvinceDataResponse>

    @FormUrlEncoded
    @POST("disaster_data_update.php")
    fun updateDisaster(
        @Field("id_logs") idLogs: Int,
        @Field("date_time") dateTime: String,
        @Field("disaster_type") disasterType: Int,
        @Field("regency") regency: Int,
        @Field("area") area: String,
        @Field("latitude") latitude: Float,
        @Field("longitude") longitude: Float,
        @Field("weather") weather: String,
        @Field("chronology") chronology: String,
        @Field("dead") dead: Int,
        @Field("missing") missing: Int,
        @Field("serious_wound") seriousWound: Int,
        @Field("minor_injuries") minorInjuries: Int,
        @Field("damage") damage: String,
        @Field("losses") losses: String,
        @Field("response") response: String,
        @Field("source") source: String,
        @Field("status") status: String,
        @Field("level") level: String,
        @Field("operator_id") operatorId: String
    ): Call<DisasterUpdateDataResponse>
}