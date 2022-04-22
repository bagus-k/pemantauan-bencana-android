package com.bagus.pemantauanbencana.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bagus.pemantauanbencana.source.local.DisasterEntity
import com.bagus.pemantauanbencana.source.local.UserEntity
import com.bagus.pemantauanbencana.source.remote.DisasterRepository

class DisasterViewModel(private val disasterRepository: DisasterRepository): ViewModel() {
    fun getAllDisaster(): LiveData<List<DisasterEntity>> = disasterRepository.getAllDisaster()
    fun getFilterDisaster(days: String, disaster: ArrayList<String>): LiveData<List<DisasterEntity>> = disasterRepository.getFilterDisaster(days, disaster)
    fun getDisasterDetail(idLogs: String): LiveData<DisasterEntity> = disasterRepository.getDetailDisaster(idLogs)
    fun getUserDetail(email: String, password: String): LiveData<UserEntity> = disasterRepository.getUserData(email, password)
}
