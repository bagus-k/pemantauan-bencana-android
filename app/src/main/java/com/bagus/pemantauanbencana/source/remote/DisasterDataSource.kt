package com.bagus.pemantauanbencana.source.remote

import androidx.lifecycle.LiveData
import com.bagus.pemantauanbencana.source.local.DisasterEntity
import com.bagus.pemantauanbencana.source.local.UserEntity

interface DisasterDataSource {
    fun getAllDisaster(): LiveData<List<DisasterEntity>>
    fun getUserData(email: String, password: String): LiveData<UserEntity>
    fun getFilterDisaster(days: String, disaster: ArrayList<String>): LiveData<List<DisasterEntity>>
    fun getDetailDisaster(idLogs: String): LiveData<DisasterEntity>
}