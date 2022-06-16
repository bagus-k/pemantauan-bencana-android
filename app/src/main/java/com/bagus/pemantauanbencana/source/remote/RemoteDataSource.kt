package com.bagus.pemantauanbencana.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import com.bagus.pemantauanbencana.source.apiservice.ApiConfig
import com.bagus.pemantauanbencana.source.local.DisasterTypesEntity
import com.bagus.pemantauanbencana.source.local.ProvinceEntity
import com.bagus.pemantauanbencana.source.remote.response.*
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
                Log.e("RemoteDataSource", "getAllDisaster onFailure : ${t.message}")
            }
        })
    }

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
                Log.e("RemoteDataSource", "getUserData onFailure : ${t.message}")
            }
        })
    }

    fun getAllDisasterTypes(callback: LoadDisasterTypesCallback) {
        val client = ApiConfig.getApiService().getAllDisasterTypes()
        client.enqueue(object : Callback<DisasterTypesDataResponse> {
            override fun onResponse(call: Call<DisasterTypesDataResponse>, responseDisaster: Response<DisasterTypesDataResponse>) {
                callback.onDisasterTypesLoaded(responseDisaster.body()?.data)
            }

            override fun onFailure(call: Call<DisasterTypesDataResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getAllDisasterType onFailure : ${t.message}")
            }
        })
    }

    fun getAllEastJavaRegencies(callback: LoadProvinceCallback) {
        val client = ApiConfig.getApiService().getAllEastJavaRegencies()
        client.enqueue(object : Callback<ProvinceDataResponse> {
            override fun onResponse(call: Call<ProvinceDataResponse>, response: Response<ProvinceDataResponse>) {
                callback.onProvinceLoaded(response.body()?.data)
            }

            override fun onFailure(call: Call<ProvinceDataResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "getAllJavaRegencies onFailure : ${t.message}")
            }
        })
    }

    fun updateDisaster(callback: LoadUpdateDisasterCallback, idLogs: Int, dateTime: String, disasterType: Int, regency: Int, area: String, latitude: Float, longitude: Float, weather: String, chronology: String, dead: Int, missing: Int, seriousWound: Int, minorInjuries: Int, damage: String, losses: String, response: String, source: String, status: String, level: String, operatorId: String) {
        val client = ApiConfig.getApiService().updateDisaster(idLogs, dateTime, disasterType, regency, area, latitude, longitude, weather, chronology, dead, missing, seriousWound, minorInjuries, damage, losses, response, source, status, level, operatorId)
        client.enqueue(object : Callback<DisasterUpdateDataResponse> {
            override fun onResponse(call: Call<DisasterUpdateDataResponse>, response: Response<DisasterUpdateDataResponse>) {
                callback.onUpdateDisasterLoaded(response.body()?.data)
            }

            override fun onFailure(call: Call<DisasterUpdateDataResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "updateDisaster onFailure : ${t.message}")
            }
        })
    }

    interface LoadDisasterCallback {
        fun onDisasterLoaded(disaster : List<DisasterResponse>?)
    }

    interface LoadUserCallback {
        fun onUserLoaded(user : List<UserResponse>?)
    }

    interface LoadDisasterTypesCallback {
        fun onDisasterTypesLoaded(disasterTypes: List<DisasterTypeResponse>?)
    }

    interface LoadProvinceCallback {
        fun onProvinceLoaded(regencies: List<ProvinceResponse>?)
    }

    interface LoadUpdateDisasterCallback {
        fun onUpdateDisasterLoaded(disaster: List<DisasterUpdateResponse>?)
    }
}