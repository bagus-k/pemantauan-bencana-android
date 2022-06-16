package com.bagus.pemantauanbencana.source.remote

import androidx.lifecycle.LiveData
import com.bagus.pemantauanbencana.source.local.*
import com.bagus.pemantauanbencana.source.remote.response.DisasterTypesDataResponse
import com.bagus.pemantauanbencana.source.remote.response.ProvinceDataResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET

interface DisasterDataSource {
    fun getAllDisaster(): LiveData<List<DisasterEntity>>
    fun getUserData(email: String, password: String): LiveData<UserEntity>
    fun getFilterDisaster(days: String, disaster: ArrayList<String>): LiveData<List<DisasterEntity>>
    fun getDetailDisaster(idLogs: String): LiveData<DisasterEntity>
    fun getAllDisasterTypes(): LiveData<List<DisasterTypesEntity>>
    fun getAllEastJavaRegencies(): LiveData<List<ProvinceEntity>>
    fun updateDisaster(idLogs: Int, dateTime: String, disasterType: Int, regency: Int, area: String, latitude: Float, longitude: Float, weather: String, chronology: String, dead: Int, missing: Int, seriousWound: Int, minorInjuries: Int, damage: String, losses: String, response: String, source: String, status: String, level: String, operatorId: String): LiveData<DisasterUpdateEntity>
}