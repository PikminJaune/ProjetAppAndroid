package com.tp_apps.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tp_apps.databinding.ItemTicketBinding
import com.tp_apps.domain.models.Ticket
import com.tp_apps.helpers.ColorHelper
import com.tp_apps.helpers.DateHelper


class TicketsRecyclerViewAdapter(var tickets: List<Ticket> = listOf(),
        private val onTicketItemClick: (Ticket) -> Unit
) : RecyclerView.Adapter<TicketsRecyclerViewAdapter.ViewHolder>() {

    override  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTicketBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.bind(ticket)
        holder.itemView.setOnClickListener{
            onTicketItemClick(ticket)
        }
    }

    override fun getItemCount() = tickets.size


    inner class ViewHolder(private val binding: ItemTicketBinding) :RecyclerView.ViewHolder(binding.root) {

        fun bind(ticket: Ticket) {
            binding.txvTicketId.text = ticket.ticketNumber
            binding.chipBas.text = ticket.status
            binding.chipBas.chipBackgroundColor = ColorHelper.ticketStatusColor(binding.root.context,ticket.status)
            binding.chipHaut.text = ticket.priority
            binding.chipHaut.chipBackgroundColor = ColorHelper.ticketPriorityColor(binding.root.context, ticket.priority)
            binding.txvTicketDate.text = DateHelper.formatISODate(ticket.createdDate)
        }
    }




}
