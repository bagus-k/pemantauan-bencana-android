package com.bagus.pemantauanbencana.ui.filter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.bagus.pemantauanbencana.databinding.ActivityDisasterFilterBinding
import com.bagus.pemantauanbencana.ui.main.MainActivity
import java.util.ArrayList

class DisasterFilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisasterFilterBinding

    companion object {
        const val FILTER_DISASTER = "FILTER_DISASTER"
        val list: ArrayList<String> = ArrayList()
        var filter: ArrayList<String> = ArrayList()
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
                intent.getSerializableExtra(FILTER_DISASTER) as ArrayList<String>
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
                intent.putExtra(FILTER_DISASTER, list)
            }
            else {
                list.add("0")
                intent.putExtra(FILTER_DISASTER, list)
            }
            startActivity(intent)
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