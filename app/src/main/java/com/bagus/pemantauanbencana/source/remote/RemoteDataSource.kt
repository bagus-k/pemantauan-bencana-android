package com.bagus.pemantauanbencana.source.remote

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.bagus.pemantauanbencana.source.apiservice.ApiConfig
import com.bagus.pemantauanbencana.source.remote.response.DataResponse
import com.bagus.pemantauanbencana.source.remote.response.DisasterResponse
import com.bagus.pemantauanbencana.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
//class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    private val handler = Handler(Looper.getMainLooper())

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
        client.enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                callback.onDisasterLoaded(response.body()?.data)
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getMovies onFailure : ${t.message}")
            }
        })
    }

    fun getUserData(callback: LoadUserCallback, email:String, password:String) {
        val client = ApiConfig.getApiService().getUser(email,password)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                callback.onUserLoaded(response.body())
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getUser onFailure : ${t.message}")
            }
        })
    }

    interface LoadDisasterCallback {
        fun onDisasterLoaded(disaster : List<DisasterResponse>?)
    }

    interface LoadUserCallback {
        fun onUserLoaded(user : UserResponse?)
    }

}