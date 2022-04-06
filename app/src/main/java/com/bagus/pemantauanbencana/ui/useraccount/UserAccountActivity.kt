package com.bagus.pemantauanbencana.ui.useraccount

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bagus.pemantauanbencana.BuildConfig
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.databinding.ActivityUserAccountBinding
import com.bagus.pemantauanbencana.ui.loginpage.LoginActivity
import com.bagus.pemantauanbencana.ui.main.MainActivity
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModel
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.MainScope

class UserAccountActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUserAccountBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "email")

    private lateinit var expandableListView: ExpandableListView
    private lateinit var expandableListViewAdapter: ExpandableListViewAdapter
    private lateinit var groupList: ArrayList<String>
    private lateinit var childList: HashMap<String, ArrayList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        showListViewAdapter()

        expandableListViewAdapter = ExpandableListViewAdapter(this, groupList, childList)
        binding.eListView.setAdapter(expandableListViewAdapter)

        val pref = SettingPreferences.getInstance(dataStore)
        val prefViewModel = ViewModelProvider(this, PreferencesViewModelFactory(pref)).get(
            PreferencesViewModel::class.java
        )

        prefViewModel.getName().observe(this, {
            name: String ->
            if (name != "NAME") {
                binding.tvUserName.text = name
                binding.btnLogin.visibility = View.GONE
                binding.btnLogout.visibility = View.VISIBLE
            }
        })


        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            prefViewModel.saveUser("EMAIL", "NAME")
            val intent = Intent(this, UserAccountActivity::class.java)
            startActivity(intent)
        }

        binding.tvExit.setOnClickListener {
            exitEvent()
        }

    }

    private fun showListViewAdapter() {

        groupList = ArrayList()
        childList = HashMap()

        groupList.add("Definisi Aplikasi")
        groupList.add("Cara Kerja Aplikasi")
        groupList.add("Sumber Data")
        groupList.add("Versi Aplikasi")

        val topicDefinition: ArrayList<String> = ArrayList()
        topicDefinition.add("Aplikasi pemantauan bencana adalah aplikasi yang digunakan untuk memantau dan menerima notifikasi bencana yang ada di wilayah provinsi Jawa Timur.")

        val topicWorks: ArrayList<String> = ArrayList()
        topicWorks.add("Pada saat pengguna pertama kali menggunakan aplikasi, sistem akan menampilkan icon bencana yang ada di wilayah Jawa Timur.")
        topicWorks.add("Icon bencana yang tampil dapat di filter menggunakan tombol filter yang ada pada peta.")
        topicWorks.add("Pengguna dapat melihat daftar bencana pada halaman bencana")
        topicWorks.add("Khusus pengguna Pusat Pengendalian Operasi Bencana atau selanjutnya disingkat Pusdalops PB, dapat login di halaman login untuk mendapatkan informasi bencana secara keseluruhan")
        topicWorks.add("Aplikasi hanya akan memberikan notifikasi jika terjadi bencana di wiliyah Provinsi Jawa Timur")

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
        builder.setTitle("Keluar ?")
        builder.setMessage("Apakah anda Yakin ingin Keluar")
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