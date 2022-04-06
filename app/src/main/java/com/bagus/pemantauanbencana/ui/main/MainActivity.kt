package com.bagus.pemantauanbencana.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.databinding.ActivityMainBinding
import com.bagus.pemantauanbencana.databinding.FragmentDisasterMapBinding
import com.bagus.pemantauanbencana.ui.disastermap.DisasterMapFragment
import com.bagus.pemantauanbencana.ui.filter.DisasterFilterActivity
import com.bagus.pemantauanbencana.ui.useraccount.SettingPreferences
import com.bagus.pemantauanbencana.ui.useraccount.UserAccountActivity
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModel
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import org.osmdroid.views.MapView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "email")

    companion object {
        const val FILTER_DISASTER = "FILTER_DISASTER"
        var list: ArrayList<String> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Firebase.messaging.subscribeToTopic("disaster")
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_disaster_map, R.id.navigation_disaster_list
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val bundle = Bundle()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = DisasterMapFragment()

        val extras = intent.extras

        if (extras != null) {
            list.clear()
            if (intent.getSerializableExtra(FILTER_DISASTER) != null) {
                list = intent.getSerializableExtra(FILTER_DISASTER) as ArrayList<String>

                bundle.putStringArrayList(FILTER_DISASTER, list)

                fragment.arguments = bundle
                fragmentTransaction.remove(fragment)
                fragmentTransaction.replace(R.id.frame_container, fragment).commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.activity_account -> {
                val intent = Intent(this, UserAccountActivity::class.java)
                startActivity(intent)
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    var clickTwice = false
    override fun onBackPressed() {
        if (clickTwice == true) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
            System.exit(0)
        }

        Handler().postDelayed({
                Toast.makeText(this, "Tekan tombol KEMBALI untuk keluar", Toast.LENGTH_SHORT)
                    .show()
                Handler().postDelayed({ clickTwice = false }, 2000)
                clickTwice = true
        }, 1)
    }

}