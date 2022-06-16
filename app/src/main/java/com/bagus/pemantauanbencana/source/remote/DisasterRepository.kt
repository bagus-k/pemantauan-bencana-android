package com.bagus.pemantauanbencana.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bagus.pemantauanbencana.source.local.*
import com.bagus.pemantauanbencana.source.remote.response.*

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
                            if (latitude.isNullOrEmpty()|| !longitude.isNullOrEmpty()) {
                                val disaster = DisasterEntity(
                                    area.toString(),
                                    damage.toString(),
                                    level.toString(),
                                    operatorId.toString(),
                                    regencyCity.toString(),
                                    latitude.toString(),
                                    link.toString(),
                                    chronology.toString(),
                                    dead.toString(),
                                    minorInjuries.toString(),
                                    source.toString(),
                                    losses.toString(),
                                    photos.toString(),
                                    eventdate.toString(),
                                    seriousWound.toString(),
                                    idLogs.toString(),
                                    province.toString(),
                                    disastertype.toString(),
                                    response.toString(),
                                    weather.toString(),
                                    missing.toString(),
                                    typeid.toString(),
                                    longitude.toString(),
                                    status.toString()
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

    override fun getUserData(usernameUser:String, passwordUser:String): LiveData<UserEntity> {
        val userResult = MutableLiveData<UserEntity>()

        remoteDataSource.getUserData(object : RemoteDataSource.LoadUserCallback {
            override fun onUserLoaded(user: List<UserResponse>?) {
                if (user != null) {
                    for (response in user) {
                        with(response) {
                            val detailUser = UserEntity(
                               email, firstName, lastName, avatar
                            )
                            userResult.postValue(detailUser)
                        }
                    }
                }
            }
        },usernameUser, passwordUser)
        return userResult
    }

    override fun getFilterDisaster(
        days: String,
        disaster: ArrayList<String>
    ): LiveData<List<DisasterEntity>> {
        val disasterResult = MutableLiveData<List<DisasterEntity>>()

        remoteDataSource.getFilterDisaster(object : RemoteDataSource.LoadDisasterCallback {
            override fun onDisasterLoaded(disaster: List<DisasterResponse>?) {
                val disasterList = ArrayList<DisasterEntity>()
                if (disaster != null) {
                    for (responses in disaster) {
                        with(responses) {

                            if (!latitude.isNullOrEmpty() || !longitude.isNullOrEmpty()) {
                                val disaster = DisasterEntity(
                                    area.toString(),
                                    damage.toString(),
                                    level.toString(),
                                    operatorId.toString(),
                                    regencyCity.toString(),
                                    latitude.toString(),
                                    link.toString(),
                                    chronology.toString(),
                                    dead.toString(),
                                    minorInjuries.toString(),
                                    source.toString(),
                                    losses.toString(),
                                    photos.toString(),
                                    eventdate.toString(),
                                    seriousWound.toString(),
                                    idLogs.toString(),
                                    province.toString(),
                                    disastertype.toString(),
                                    response.toString(),
                                    weather.toString(),
                                    missing.toString(),
                                    typeid.toString(),
                                    longitude.toString(),
                                    status.toString()
                                )
                                disasterList.add(disaster)
                            }
                        }
                    }
                    disasterResult.postValue(disasterList)
                }
            }
        }, days, disaster)
        return disasterResult
    }

    override fun getDetailDisaster(id: String): LiveData<DisasterEntity> {
        val detailResult = MutableLiveData<DisasterEntity>()

        remoteDataSource.getDisasterDetail(object : RemoteDataSource.LoadDisasterCallback {
            override fun onDisasterLoaded(disaster: List<DisasterResponse>?) {
                if (disaster != null) {
                    for (responses in disaster) {
                        with(responses) {
                            val result = DisasterEntity(
                                area.toString(),
                                damage.toString(),
                                level.toString(),
                                operatorId.toString(),
                                regencyCity.toString(),
                                latitude.toString(),
                                link.toString(),
                                chronology.toString(),
                                dead.toString(),
                                minorInjuries.toString(),
                                source.toString(),
                                losses.toString(),
                                photos.toString(),
                                eventdate.toString(),
                                seriousWound.toString(),
                                idLogs.toString(),
                                province.toString(),
                                disastertype.toString(),
                                response.toString(),
                                weather.toString(),
                                missing.toString(),
                                typeid.toString(),
                                longitude.toString(),
                                status.toString()
                            )
                            detailResult.postValue(result)
                        }
                    }
                }
            }
        },id)
        return detailResult
    }

    override fun getAllDisasterTypes(): LiveData<List<DisasterTypesEntity>> {
        val disasterResult = MutableLiveData<List<DisasterTypesEntity>>()

        remoteDataSource.getAllDisasterTypes(object : RemoteDataSource.LoadDisasterTypesCallback {
            override fun onDisasterTypesLoaded(disaster: List<DisasterTypeResponse>?) {
                val disasterList = ArrayList<DisasterTypesEntity>()
                if (disaster != null) {
                    for (responses in disaster) {
                        with(responses) {
                            val disaster = DisasterTypesEntity(
                                idDisaster, disastertype, desc
                            )
                            disasterList.add(disaster)
                        }
                    }
                    disasterResult.postValue(disasterList)
                }
            }
        })
        return disasterResult
    }

    override fun getAllEastJavaRegencies(): LiveData<List<ProvinceEntity>> {
        val provinceResult = MutableLiveData<List<ProvinceEntity>>()

        remoteDataSource.getAllEastJavaRegencies(object : RemoteDataSource.LoadProvinceCallback {
            override fun onProvinceLoaded(province: List<ProvinceResponse>?) {
                val provinceList = ArrayList<ProvinceEntity>()
                if (province != null) {
                    for (responses in province) {
                        with(responses) {
                            val province = ProvinceEntity(
                                bpsCode, regencyCity, latitude, idRegency, longitude
                            )
                            provinceList.add(province)
                        }
                    }
                    provinceResult.postValue(provinceList)
                }
            }
        })
        return provinceResult
    }

    override fun updateDisaster(
        idLogs: Int,
        dateTime: String,
        disasterType: Int,
        regency: Int,
        area: String,
        latitude: Float,
        longitude: Float,
        weather: String,
        chronology: String,
        dead: Int,
        missing: Int,
        seriousWound: Int,
        minorInjuries: Int,
        damage: String,
        losses: String,
        response: String,
        source: String,
        status: String,
        level: String,
        operatorId: String
    ): LiveData<DisasterUpdateEntity> {
        val disasterResult = MutableLiveData<DisasterUpdateEntity>()

        remoteDataSource.updateDisaster(object : RemoteDataSource.LoadUpdateDisasterCallback {
            override fun onUpdateDisasterLoaded(disaster: List<DisasterUpdateResponse>?) {
                if (disaster != null) {
                    for (response in disaster) {
                        with(response) {
                            val disaster = DisasterUpdateEntity(
                                message
                            )
                            disasterResult.postValue(disaster)
                        }
                    }
                }
            }
        },idLogs, dateTime, disasterType, regency, area, latitude, longitude, weather, chronology, dead, missing, seriousWound, minorInjuries, damage, losses, response, source, status, level, operatorId)
        return disasterResult
    }

}