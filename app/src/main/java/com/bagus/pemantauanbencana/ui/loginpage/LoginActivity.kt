package com.bagus.pemantauanbencana.ui.loginpage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bagus.pemantauanbencana.databinding.ActivityLoginBinding
import com.bagus.pemantauanbencana.source.apiservice.ApiConfig
import com.bagus.pemantauanbencana.source.remote.RemoteDataSource
import com.bagus.pemantauanbencana.source.remote.response.UserDataResponse
import com.bagus.pemantauanbencana.ui.useraccount.SettingPreferences
import com.bagus.pemantauanbencana.ui.useraccount.UserAccountActivity
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModel
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModelFactory
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModel
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "email")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val disasterFactory = DisasterViewModelFactory.getInstance(this)
        val disasterViewModel = ViewModelProvider(this, disasterFactory)[DisasterViewModel::class.java]

        val pref = SettingPreferences.getInstance(dataStore)
        val prefViewModel = ViewModelProvider(this, PreferencesViewModelFactory(pref)).get(
            PreferencesViewModel::class.java
        )

        binding.btnLogin.setOnClickListener {
            val email = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()

            disasterViewModel.getUserDetail(email,password).observe(this) { user ->
                if (user != null) {
                    val userEmail = user.email
                    val userName = "${user.firstName} ${user.lastName}"
                    if (userEmail != null && userName != null) {
                        prefViewModel.saveUser(userEmail, userName)
                        val intent = Intent(this, UserAccountActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}