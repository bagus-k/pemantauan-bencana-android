package com.bagus.pemantauanbencana.ui.disastermap

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.bagus.pemantauanbencana.BuildConfig
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.databinding.FragmentDisasterMapBinding
import com.bagus.pemantauanbencana.ui.filter.DisasterFilterActivity
import com.bagus.pemantauanbencana.ui.infowindow.MarkerWindow
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModel
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModelFactory
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.ArrayList

class DisasterMapFragment : Fragment() {

    private lateinit var binding: FragmentDisasterMapBinding

    companion object {
        private lateinit var map: MapView
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 1
        const val FILTER_DISASTER = "FILTER_DISASTER"
        var list: ArrayList<String> = ArrayList()
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

        if (activity != null) {
            Configuration.getInstance().load(requireActivity(), PreferenceManager.getDefaultSharedPreferences(requireActivity()));
            Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID

            map = binding.mapView
            map.setTileSource(TileSourceFactory.MAPNIK)
            map.setMultiTouchControls(false)
            map.setBuiltInZoomControls(false)

            val mapController = map.controller
            mapController.setZoom(8)

            val startPoint = GeoPoint(-7.5468636, 111.9801192)
            mapController.setCenter(startPoint)

            val factory = DisasterViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[DisasterViewModel::class.java]

            val string: Array<String> = arrayOf(
                "DROUGHT",
                "EARTHQUAKE",
                "FLOOD",
                "HIGHSURF",
                "HIGHWIND",
                "INCIDENT",
                "LANDSLIDE",
                "TORNADO",
                "VOLCANO",
                "WILDFIRE"
            )

            val extras = arguments
            if (extras != null) {
                list = extras.getStringArrayList(FILTER_DISASTER) as ArrayList<String>
                map.overlays.clear()
                if(list != null) {
                    if (list[0] == "0") {
                        list.clear()
                    } else {
                        list = extras.getStringArrayList(FILTER_DISASTER) as ArrayList<String>
                    }
                } else {
                    list.clear()
                }
            } else {
                for (item in string) {
                    list.add(item)
                }
            }

            viewModel.getAllDisaster().observe(viewLifecycleOwner) { disaster ->
                for (item in disaster) {
                    val marker = Marker(map)
                    if (item.latitude != "null" || item.longitude != "null" || item.latitude != null || item.longitude != null) {
                        marker.position =
                            GeoPoint(item.latitude.toDouble(), item.longitude.toDouble())
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        when (item.typeid) {
                            "DROUGHT" -> marker.icon =
                                ContextCompat.getDrawable(requireActivity(), R.mipmap.drought)
                            "EARTHQUAKE" -> marker.icon =
                                ContextCompat.getDrawable(requireActivity(), R.mipmap.earthquake)
                            "FLOOD" -> marker.icon =
                                ContextCompat.getDrawable(requireActivity(), R.mipmap.flood)
                            "HIGHSURF" -> marker.icon =
                                ContextCompat.getDrawable(requireActivity(), R.mipmap.highsurf)
                            "HIGHWIND" -> marker.icon =
                                ContextCompat.getDrawable(requireActivity(), R.mipmap.highwind)
                            "INCIDENT" -> marker.icon =
                                ContextCompat.getDrawable(requireActivity(), R.mipmap.incident)
                            "LANDSLIDE" -> marker.icon =
                                ContextCompat.getDrawable(requireActivity(), R.mipmap.landslide)
                            "TORNADO" -> marker.icon =
                                ContextCompat.getDrawable(requireActivity(), R.mipmap.tornado)
                            "VOLCANO" -> marker.icon =
                                ContextCompat.getDrawable(requireActivity(), R.mipmap.volcano)
                            "WILDFIRE" -> marker.icon =
                                ContextCompat.getDrawable(requireActivity(), R.mipmap.wildfire)
                        }

                        var infoWindow = MarkerWindow(map, item)
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

            binding.btnFilter.setOnClickListener {
                val intent = Intent(requireActivity(), DisasterFilterActivity::class.java)
                if (list.isNotEmpty()) {
                    intent.putExtra(FILTER_DISASTER, list)
                }
                startActivity(intent)
            }

            binding.btnZoomOut.setOnClickListener {
                map.controller.zoomOut()
            }

            binding.btnZoomIn.setOnClickListener {
                map.controller.zoomIn()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
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
}