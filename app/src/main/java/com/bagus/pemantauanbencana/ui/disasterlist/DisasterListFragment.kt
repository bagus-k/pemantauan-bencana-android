package com.bagus.pemantauanbencana.ui.disasterlist

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bagus.pemantauanbencana.databinding.FragmentDisasterListBinding
import com.bagus.pemantauanbencana.ui.disastermap.DisasterMapFragment
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModel
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModelFactory

class DisasterListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentDisasterListBinding
    private var checkListDisaster: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisasterListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (checkForInternet(requireActivity())) {
            init()
            Handler().postDelayed({
                if (!checkListDisaster) {
                    Toast.makeText(requireActivity(), "Koneksi Error, gulir ke bawah untuk reload", Toast.LENGTH_SHORT).show()
                }
            }, 10000)
        } else {
            Toast.makeText(requireActivity(), "Tidak ada koneksi internet, gulir ke bawah untuk reload", Toast.LENGTH_SHORT).show()
        }

        binding.swiperefresh.setOnRefreshListener(this)
    }

    private fun init() {
        val factory = DisasterViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(requireActivity(), factory)[DisasterViewModel::class.java]

        val disasterAdapter = DisasterAdapter()

        viewModel.getAllDisaster().observe(requireActivity()) { disaster ->
            when {
                disaster.isNotEmpty() -> {
                    binding.listItemShimmer.stopShimmerAnimation()
                    binding.listItemShimmer.visibility = View.GONE
                    binding.rvDisaster.visibility = View.VISIBLE
                    disasterAdapter.setDisaster(disaster)
                    disasterAdapter.notifyDataSetChanged()
                    checkListDisaster = true
                }
                disaster.isEmpty() -> {
                    Toast.makeText(requireActivity(), "Data tidak ditemukan" ,Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireActivity(), "Koneksi Error" ,Toast.LENGTH_SHORT).show()
                }
            }
        }

        with(binding.rvDisaster) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = disasterAdapter
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

    override fun onResume() {
        super.onResume()
        binding.listItemShimmer.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        binding.listItemShimmer.stopShimmerAnimation()
    }

    override fun onRefresh() {
        binding.listItemShimmer.startShimmerAnimation()
        binding.listItemShimmer.visibility = View.VISIBLE
        binding.rvDisaster.visibility = View.GONE
        if (checkForInternet(requireActivity())) {
            init()
            Handler().postDelayed({
                if (!checkListDisaster) {
                    Toast.makeText(requireActivity(), "Koneksi Error, gulir ke bawah untuk reload", Toast.LENGTH_SHORT).show()
                }
            }, 10000)
        } else {
            Toast.makeText(requireActivity(), "Tidak ada koneksi internet, gulir ke bawah untuk reload", Toast.LENGTH_SHORT).show()
        }
        binding.swiperefresh.isRefreshing = false
    }
}