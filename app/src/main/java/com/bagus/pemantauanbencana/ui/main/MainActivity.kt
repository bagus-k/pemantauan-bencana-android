package com.bagus.pemantauanbencana.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.*
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
import com.bagus.pemantauanbencana.ui.about.AboutActivity
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

        //Custom action bar
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        val layoutInflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.custom_action_bar, null)
        supportActionBar?.setCustomView(view)


//        if (savedInstanceState == null) {
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_disaster_map, R.id.navigation_disaster_list
            )
        )
        val extras = intent.extras

        if (extras != null) {
//            list.clear()
            if (intent.getSerializableExtra(FILTER_DISASTER_LIST) != null) {
                val list: ArrayList<String> = intent.getSerializableExtra(FILTER_DISASTER_LIST) as ArrayList<String>
//                list = intent.getSerializableExtra(FILTER_DISASTER_LIST) as ArrayList<String>
                val days = intent.getSerializableExtra(FILTER_DISASTER_DAYS) as String

//                if (savedInstanceState == null) {
                    val bundle = Bundle()
                    val fragmentManager = supportFragmentManager
//                    val fragmentTransaction = fragmentManager.beginTransaction()
//                    val fragment = DisasterMapFragment()

                    bundle.putStringArrayList(FILTER_DISASTER_LIST, list)
                    bundle.putString(FILTER_DISASTER_DAYS, days)

                    navController.navigate(R.id.navigation_disaster_map, bundle)

//                    fragment.arguments = bundle
//                    fragmentTransaction.remove(fragment)
//                    fragmentTransaction.replace(R.id.frame_container, fragment).commit()
//                }
            }
        }
        else {
            val disasterName: Array<String> = arrayOf(
                "DROUGHT",
                "EARTHQUAKE",
                "FLOOD",
                "HIGHSURF",
                "HIGHWIND",
                "INCIDENT",
                "LANDSLIDE",
                "TORNADO",
                "VOLCANO",
                "WILDFIRE",
                "TSUNAMI"
            )
//            val list: ArrayList<DisasterTypeEntity> = ArrayList()
            val list: ArrayList<String> = ArrayList()

            for (item in disasterName) {
//                list.add(DisasterTypeEntity(item))
                list.add(item)
            }
            val bundle = Bundle()
//            bundle.putParcelableArrayList(FILTER_DISASTER_LIST,list)
            bundle.putStringArrayList(FILTER_DISASTER_LIST, list)
            bundle.putString(FILTER_DISASTER_DAYS, "3")

            navController.navigate(R.id.navigation_disaster_map, bundle)
        }
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
                val intent = Intent(this, AboutActivity::class.java)
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

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

//        supportFragmentManager.putFragment(outState)
    }
}