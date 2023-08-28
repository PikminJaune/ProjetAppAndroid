package com.tp_apps.presentation.ui.gateways

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tp_apps.R
import com.tp_apps.databinding.FragmentGatewaysBinding
import com.tp_apps.domain.models.Gateway
import com.tp_apps.helpers.LoadingResource
import com.tp_apps.helpers.notifyAllItemChanged
import com.tp_apps.presentation.adapters.GatewaysRecyclerViewAdapter

class GatewaysFragment : Fragment(R.layout.fragment_gateways) {

    private val binding: FragmentGatewaysBinding by viewBinding()
    private val viewModel: GatewaysViewModel by viewModels()

    private lateinit var gatewaysRecyclerViewAdapter: GatewaysRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gatewaysRecyclerViewAdapter = GatewaysRecyclerViewAdapter(listOf(), ::onRecyclerViewClick)
        binding.rcvGateways.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = gatewaysRecyclerViewAdapter
        }

        viewModel.gateways.observe(viewLifecycleOwner) {

            when (it) {
                is LoadingResource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is LoadingResource.Success -> {
                    binding.pgbLoading.hide()
                    gatewaysRecyclerViewAdapter.gateways = it.data!!
                    gatewaysRecyclerViewAdapter.notifyAllItemChanged()
                    binding.rcvGateways.visibility = View.VISIBLE
                }
                is LoadingResource.Loading -> {
                    binding.pgbLoading.show()
                    binding.rcvGateways.visibility = View.INVISIBLE
                }
            }
        }
    }

    //Quand on clic sur un gateway
    private fun onRecyclerViewClick(gateway: Gateway) {
        val direction = GatewaysFragmentDirections.actionNavigationGatewaysToDetailGatewayFragment(gateway.href)
        findNavController().navigate(direction)


    }
}