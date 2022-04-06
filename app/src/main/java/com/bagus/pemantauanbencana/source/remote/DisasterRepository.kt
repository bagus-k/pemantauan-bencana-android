package com.bagus.pemantauanbencana.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bagus.pemantauanbencana.source.local.DisasterEntity
import com.bagus.pemantauanbencana.source.local.UserEntity
import com.bagus.pemantauanbencana.source.remote.response.DisasterResponse
import com.bagus.pemantauanbencana.source.remote.response.UserResponse

class DisasterRepository private constructor(private val remoteDataSource: RemoteDataSource) :
    DisasterDataSource {
    companion object {
        @Volatile
        private var instance: DisasterRepository? = null
        fun getInstance(remoteData: RemoteDataSource): DisasterRepository =
            instance ?: synchronized(this) {
                instance ?: DisasterRepository(remoteData)
//                DisasterRepository(remoteData).apply { instance = this }
            }
    }

    override fun getAllDisaster(): LiveData<List<DisasterEntity>> {
        val disasterResult = MutableLiveData<List<DisasterEntity>>()

        remoteDataSource.getAllDisaster(object : RemoteDataSource.LoadDisasterCallback {
            override fun onDisasterLoaded(disaster: List<DisasterResponse>?) {
                val disasterList = ArrayList<DisasterEntity>()
                if (disaster != null) {
                    for (responses in disaster) {
                        with(responses) {

                            if (latitude != "null" || longitude != "null") {
                                val disaster = DisasterEntity(
                                    area,
                                    damage,
                                    level,
                                    operatorId,
                                    regencyCity,
                                    latitude,
                                    link,
                                    chronology,
                                    dead,
                                    minorInjuries,
                                    source,
                                    losses,
                                    photos,
                                    eventdate,
                                    seriousWound,
                                    idLogs,
                                    province,
                                    disastertype,
                                    response,
                                    weather,
                                    missing,
                                    typeid,
                                    longitude,
                                    status
                                )
                                disasterList.add(disaster)
                            }
                        }
                    }
                    disasterResult.postValue(disasterList)
                }
            }
        })
        return disasterResult
    }

    override fun getUserData(emailUser:String, passwordUser:String): LiveData<UserEntity> {
        val userResult = MutableLiveData<UserEntity>()

        remoteDataSource.getUserData(object : RemoteDataSource.LoadUserCallback {
            override fun onUserLoaded(user: UserResponse?) {
                if (user != null) {
                        with(user) {
                            val detailUser = UserEntity(
                                status = status,
                                message = message,
                                email = email,
                                name = name
                            )
                            userResult.postValue(detailUser)
                        }
                }
            }
        },emailUser, passwordUser)
        return userResult
    }
}