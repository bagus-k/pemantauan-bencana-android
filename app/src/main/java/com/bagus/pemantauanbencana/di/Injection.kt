package com.bagus.pemantauanbencana.di

import android.content.Context
import com.bagus.pemantauanbencana.source.remote.DisasterRepository
import com.bagus.pemantauanbencana.source.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): DisasterRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
//        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        return DisasterRepository.getInstance(remoteDataSource)
    }
}