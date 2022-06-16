package com.bagus.pemantauanbencana.ui.disasterdetail

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.databinding.ActivityDisasterDetailBinding
import com.bagus.pemantauanbencana.ui.editdisaster.EditDisasterActivity
import com.bagus.pemantauanbencana.ui.about.SettingPreferences
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModel
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModelFactory
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModel
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModelFactory
import com.bumptech.glide.Glide

class DisasterDetailActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityDisasterDetailBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "email")
    private var checkListDisaster: Boolean = false


    companion object {
        const val EXTRA_DISASTER = "EXTRA_DISASTER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisasterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (checkForInternet(this)) {
            init()
            Handler().postDelayed({
                if (!checkListDisaster) {
                    Toast.makeText(this, "Koneksi Error, gulir ke bawah untuk reload", Toast.LENGTH_SHORT).show()
                }
            }, 10000)
        } else {
            Toast.makeText(this, "Tidak ada koneksi internet, gulir ke bawah untuk reload", Toast.LENGTH_SHORT).show()
        }
        binding.swiperefresh.setOnRefreshListener(this)
    }

    private fun init() {
        binding.btnEdit.visibility = View.GONE
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
            if (item != null) {
                checkListDisaster = true
                supportActionBar?.title = item.disastertype

                when (item.typeid) {
                    "DROUGHT" -> {
                        binding.disasterLogo.setImageResource(R.drawable.drought_high)
                    }
                    "EARTHQUAKE" -> {
                        binding.disasterLogo.setImageResource(R.drawable.earthquake_high)
                    }
                    "FLOOD" -> {
                        binding.disasterLogo.setImageResource(R.drawable.flood_high)
                    }
                    "HIGHSURF" -> {
                        binding.disasterLogo.setImageResource(R.drawable.highsurf_high)
                    }
                    "HIGHWIND" -> {
                        binding.disasterLogo.setImageResource(R.drawable.highwind_high)
                    }
                    "INCIDENT" -> {
                        binding.disasterLogo.setImageResource(R.drawable.incident_high)
                    }
                    "LANDSLIDE" -> {
                        binding.disasterLogo.setImageResource(R.drawable.landslide_high)
                    }
                    "TORNADO" -> {
                        binding.disasterLogo.setImageResource(R.drawable.tornado_high)
                    }
                    "VOLCANO" -> {
                        binding.disasterLogo.setImageResource(R.drawable.volcano_high)
                    }
                    "WILDFIRE" -> {
                        binding.disasterLogo.setImageResource(R.drawable.wildfire_high)
                    }
                    "TSUNAMI" -> {
                        binding.disasterLogo.setImageResource(R.drawable.highsurf_high)
                    }
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
                binding.tvContentInjuries.text =
                    "${item.minorInjuries} Luka Ringan \n${item.seriousWound} Luka Berat \n${item.missing} Korban Hilang \n${item.dead} Korban Jiwa"
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
                        binding.textContentIdlogs.text = item.idLogs

                        binding.textResponse.visibility = View.VISIBLE
                        binding.textContentResponse.visibility = View.VISIBLE
                        binding.textSource.visibility = View.VISIBLE
                        binding.textContentSource.visibility = View.VISIBLE
                        binding.textOperator.visibility = View.VISIBLE
                        binding.textContentOperator.visibility = View.VISIBLE
                        binding.textIdlogs.visibility = View.VISIBLE
                        binding.textContentIdlogs.visibility = View.VISIBLE
                       // binding.btnEdit.visibility = View.VISIBLE

                    }
                }

                if (!item.photos.equals("")) {
                    val splitPhotos = item.photos.split("\"")
                    val splitPhoto = splitPhotos.get(3).split("/")
                    Glide.with(this)
                        .load("https://smartpb.bpbd.jatimprov.go.id/files/" + splitPhoto.get(1).replace(" ",
                            "%20"
                        ))
                        .into(binding.imgDisasterPhoto)
                    binding.imgDisasterPhoto.visibility = View.VISIBLE
                }

                binding.detailShimmer.stopShimmerAnimation()
                binding.detailShimmer.visibility = View.GONE
                binding.svDisasterDetail.visibility = View.VISIBLE

                binding.btnEdit.setOnClickListener(View.OnClickListener {
                    val intent = Intent(this, EditDisasterActivity::class.java)
                    intent.putExtra(EXTRA_DISASTER, idLogs)
                    startActivity(intent)
                })
            }
        }
    }

    override fun onRefresh() {
        binding.detailShimmer.startShimmerAnimation()
        binding.detailShimmer.visibility = View.VISIBLE
        binding.svDisasterDetail.visibility = View.GONE
        init()
        binding.swiperefresh.isRefreshing = false
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