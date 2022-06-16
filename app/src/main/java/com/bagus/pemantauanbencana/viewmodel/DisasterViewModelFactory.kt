package com.bagus.pemantauanbencana.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bagus.pemantauanbencana.di.Injection
import com.bagus.pemantauanbencana.source.remote.DisasterRepository

class DisasterViewModelFactory private constructor(private val disasterRepository: DisasterRepository) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: DisasterViewModelFactory? = null

        fun getInstance(context: Context): DisasterViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DisasterViewModelFactory(Injection.provideRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DisasterViewModel::class.java) -> {
                DisasterViewModel(disasterRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}