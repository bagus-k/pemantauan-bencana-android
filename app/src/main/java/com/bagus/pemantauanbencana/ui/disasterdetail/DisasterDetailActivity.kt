package com.bagus.pemantauanbencana.ui.disasterdetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.databinding.ActivityDisasterDetailBinding
import com.bagus.pemantauanbencana.ui.useraccount.SettingPreferences
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModel
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModelFactory
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModel
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModelFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class DisasterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisasterDetailBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "email")

    companion object {
        const val EXTRA_DISASTER = "EXTRA_DISASTER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisasterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = DisasterViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DisasterViewModel::class.java]
        val extras = intent.extras
        val idLogs = extras?.getString(EXTRA_DISASTER)
        if (idLogs != null) {
            Log.e("DETAIL", idLogs)
        }
        val pref = SettingPreferences.getInstance(dataStore)
        val prefViewModel = ViewModelProvider(this, PreferencesViewModelFactory(pref)).get(
            PreferencesViewModel::class.java
        )

        viewModel.getDisasterDetail(idLogs!!).observe(this) { item ->
//            for (item in disaster) {
//                if (item.idLogs == idLogs) {

                    supportActionBar?.title = item.disastertype

                    if (item.typeid == "DROUGHT") {
                        binding.disasterLogo.setImageResource(R.drawable.drought_high)
                    } else if (item.typeid == "EARTHQUAKE") {
                        binding.disasterLogo.setImageResource(R.drawable.earthquake_high)
                    } else if (item.typeid == "FLOOD") {
                        binding.disasterLogo.setImageResource(R.drawable.flood_high)
                    } else if (item.typeid == "HIGHSURF") {
                        binding.disasterLogo.setImageResource(R.drawable.highsurf_high)
                    } else if (item.typeid == "HIGHWIND") {
                        binding.disasterLogo.setImageResource(R.drawable.highwind_high)
                    } else if (item.typeid == "INCIDENT") {
                        binding.disasterLogo.setImageResource(R.drawable.incident_high)
                    } else if (item.typeid == "LANDSLIDE") {
                        binding.disasterLogo.setImageResource(R.drawable.landslide_high)
                    } else if (item.typeid == "TORNADO") {
                        binding.disasterLogo.setImageResource(R.drawable.tornado_high)
                    } else if (item.typeid == "VOLCANO") {
                        binding.disasterLogo.setImageResource(R.drawable.volcano_high)
                    } else if (item.typeid == "WILDFIRE") {
                        binding.disasterLogo.setImageResource(R.drawable.wildfire_high)
                    }

                    binding.tvDisasterName.text = item.disastertype
                    binding.tvDisasterEventDate.text = item.eventdate
                    binding.tvDisasterProvince.text = item.regencyCity

                    if (item.status == "BELUM") {
                        binding.textContentStatus.background = ContextCompat.getDrawable(
                            this,
                            R.drawable.rounded_corner_red
                        )
                    } else if (item.status == "SELESAI") {
                        binding.textContentStatus.background = ContextCompat.getDrawable(
                            this,
                            R.drawable.rounded_corner_green
                        )
                    }
                    binding.textContentStatus.text = item.status

                    if (item.level == "RENDAH") {
                        binding.textContentLevel.background = ContextCompat.getDrawable(
                            this,
                            R.drawable.rounded_corner_green
                        )
                    } else if (item.level == "SEDANG") {
                        binding.textContentLevel.background = ContextCompat.getDrawable(
                            this,
                            R.drawable.rounded_corner_yellow
                        )
                    } else if (item.level == "TINGGI") {
                        binding.textContentLevel.background = ContextCompat.getDrawable(
                            this,
                            R.drawable.rounded_corner_red
                        )
                    }

                    binding.textContentLevel.text = item.level
                    binding.textContentCoordinate.text = "${item.latitude} , ${item.longitude}"
                    binding.textContentWeather.text = item.weather
                    binding.textContentArea.text = item.area
                    binding.tvContentInjuries.text = "${item.minorInjuries} Luka Ringan, ${item.seriousWound} Luka Berat, ${item.missing} Korban Hilang, ${item.dead} Korban Jiwa"
                    binding.tvContentDamage.text = item.damage

                    val chronologyTextReplace = item.chronology?.replace("<br>", "\n")
                    binding.textContentChronology.text = chronologyTextReplace

                    prefViewModel.getName().observe(this) { name: String ->
                        if (name != "NAME") {
                            var responseTextReplace = item.response?.replace("<br>", "\n")
                            responseTextReplace = responseTextReplace?.replace("<div>", "")
                            responseTextReplace = responseTextReplace?.replace("</div>", "")
                            binding.textContentResponse.text = responseTextReplace
                            binding.textContentSource.text = item.source
                            binding.textContentOperator.text = item.operatorId

                            binding.textResponse.visibility = View.VISIBLE
                            binding.textContentResponse.visibility = View.VISIBLE
                            binding.textSource.visibility = View.VISIBLE
                            binding.textContentSource.visibility = View.VISIBLE
                            binding.textOperator.visibility = View.VISIBLE
                            binding.textContentOperator.visibility = View.VISIBLE
                        }
                    }

                    binding.detailShimmer.stopShimmerAnimation()
                    binding.detailShimmer.visibility = View.GONE
                    binding.svDisasterDetail.visibility = View.VISIBLE
                }
//            }
//        }

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

    override fun onResume() {
        super.onResume()
        binding.detailShimmer.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        binding.detailShimmer.stopShimmerAnimation()
    }
}