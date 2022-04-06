package com.bagus.pemantauanbencana.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bagus.pemantauanbencana.ui.useraccount.SettingPreferences
import kotlinx.coroutines.launch

class PreferencesViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getEmail(): LiveData<String> {
        return pref.getEmail().asLiveData()
    }
    fun getName(): LiveData<String> {
        return pref.getName().asLiveData()
    }

    fun saveUser(username: String, name: String) {
        viewModelScope.launch {
            pref.setUser(username,name)
        }
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}