package com.bagus.pemantauanbencana.ui.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.ExpandableListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bagus.pemantauanbencana.BuildConfig
import com.bagus.pemantauanbencana.databinding.ActivityAboutBinding
import com.bagus.pemantauanbencana.ui.loginpage.LoginActivity
import com.bagus.pemantauanbencana.ui.main.MainActivity
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModel
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModelFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "email")

    private lateinit var expandableListView: ExpandableListView
    private lateinit var expandableListViewAdapter: ExpandableListViewAdapter
    private lateinit var groupList: ArrayList<String>
    private lateinit var childList: HashMap<String, ArrayList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val pref = SettingPreferences.getInstance(dataStore)
        val prefViewModel = ViewModelProvider(this, PreferencesViewModelFactory(pref)).get(
            PreferencesViewModel::class.java
        )
        showListViewAdapter()
        expandableListViewAdapter = ExpandableListViewAdapter(this, groupList, childList)
        binding.eListView.setAdapter(expandableListViewAdapter)

        prefViewModel.getName().observe(this) { name: String ->
            if (name != "NAME") {
                binding.tvUserName.text = name
                binding.btnLogin.visibility = View.GONE
                binding.btnLogout.visibility = View.VISIBLE
            }
        }

        prefViewModel.getEmail().observe(this) { email: String ->
            if (email != "EMAIL") {
                binding.tvUserEmail.text = email
            }
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            logoutEvent()
        }

        binding.tvExit.setOnClickListener {
            exitEvent()
        }

        prefViewModel.getNotificationSetting().observe(this
        ) { notif: Boolean ->
            if (notif) {
                Firebase.messaging.subscribeToTopic("disaster")
                binding.switchNotification.isChecked = true
            } else {
                Firebase.messaging.unsubscribeFromTopic("disaster")
                binding.switchNotification.isChecked = false
            }
        }

        binding.switchNotification.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            prefViewModel.saveNotificationSetting(isChecked)
        }
    }

    private fun logoutEvent() {
        val builder = AlertDialog.Builder(this)
        val pref = SettingPreferences.getInstance(dataStore)
        val prefViewModel = ViewModelProvider(this, PreferencesViewModelFactory(pref)).get(
            PreferencesViewModel::class.java
        )
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah anda yakin ingin Logout ?")
        builder.setPositiveButton("Ya") { dialog, id ->
            prefViewModel.saveUser("EMAIL", "NAME")
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("Tidak") { dialog, id ->
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun showListViewAdapter() {
        val pref = SettingPreferences.getInstance(dataStore)
        val prefViewModel = ViewModelProvider(this, PreferencesViewModelFactory(pref)).get(
            PreferencesViewModel::class.java
        )

        groupList = ArrayList()
        childList = HashMap()

        groupList.add("Definisi Aplikasi")
        groupList.add("Cara Kerja Aplikasi")
        groupList.add("Sumber Data")
        groupList.add("Versi Aplikasi")

        val topicDefinition: ArrayList<String> = ArrayList()
        topicDefinition.add("SIPENA (Sistem Pemantauan Bencana) adalah aplikasi yang digunakan untuk memantau dan menerima notifikasi bencana yang ada di wilayah provinsi Jawa Timur.")

        val topicWorks: ArrayList<String> = ArrayList()
        topicWorks.add("Pada saat pengguna pertama kali menggunakan aplikasi, sistem akan menampilkan icon bencana yang ada di wilayah Jawa Timur.")
        topicWorks.add("Icon bencana yang tampil dapat di filter menggunakan tombol filter yang ada pada peta.")
        topicWorks.add("Pengguna dapat melihat daftar bencana pada halaman bencana")
        topicWorks.add("Aplikasi hanya akan memberikan notifikasi jika terjadi bencana di wiliyah Provinsi Jawa Timur")
        topicWorks.add("Fitur notifikasi dapat diaktifkan dan dinonaktifkan melalui preferensi notifikasi bencana")
        topicWorks.add("Khusus pengguna Pusat Pengendalian Operasi Bencana atau selanjutnya disingkat Pusdalops PB, dapat login di halaman login untuk mendapatkan informasi bencana secara keseluruhan")
        prefViewModel.getName().observe(this) { name: String ->
            if (name != "NAME") {
                topicWorks.add("Khusus pengguna Pusdalops PB, terdapat fitur edit bencana yang dapat diakses pada menu detail bencana")
            }
        }

        val topicSource: ArrayList<String> = ArrayList()
        topicSource.add("Sumber data yang terdapat pada aplikasi ini berasal dari Badan Penanggulangan Bencana Daerah (BPBD) Provinsi Jawa Timur")

        val topicVersion: ArrayList<String> = ArrayList()
        topicVersion.add("Saat ini versi yang digunakan yaitu ${BuildConfig.VERSION_NAME}")

        childList[groupList[0]] = topicDefinition
        childList[groupList[1]] = topicWorks
        childList[groupList[2]] = topicSource
        childList[groupList[3]] = topicVersion
    }

    private fun exitEvent() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Keluar")
        builder.setMessage("Apakah anda Yakin ingin Keluar ?")
        builder.setPositiveButton("Ya") { dialog, id ->
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
            System.exit(0)
        }
        builder.setNegativeButton("Tidak") { dialog, id ->
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}