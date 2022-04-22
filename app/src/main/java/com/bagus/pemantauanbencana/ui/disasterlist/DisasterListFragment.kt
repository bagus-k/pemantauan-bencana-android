package com.bagus.pemantauanbencana.ui.disasterlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagus.pemantauanbencana.databinding.FragmentDisasterListBinding
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModel
import com.bagus.pemantauanbencana.viewmodel.DisasterViewModelFactory

class DisasterListFragment : Fragment() {

    private lateinit var binding: FragmentDisasterListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisasterListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = DisasterViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(requireActivity(), factory)[DisasterViewModel::class.java]

        val disasterAdapter = DisasterAdapter()

        viewModel.getAllDisaster().observe(requireActivity()) { disaster ->
            if (disaster != null) {
                binding.listItemShimmer.stopShimmerAnimation()
                binding.listItemShimmer.visibility = View.GONE
                binding.rvDisaster.visibility = View.VISIBLE
                disasterAdapter.setDisaster(disaster)
                disasterAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireActivity(), "Koneksi Error", Toast.LENGTH_SHORT).show()
            }
        }

        with(binding.rvDisaster) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = disasterAdapter
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
}