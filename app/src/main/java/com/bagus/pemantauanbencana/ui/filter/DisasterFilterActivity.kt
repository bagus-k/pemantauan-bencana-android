package com.bagus.pemantauanbencana.ui.filter

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation.findNavController
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.databinding.ActivityDisasterFilterBinding
import com.bagus.pemantauanbencana.ui.main.MainActivity
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider

class DisasterFilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisasterFilterBinding

    companion object {
        const val FILTER_DISASTER_LIST = "FILTER_DISASTER_LIST"
        const val FILTER_DISASTER_DAYS = "FILTER_DISASTER_DAYS"
        val list: ArrayList<String> = ArrayList()
        var filter: ArrayList<String> = ArrayList()
        var filter_days: Int = 30
        var filterDays: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisasterFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Filter Bencana"

        list.clear()
        val extras = intent.extras
        if (extras != null) {
            filter =
                intent.getSerializableExtra(FILTER_DISASTER_LIST) as ArrayList<String>
            filterDays =
                intent.getSerializableExtra(FILTER_DISASTER_DAYS) as String
            binding.sliderDaysValue.text = "Tampilkan data selama ${filterDays} hari terakhir"
            binding.sliderDays.value = filterDays.toFloat()
            if (filter[0] == "0") {
                setCheckbox(false)
            } else {
                if (filter.size >= 10) {
                    setCheckbox(true)
                } else {
                    for (item in filter) {
                        when (item) {
                            "TORNADO" -> binding.cbTornado.isChecked = true
                            "FLOOD" -> binding.cbFlood.isChecked = true
                            "LANDSLIDE" -> binding.cbLandslide.isChecked = true
                            "EARTHQUAKE" -> binding.cbEarthQuake.isChecked = true
                            "HIGHSURF" -> binding.cbHighSurf.isChecked = true
                            "DROUGHT" -> binding.cbDrought.isChecked = true
                            "WILDFIRE" -> binding.cbWildFire.isChecked = true
                            "INCIDENT" -> binding.cbIncident.isChecked = true
                            "HIGHWIND" -> binding.cbHighWind.isChecked = true
                            "VOLCANO" -> binding.cbVolcano.isChecked = true
                        }
                    }
                }
            }
        } else {
            setCheckbox(false)
        }

        binding.sliderDays.addOnChangeListener { slider, value, fromUser ->
            val mValue = String.format("%.0f", value)
            binding.sliderDaysValue.text = "Tampilkan data selama ${mValue} hari terakhir"
//            filter_days = mValue.toInt()
        }

        binding.cbSelectAll.setOnClickListener {
            if (binding.cbSelectAll.isChecked) {
                setCheckbox(true)
            } else {
                setCheckbox(false)
            }
        }

        binding.btnFilter.setOnClickListener{

            if (binding.cbTornado.isChecked) {
                list.add("TORNADO")
            }
            if (binding.cbFlood.isChecked) {
                list.add("FLOOD")
            }
            if (binding.cbLandslide.isChecked) {
                list.add("LANDSLIDE")
            }
            if (binding.cbEarthQuake.isChecked) {
                list.add("EARTHQUAKE")
            }
            if (binding.cbHighSurf.isChecked) {
                list.add("HIGHSURF")
            }
            if (binding.cbDrought.isChecked) {
                list.add("DROUGHT")
            }
            if (binding.cbWildFire.isChecked) {
                list.add("WILDFIRE")
            }
            if (binding.cbIncident.isChecked) {
                list.add("INCIDENT")
            }
            if (binding.cbHighWind.isChecked) {
                list.add("HIGHWIND")
            }
            if (binding.cbVolcano.isChecked) {
                list.add("VOLCANO")
            }


            val intent = Intent(this, MainActivity::class.java)
            if (list.isNotEmpty()) {
                intent.putExtra(FILTER_DISASTER_LIST, list)
//                intent
//                MainActivity
            }
//            else {
//                list.add("0")
//                intent.putExtra(FILTER_DISASTER_LIST, list)
//            }


            intent.putExtra(FILTER_DISASTER_DAYS, filter_days.toString())
            startActivity(intent)
//            val navController = findNavController(
//                binding.btnFilter.context as MainActivity,
//                R.id.nav_host_fragment_activity_main
//            )
//
//            navController.navigate(R.id.navigation_disaster_map)

//            val navController = findNavController(
//                binding.btnFilter.context as MainActivity,
//                R.id.nav_host_fragment_activity_main
//            )
//            navController.popBackStack()
//            navController.navigate(R.id.navigation_disaster_map)
        }
    }

    private fun setCheckbox(boolean: Boolean) {
        binding.cbSelectAll.isChecked = boolean
        binding.cbTornado.isChecked = boolean
        binding.cbFlood.isChecked = boolean
        binding.cbLandslide.isChecked = boolean
        binding.cbEarthQuake.isChecked = boolean
        binding.cbHighSurf.isChecked = boolean
        binding.cbDrought.isChecked = boolean
        binding.cbWildFire.isChecked = boolean
        binding.cbIncident.isChecked = boolean
        binding.cbHighWind.isChecked = boolean
        binding.cbVolcano.isChecked = boolean
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }
}