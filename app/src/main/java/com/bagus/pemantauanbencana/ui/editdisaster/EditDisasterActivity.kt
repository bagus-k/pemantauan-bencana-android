package com.bagus.pemantauanbencana.ui.editdisaster

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.databinding.ActivityEditDisasterBinding
import com.bagus.pemantauanbencana.ui.about.AboutActivity
import com.bagus.pemantauanbencana.ui.about.SettingPreferences
import com.bagus.pemantauanbencana.ui.disasterdetail.DisasterDetailActivity
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModel
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModelFactory
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModel
import com.bagus.pemantauanbencana.viewmodel.PreferencesViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class EditDisasterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDisasterBinding
    private var idListDisasterTypes = ArrayList<Int>()
    private var listDisasterTypes = ArrayList<String>()
    private var idListRegencies = ArrayList<Int>()
    private var listRegencies = ArrayList<String>()
    private var checkUpdateData: Boolean = false

    companion object {
        const val EXTRA_DISASTER = "EXTRA_DISASTER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDisasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Ubah Data Bencana"

        binding.svEditDisasterDetail.visibility = View.GONE

        val factory = DisasterViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DisasterViewModel::class.java]

        val extras = intent.extras
        val idLogs = extras?.getString(EXTRA_DISASTER)

        showEditItem(viewModel, idLogs.toString())

        binding.edtTime.inputType = InputType.TYPE_NULL
        binding.edtTime.setOnClickListener { showDateTimeDialog(binding.edtTime) }

        binding.btnUpdate.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Konfirmasi Ubah Data bencana")
            builder.setMessage("Apakah anda yakin ingin mengubah data bencana ?")
            builder.setPositiveButton("Ya") { dialog, id ->
                if (checkForInternet(this)) {
                    updatedata(viewModel)
                    Handler().postDelayed({
                        if (!checkUpdateData) {
                            Toast.makeText(this, "Koneksi Error, Ubah data bencana gagal", Toast.LENGTH_SHORT).show()
                        }
                    }, 10000)
                } else {
                    Toast.makeText(this, "Tidak ada koneksi internet, Ubah data bencana gagal", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("Tidak") { dialog, id ->
                dialog.cancel()
            }
            val alert = builder.create()
            alert.show()
        }
    }

    private fun showDateTimeDialog(edtTime: TextInputEditText) {
        val calendar = Calendar.getInstance()
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = day
                val timeSetListener =
                    OnTimeSetListener { timePicker, hour, minute ->
                        calendar[Calendar.HOUR_OF_DAY] = hour
                        calendar[Calendar.MINUTE] = minute
                        val simpleDateFormat = SimpleDateFormat("yy-MM-dd HH:mm")
                        edtTime.setText(simpleDateFormat.format(calendar.time) + ":00")
                    }
                TimePickerDialog(
                    this, timeSetListener,
                    calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], false
                ).show()
            }
        DatePickerDialog(
            this, dateSetListener,
            calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]
        ).show()
    }

    private fun updatedata(viewModel: DisasterViewModel) {
        val idLogs = binding.tvIdLogsValue.text.toString().toInt()
        val eventDate = binding.edtTime.text.toString()
        val disasterType = idListDisasterTypes.get(listDisasterTypes.indexOf(binding.edtTextDisasterType.text.toString()))
        val regency = idListRegencies.get(listRegencies.indexOf(binding.edtTextRegency.text.toString()))
        val area = binding.edtArea.text.toString()
        var latitude: Float = 0F
        if (binding.edtLatitude.text.toString().isNotEmpty()) {
            latitude =  binding.edtLatitude.text.toString().toFloat()
        }
        var longitude: Float = 0F
        if (binding.edtLongitude.text.toString().isNotEmpty()) {
            longitude = binding.edtLongitude.text.toString().toFloat()
        }
        val weather = binding.edtWeather.text.toString()
        val chronology = binding.edtChronology.text.toString()
        var dead: Int = 0
        if (binding.edtDead.text.toString().isNotEmpty()){
            dead = binding.edtDead.text.toString().toInt()
        }
        var missing: Int = 0
        if (binding.edtMissing.text.toString().isNotEmpty()){
            missing = binding.edtMissing.text.toString().toInt()
        }
        var seriousWound: Int = 0
        if (binding.edtSeriousWound.text.toString().isNotEmpty()){
            seriousWound = binding.edtSeriousWound.text.toString().toInt()
        }
        var minorInjuries: Int = 0
        if (binding.edtMinorInjuries.text.toString().isNotEmpty()){
            minorInjuries = binding.edtMinorInjuries.text.toString().toInt()
        }
        val damage = binding.edtDamage.text.toString()
        val losses = binding.edtLosses.text.toString()
        val response = binding.edtResponse.text.toString()
        val source = binding.edtSource.text.toString()
        val status = binding.edtStatus.text.toString()
        val level = binding.edtLevel.text.toString()
        val operatorId = binding.edtOperatorId.text.toString()

        viewModel.updateDisaster(idLogs, eventDate, disasterType, regency, area,
            latitude, longitude, weather, chronology, dead, missing, seriousWound, minorInjuries, damage, losses, response, source, status, level, operatorId).observe(this) { disaster ->
            checkUpdateData = true
            if (disaster.message == "Update Berhasil") {
                Toast.makeText(this, "Ubah data bencana berhasil", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, DisasterDetailActivity::class.java)
                intent.putExtra(EXTRA_DISASTER, idLogs.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "Data error, Ubah data gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun disasterTypes(viewModel: DisasterViewModel){
        viewModel.getAllDisasterTypes().observe(this) { disaster ->
            disaster.forEach { item ->
                idListDisasterTypes.add(item.idDisaster!!.toInt())
                listDisasterTypes.add(item.disastertype.toString())
            }
            val adapterDisasterTypes= ArrayAdapter<String>( binding.edtTextDisasterType.context, R.layout.material_dropdown_item, listDisasterTypes)
            binding.edtTextDisasterType.setAdapter(adapterDisasterTypes)
        }
    }

    private fun showRegencies(viewModel: DisasterViewModel){
        viewModel.getAllEastJavaRegencies().observe(this) { regencies ->
            regencies.forEach { item ->
                idListRegencies.add(item.idRegency!!.toInt())
                listRegencies.add(item.regencyCity.toString())
            }
            val adapterRegency = ArrayAdapter<String>(this, R.layout.material_dropdown_item, listRegencies)
            binding.edtTextRegency.setAdapter(adapterRegency)
        }
    }

    private fun showEditItem(viewModel: DisasterViewModel, idLogs: String){

        viewModel.getDisasterDetail(idLogs!!).observe(this) { item ->
            if (item != null) {

                binding.tvIdLogsValue.text = item.idLogs
                binding.edtTime.setText(item.eventdate)
                binding.edtTextDisasterType.setText(item.disastertype)
                binding.edtTextRegency.setText(item.regencyCity)
                binding.edtArea.setText(item.area)
                binding.edtLatitude.setText(item.latitude)
                binding.edtLongitude.setText(item.longitude)
                binding.edtWeather.setText(item.weather)
                binding.edtChronology.setText(item.chronology)
                binding.edtDead.setText(item.dead)
                binding.edtLosses.setText(item.losses)
                binding.edtSeriousWound.setText(item.seriousWound)
                binding.edtMinorInjuries.setText(item.minorInjuries)
                binding.edtDamage.setText(item.damage)
                binding.edtLosses.setText(item.losses)
                binding.edtResponse.setText(item.response)
                binding.edtSource.setText(item.source)
                binding.edtStatus.setText(item.status)
                binding.edtLevel.setText(item.level)
                binding.edtOperatorId.setText(item.operatorId)
            }
            disasterTypes(viewModel)
            showRegencies(viewModel)

            val listStatus = resources.getStringArray(R.array.disaster_status)
            val adapterStatus= ArrayAdapter(this, R.layout.material_dropdown_item, listStatus)
            binding.edtStatus.setAdapter(adapterStatus)

            val listLevel = resources.getStringArray(R.array.disaster_level)
            val adapterLevel= ArrayAdapter<String>(this, R.layout.material_dropdown_item, listLevel)
            binding.edtLevel.setAdapter(adapterLevel)
            binding.svEditDisasterDetail.visibility = View.VISIBLE
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