package com.bagus.pemantauanbencana.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bagus.pemantauanbencana.ui.about.SettingPreferences

class PreferencesViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreferencesViewModel::class.java)) {
            return PreferencesViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}