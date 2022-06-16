package com.bagus.pemantauanbencana.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bagus.pemantauanbencana.source.local.*
import com.bagus.pemantauanbencana.source.remote.DisasterRepository

class DisasterViewModel(private val disasterRepository: DisasterRepository): ViewModel() {
    fun getAllDisaster(): LiveData<List<DisasterEntity>> = disasterRepository.getAllDisaster()
    fun getFilterDisaster(days: String, disaster: ArrayList<String>): LiveData<List<DisasterEntity>> = disasterRepository.getFilterDisaster(days, disaster)
    fun getDisasterDetail(idLogs: String): LiveData<DisasterEntity> = disasterRepository.getDetailDisaster(idLogs)
    fun getUserDetail(email: String, password: String): LiveData<UserEntity> = disasterRepository.getUserData(email, password)
    fun getAllDisasterTypes(): LiveData<List<DisasterTypesEntity>> = disasterRepository.getAllDisasterTypes()
    fun getAllEastJavaRegencies(): LiveData<List<ProvinceEntity>> = disasterRepository.getAllEastJavaRegencies()
    fun updateDisaster( idLogs: Int, dateTime: String, disasterType: Int, regency: Int, area: String, latitude: Float, longitude: Float, weather: String, chronology: String, dead: Int, missing: Int, seriousWound: Int, minorInjuries: Int, damage: String, losses: String, response: String, source: String, status: String, level: String, operatorId: String): LiveData<DisasterUpdateEntity> = disasterRepository.updateDisaster(idLogs, dateTime, disasterType, regency, area, latitude, longitude, weather, chronology, dead, missing, seriousWound, minorInjuries, damage, losses, response, source, status, level, operatorId)
}
