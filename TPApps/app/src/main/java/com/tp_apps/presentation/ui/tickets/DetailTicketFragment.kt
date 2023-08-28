package com.tp_apps.presentation.ui.tickets

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.tp_apps.R
import com.tp_apps.data.repositories.GatewayRepository
import com.tp_apps.databinding.FragmentDetailTicketBinding
import com.tp_apps.domain.models.Gateway
import com.tp_apps.helpers.*
import com.tp_apps.helpers.ColorHelper.ticketPriorityColor
import com.tp_apps.helpers.ColorHelper.ticketStatusColor
import com.tp_apps.presentation.adapters.GatewaysRecyclerViewAdapter
import com.tp_apps.presentation.ui.gateways.GatewaysFragmentDirections
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode


class DetailTicketFragment : Fragment(R.layout.fragment_detail_ticket) {

    private val binding: FragmentDetailTicketBinding by viewBinding()
    private val viewModel: DetailTicketViewModel by viewModels {
        DetailTicketViewModel.Factory(args.href)
    }

    private var position: LatLng? = null

    private lateinit var gatewaysRecyclerViewAdapter: GatewaysRecyclerViewAdapter

    private val args: DetailTicketFragmentArgs by navArgs()

    //----------------------------------------------------------------------------------------------------------

    private val quickieActivityLauncher =
        registerForActivityResult(ScanQRCode(), ::handleQuickieQRResult)

    private val gatewayRepository = GatewayRepository()

    //----------------------------------------------------------------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonInstall.text = getString(R.string.Install)

        gatewaysRecyclerViewAdapter =
            GatewaysRecyclerViewAdapter(listOf(), ::onRecyclerViewClick)

        binding.rcvGatewaysTicket.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = gatewaysRecyclerViewAdapter
        }

        //========================================================================================

        //GET ALL
        viewModel.gateways.observe(viewLifecycleOwner) {
            when (it) {
                is LoadingResource.Error -> {
                    Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
                }
                is LoadingResource.Loading -> {

                }
                is LoadingResource.Success -> {
                    gatewaysRecyclerViewAdapter.gateways = it.data!!
                    gatewaysRecyclerViewAdapter.notifyAllItemChanged()
                }
            }
        }

        //POST ONE
        viewModel.gateway.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(context, R.string.Installation_Bad, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(context, R.string.Installation_Good, Toast.LENGTH_SHORT).show()
                }
            }
        }

        //========================================================================================

        viewModel.ticket.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    /* TODO : Changer le message */
                    Toast.makeText(requireContext(), it.throwable.message, Toast.LENGTH_SHORT)
                        .show()
                    requireActivity().supportFragmentManager.popBackStack()
                }
                is Resource.Success -> {

                    val ticket = it.data!!
                    viewModel.retrieveCustomerGateways(it.data.customer.href)


                    binding.txvTicketId.text = ticket.ticketNumber
                    binding.txvTicketDate.text = DateHelper.formatISODate(ticket.createdDate)

                    binding.chipHaut.chipBackgroundColor =
                        ticketPriorityColor(requireContext(), ticket.priority)
                    binding.chipHaut.text = ticket.priority


                    binding.txvNomPrenomTicket.text =
                        "${ticket.customer.firstName} ${ticket.customer.lastName}"
                    binding.txvAddressTicket.text = ticket.customer.address
                    binding.txvVilleTicket.text = ticket.customer.city

                    Glide.with(binding.root.context)
                        .load(Constants.FLAG_API_URL.format(ticket.customer.country).lowercase())
                        .into(binding.imvPaysTicket)


                    position = LatLng(
                        ticket.customer.coord.latitude.toDouble(),
                        ticket.customer.coord.longitude.toDouble()
                    )

                    with(binding){
                        chipBas.text = ticket.status
                        chipBas.chipBackgroundColor = ticketStatusColor(requireContext(), ticket.status)
                    }

                    //Solve and Open a ticket !!
                    when (Constants.TicketStatus.valueOf(ticket.status)) {
                        Constants.TicketStatus.Open -> {
                            with(binding) {
                                buttonSolve.visibility = View.VISIBLE
                                buttonInstall.visibility = View.VISIBLE
                                buttonOpen!!.visibility = View.INVISIBLE
                            }
                        }
                        Constants.TicketStatus.Solved -> {
                            with(binding){
                                buttonSolve.visibility = View.INVISIBLE
                                buttonInstall.visibility = View.INVISIBLE
                                buttonOpen!!.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }


        /* Button pour ouvrir le scan du codeQR*/
        binding.buttonInstall.setOnClickListener() {
            quickieActivityLauncher.launch(null)
        }

        binding.buttonSolve.setOnClickListener{
            viewModel.solveATicket()
        }

        binding.buttonOpen.setOnClickListener {
            viewModel.openATicket()
        }

        binding.floatingActionButton.setOnClickListener {
            val action = DetailTicketFragmentDirections
                .actionDetailTicketFragmentToMapsActivity(position!!)
            findNavController().navigate(action)
        }

    }



    //----------------------------------------------------------------------------------------------------------

    private fun handleQuickieQRResult(qrResult: QRResult) {
        when (qrResult) {
            is QRResult.QRSuccess -> {
                //Avoir accès au href du client
                val href = viewModel.ticket.value!!.data!!.customer.href

                Log.e("post", href)

                //Ajouter la borne
                viewModel.addGateway(qrResult.content.rawValue, href)


            }
            is QRResult.QRUserCanceled -> {
                Toast.makeText(context, "Tentative de scan annulée", Toast.LENGTH_SHORT).show()
            }
            is QRResult.QRMissingPermission -> {
                Toast.makeText(context, "Manque la permission de camera", Toast.LENGTH_SHORT).show()
            }
            is QRResult.QRError -> {
                Toast.makeText(context, qrResult.exception.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    //----------------------------------------------------------------------------------------------------------

    private fun onRecyclerViewClick(gateway: Gateway) {
        Toast.makeText(requireContext(), gateway.serialNumber, Toast.LENGTH_LONG).show()
        val direction =
            GatewaysFragmentDirections.actionNavigationGatewaysToDetailGatewayFragment(gateway.href)
        findNavController().navigate(direction)
    }

}