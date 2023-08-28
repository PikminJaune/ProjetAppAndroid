package com.tp_apps.presentation.ui.tickets

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tp_apps.R
import com.tp_apps.databinding.FragmentTicketsBinding
import com.tp_apps.domain.models.Ticket
import com.tp_apps.helpers.LoadingResource
import com.tp_apps.helpers.notifyAllItemChanged
import com.tp_apps.presentation.adapters.TicketsRecyclerViewAdapter


class TicketsFragment : Fragment(R.layout.fragment_tickets) {

    private val binding: FragmentTicketsBinding by viewBinding()
    private val viewModel: TicketsViewModel by viewModels()

    private lateinit var ticketsRecyclerViewAdapter: TicketsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ticketsRecyclerViewAdapter = TicketsRecyclerViewAdapter(listOf(), ::onTicketItemClick)

        binding.rcvTickets.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ticketsRecyclerViewAdapter
        }

        viewModel.tickets.observe(viewLifecycleOwner) {
            when(it){
                is LoadingResource.Error -> {
                    Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_LONG).show()
                }
                is LoadingResource.Loading -> {

                    binding.rcvTickets.visibility = View.INVISIBLE
                }
                is LoadingResource.Success -> {
                    ticketsRecyclerViewAdapter.tickets = it.data!!
                    ticketsRecyclerViewAdapter.notifyAllItemChanged()
                    binding.rcvTickets.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onTicketItemClick(ticket: Ticket) {
        val direction = TicketsFragmentDirections.actionNavigationTicketsToDetailTicketFragment(ticket.href)
        findNavController().navigate(direction)
    }

}