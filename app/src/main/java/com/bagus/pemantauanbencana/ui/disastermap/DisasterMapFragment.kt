package com.bagus.pemantauanbencana.ui.disastermap

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.bagus.pemantauanbencana.BuildConfig
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.databinding.FragmentDisasterMapBinding
import com.bagus.pemantauanbencana.source.local.DisasterEntity
import com.bagus.pemantauanbencana.ui.filter.DisasterFilterActivity
import com.bagus.pemantauanbencana.ui.infowindow.MarkerWindow
import com.bagus.pemantauanbencana.ui.main.MainActivity
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModel
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModelFactory
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class DisasterMapFragment : Fragment() {

    private lateinit var binding: FragmentDisasterMapBinding

    companion object {
        private lateinit var map: MapView
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 1
        const val FILTER_DISASTER_LIST = "FILTER_DISASTER_LIST"
        const val FILTER_DISASTER_DAYS = "FILTER_DISASTER_DAYS"
        var list: ArrayList<String> = ArrayList()
        var days: String = ""
        var checkListDisaster: Boolean = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisasterMapBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            if (checkForInternet(requireActivity())) {
                init()
                Handler().postDelayed({
                    if (!checkListDisaster) {
                        Toast.makeText(requireActivity(), "Koneksi Error, tekan tombol reload", Toast.LENGTH_SHORT).show()
                    }
                }, 10000)

            } else {
                Toast.makeText(requireActivity(), "Tidak ada koneksi internet, tekan tombol reload", Toast.LENGTH_SHORT).show()
            }

            binding.btnFilter.setOnClickListener {
                val intent = Intent(requireActivity(), DisasterFilterActivity::class.java)
                if (list.isNotEmpty()) {
                    intent.putExtra(FILTER_DISASTER_LIST, list)
                    intent.putExtra(FILTER_DISASTER_DAYS, days)
                }
                startActivity(intent)
            }

            binding.btnZoomOut.setOnClickListener {
                map.controller.zoomOut()
            }

            binding.btnZoomIn.setOnClickListener {
                map.controller.zoomIn()
            }

            binding.btnRefresh.setOnClickListener {
                if (checkForInternet(requireActivity())) {
                    init()
                    Handler().postDelayed({
                        if (!checkListDisaster) {
                            Toast.makeText(requireActivity(), "Koneksi Error, tekan tombol reload", Toast.LENGTH_SHORT).show()
                        }
                    }, 10000)

                } else {
                    Toast.makeText(requireActivity(), "Tidak ada koneksi internet, tekan tombol reload", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun init() {
        Configuration.getInstance().load(requireActivity(), PreferenceManager.getDefaultSharedPreferences(requireActivity()));
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID

        map = binding.mapView
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.setBuiltInZoomControls(true)

        val mapController = map.controller
        mapController.setZoom(8)

        val startPoint = GeoPoint(-7.5468636, 111.9801192)
        mapController.setCenter(startPoint)

        val factory = DisasterViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(requireActivity(), factory)[DisasterViewModel::class.java]
        val extras = arguments
        map.overlays.clear()

        if (extras != null) {
            list = extras.getStringArrayList(FILTER_DISASTER_LIST) as ArrayList<String>
            days = extras.getString(FILTER_DISASTER_DAYS) as String
            viewModel.getFilterDisaster(days, list).observe(requireActivity()) { disaster ->
                when {
                    disaster.isNotEmpty() -> {
                        showDisaster(disaster)
                        checkListDisaster = true
                    }
                    disaster.isEmpty() -> {
                        Toast.makeText(context, "Data tidak ditemukan" ,Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, "Koneksi Error" ,Toast.LENGTH_SHORT).show()
                    }
                }
            }
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

    private fun showDisaster(disaster: List<DisasterEntity>) {
        for (item in disaster) {
            if (item.latitude != "null" && item.longitude != "null") {

                val marker = Marker(map)
                val longitude = item.longitude.toDouble()
                val latitude = item.latitude.toDouble()
                if (longitude <= 180.0 && longitude >= -180.0 || latitude <= 180.0 && latitude >= -180.0) {
                    marker.position =
                        GeoPoint(latitude, longitude)
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    when (item.typeid) {
                        "EARTHQUAKE" -> marker.icon =
                            ContextCompat.getDrawable(requireActivity(), R.mipmap.earthquake)
                        "VOLCANO" -> marker.icon =
                            ContextCompat.getDrawable(requireActivity(), R.mipmap.volcano)
                        "TSUNAMI" -> marker.icon =
                            ContextCompat.getDrawable(requireActivity(), R.mipmap.highsurf)
                        "LANDSLIDE" -> marker.icon =
                            ContextCompat.getDrawable(requireActivity(), R.mipmap.landslide)
                        "FLOOD" -> marker.icon =
                            ContextCompat.getDrawable(requireActivity(), R.mipmap.flood)
                        "DROUGHT" -> marker.icon =
                            ContextCompat.getDrawable(requireActivity(), R.mipmap.drought)
                        "TORNADO" -> marker.icon =
                            ContextCompat.getDrawable(requireActivity(), R.mipmap.tornado)
                        "INCIDENT" -> marker.icon =
                            ContextCompat.getDrawable(requireActivity(), R.mipmap.incident)
                        "WILDFIRE" -> marker.icon =
                            ContextCompat.getDrawable(requireActivity(), R.mipmap.wildfire)
                        "HIGHSURF" -> marker.icon =
                            ContextCompat.getDrawable(requireActivity(), R.mipmap.highsurf)
                        "HIGHWIND" -> marker.icon =
                            ContextCompat.getDrawable(requireActivity(), R.mipmap.highwind)
                    }

                    val infoWindow = MarkerWindow(map, item)
                    marker.infoWindow = infoWindow
                    for (i in list) {
                        if (i == item.typeid) {
                            map.overlays.add(marker)
                        }
                        map.invalidate()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val permissionsToRequest = ArrayList<String>();
        var i = 0;
        while (i < grantResults.size) {
            permissionsToRequest.add(permissions[i]);
            i++;
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE
            );
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(FILTER_DISASTER_DAYS,days)
        outState.putStringArrayList(FILTER_DISASTER_LIST, list)
    }

//    override fun onResume() {
//        super.onResume()
//        map.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        map.onPause()
//    }

//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        days = savedInstanceState?.getString(FILTER_DISASTER_DAYS).toString()
//        list = savedInstanceState?.getStringArrayList(FILTER_DISASTER_LIST) as ArrayList<String>
//    }
}