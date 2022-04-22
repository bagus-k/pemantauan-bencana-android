package com.bagus.pemantauanbencana.source.remote

import android.util.Log
import com.bagus.pemantauanbencana.source.apiservice.ApiConfig
import com.bagus.pemantauanbencana.source.remote.response.DisasterDataResponse
import com.bagus.pemantauanbencana.source.remote.response.DisasterResponse
import com.bagus.pemantauanbencana.source.remote.response.UserDataResponse
import com.bagus.pemantauanbencana.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
//class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    companion object {

        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getAllDisaster(callback: LoadDisasterCallback) {
        val client = ApiConfig.getApiService().getAllDisaster()
        client.enqueue(object : Callback<DisasterDataResponse> {
            override fun onResponse(call: Call<DisasterDataResponse>, responseDisaster: Response<DisasterDataResponse>) {
                callback.onDisasterLoaded(responseDisaster.body()?.data)
            }

            override fun onFailure(call: Call<DisasterDataResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getDisaster onFailure : ${t.message}")
            }
        })
    }

//    fun getFilterDisaster(callback: LoadDisasterCallback, days:String, disaster: ArrayList<String>) {
//        val client = ApiConfig.getApiService().getFilterDisaster(days, disaster)
//        client.enqueue(object : Callback<DisasterDataResponse> {
//            override fun onResponse(call: Call<DisasterDataResponse>, responseDisaster: Response<DisasterDataResponse>) {
//                callback.onDisasterLoaded(responseDisaster.body()?.data)
//            }
//
//            override fun onFailure(call: Call<DisasterDataResponse>, t: Throwable) {
//                Log.e("RemoteDataSource", "getFilterDisaster onFailure : ${t.message}")
//            }
//        })
//    }

    fun getFilterDisaster(callback: LoadDisasterCallback, days:String, disaster: ArrayList<String>) {
        val client = ApiConfig.getApiService().getFilterDisaster(days, disaster)
        client.enqueue(object : Callback<DisasterDataResponse> {
            override fun onResponse(call: Call<DisasterDataResponse>, responseDisaster: Response<DisasterDataResponse>) {
                callback.onDisasterLoaded(responseDisaster.body()?.data)
            }

            override fun onFailure(call: Call<DisasterDataResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getFilterDisaster onFailure : ${t.message}")
            }
        })
    }

    fun getDisasterDetail(callback: LoadDisasterCallback, idLogs:String) {
        val client = ApiConfig.getApiService().getDetailDisaster(idLogs)
        client.enqueue(object : Callback<DisasterDataResponse> {
            override fun onResponse(call: Call<DisasterDataResponse>, responseDisaster: Response<DisasterDataResponse>) {
                callback.onDisasterLoaded(responseDisaster.body()?.data)
            }
            override fun onFailure(call: Call<DisasterDataResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getDisasterDetail onFailure : ${t.message}")
            }
        })
    }

    fun getUserData(callback: LoadUserCallback, username:String, password:String) {
        val client = ApiConfig.getApiService().getUser(username,password)
        client.enqueue(object : Callback<UserDataResponse> {
            override fun onResponse(call: Call<UserDataResponse>, responseUser: Response<UserDataResponse>) {
                callback.onUserLoaded(responseUser.body()?.data)
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getUser onFailure : ${t.message}")
            }
        })
    }

    interface LoadDisasterCallback {
        fun onDisasterLoaded(disaster : List<DisasterResponse>?)
    }

    interface LoadUserCallback {
        fun onUserLoaded(user : List<UserResponse>?)
    }

}