package com.bagus.pemantauanbencana.source.apiservice

import com.bagus.pemantauanbencana.source.remote.response.DataResponse
import com.bagus.pemantauanbencana.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("get_all_disaster.php")
    fun getAllDisaster(
        @Query("table") table: String = "v_disasterlogs_all",
        @Query("action") action: String = "list"
    ): Call<DataResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun getUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>
}