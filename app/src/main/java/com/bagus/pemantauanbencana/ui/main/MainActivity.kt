package com.bagus.pemantauanbencana.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.databinding.ActivityMainBinding
import com.bagus.pemantauanbencana.ui.disastermap.DisasterMapFragment
import com.bagus.pemantauanbencana.ui.useraccount.UserAccountActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "email")

    companion object {
        const val FILTER_DISASTER_LIST = "FILTER_DISASTER_LIST"
        const val FILTER_DISASTER_DAYS = "FILTER_DISASTER_DAYS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

//        if (savedInstanceState == null) {
            val navView: BottomNavigationView = binding.navView

            val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val extras = intent.extras

        if (extras != null) {
//            list.clear()
            if (intent.getSerializableExtra(FILTER_DISASTER_LIST) != null) {
                val list: ArrayList<String> = intent.getSerializableExtra(FILTER_DISASTER_LIST) as ArrayList<String>
//                list = intent.getSerializableExtra(FILTER_DISASTER_LIST) as ArrayList<String>
                val days = intent.getSerializableExtra(FILTER_DISASTER_DAYS) as String

                if (savedInstanceState == null) {
                    val bundle = Bundle()
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    val fragment = DisasterMapFragment()

                    bundle.putStringArrayList(FILTER_DISASTER_LIST, list)
                    bundle.putString(FILTER_DISASTER_DAYS, days)

                    navController.navigate(R.id.navigation_disaster_map, bundle)

//                    fragment.arguments = bundle
//                    fragmentTransaction.remove(fragment)
//                    fragmentTransaction.replace(R.id.frame_container, fragment).commit()
                }
            }
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_disaster_map, R.id.navigation_disaster_list
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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