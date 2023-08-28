package com.tp_apps.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tp_apps.R
import com.tp_apps.databinding.ItemGatewaysBinding
import com.tp_apps.domain.models.Gateway
import com.tp_apps.helpers.ColorHelper

class GatewaysRecyclerViewAdapter(
    var gateways: List<Gateway> = listOf(),
    private val onGatewayClick: (Gateway) -> Unit
) : RecyclerView.Adapter<GatewaysRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemGatewaysBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gateway: Gateway) {


            val context = binding.root.context

            if (gateway.connection.status == "Online") {
                binding.chipStatus.text = context.getText(R.string.Online)
                binding.txvLatence.text =
                    context.getString(R.string.ns, gateway.connection.ping.toString())
                binding.txvDownload.text =
                    context.getString(R.string.Ebps, gateway.connection.download.toString())
                binding.txvUpload.text =
                    context.getString(R.string.Ebps, gateway.connection.upload.toString())

                binding.txvOffline.visibility = View.INVISIBLE
                binding.txvLatence.visibility = View.VISIBLE
                binding.txvDownload.visibility = View.VISIBLE
                binding.txvUpload.visibility = View.VISIBLE
                binding.imvDownload.visibility = View.VISIBLE
                binding.imvLatence.visibility = View.VISIBLE
                binding.imvUpload.visibility = View.VISIBLE

            } else {
                binding.chipStatus.text = context.getText(R.string.Offline)
                binding.txvOffline.visibility = View.VISIBLE
                binding.txvLatence.visibility = View.INVISIBLE
                binding.txvDownload.visibility = View.INVISIBLE
                binding.txvUpload.visibility = View.INVISIBLE
                binding.imvDownload.visibility = View.INVISIBLE
                binding.imvLatence.visibility = View.INVISIBLE
                binding.imvUpload.visibility = View.INVISIBLE
            }

            binding.txvTicketId.text = gateway.serialNumber
            binding.chipStatus.chipBackgroundColor =
                ColorHelper.connectionStatusColor(binding.root.context, gateway.connection.status)
        }
    }

    //==========================================================================

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGatewaysBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gateway = gateways[position]
        holder.bind(gateway)

        holder.itemView.setOnClickListener {
            onGatewayClick(gateway)
        }
    }

    override fun getItemCount() = gateways.size

}