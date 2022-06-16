package com.bagus.pemantauanbencana.ui.loginpage

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bagus.pemantauanbencana.databinding.ActivityLoginBinding
import com.bagus.pemantauanbencana.ui.about.SettingPreferences
import com.bagus.pemantauanbencana.ui.about.AboutActivity
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModel
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModelFactory
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModel
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "email")
    private var checkConnection: Boolean = false

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

            if (binding.edtUsername.text.toString().equals("")) {
                binding.edtUsername.setError("Form tidak boleh kosong")
            }
            if (binding.edtPassword.text.toString().equals("")) {
                binding.edtPassword.setError("Form tidak boleh kosong")
            }
            if (!binding.edtUsername.text.toString().equals("") && !binding.edtPassword.text.toString().equals("")){
                binding.loading.visibility = View.VISIBLE
                binding.btnLogin.visibility = View.GONE

                if (checkForInternet(this)) {
                    disasterViewModel.getUserDetail(email,password).observe(this) { user ->
                        if (user.firstName.equals("") && user.lastName.equals("") && user.email.equals("")){
                            showToast("Username / Password tidak ditemukan")
                            binding.loading.visibility = View.GONE
                            binding.btnLogin.visibility = View.VISIBLE
                        } else {
                            val userEmail = user.email
                            val userName = "${user.firstName} ${user.lastName}"
                            if (userEmail != null && userName != null) {
                                prefViewModel.saveUser(userEmail, userName)
                                val intent = Intent(this, AboutActivity::class.java)
                                startActivity(intent)
                            }
                        }
                        checkConnection = true
                    }
                    Handler().postDelayed({
                        if (!checkConnection){
                            Toast.makeText(this, "Koneksi Error, login gagal", Toast.LENGTH_SHORT).show()
                            binding.loading.visibility = View.GONE
                            binding.btnLogin.visibility = View.VISIBLE
                        }
                    }, 10000)
                } else {
                    Toast.makeText(this, "Tidak ada koneksi internet, login gagal", Toast.LENGTH_SHORT).show()
                    binding.loading.visibility = View.GONE
                    binding.btnLogin.visibility = View.VISIBLE
                }

            }
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message ,Toast.LENGTH_SHORT).show()
    }


}