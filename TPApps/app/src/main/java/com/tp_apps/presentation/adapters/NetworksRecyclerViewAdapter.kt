package com.tp_apps.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tp_apps.R
import com.tp_apps.databinding.ItemNetworkBinding
import com.tp_apps.domain.models.NetworkNode
import com.tp_apps.helpers.ColorHelper

class NetworksRecyclerViewAdapter(var networkNodes: List<NetworkNode> = listOf())
    : RecyclerView.Adapter<NetworksRecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : NetworksRecyclerViewAdapter.ViewHolder {
        return ViewHolder(ItemNetworkBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NetworksRecyclerViewAdapter.ViewHolder, position: Int) {
        val network = networkNodes[position]
        holder.bind(network)

    }

    override fun getItemCount() = networkNodes.count()


    inner class ViewHolder(private val binding: ItemNetworkBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(networkNodes : NetworkNode){
            binding.txvNomNoeud.text = networkNodes.name
            binding.txvAdresseIP.text = networkNodes.connection.ip

            binding.txvDownload.text = "${networkNodes.connection.download.toString()} Ebps"
            binding.txvLatence.text = "${networkNodes.connection.ping.toString()} ns"
            binding.txvUpload.text = "${networkNodes.connection.upload.toString()} Ebps"
            binding.txvQualiteSignal.text = "${networkNodes.connection.signal.toString()} dBm"

            val context = binding.root.context

            if(networkNodes.connection.status == context.getText(R.string.Online_Server)){
                binding.chipHaut.text = context.getText(R.string.Online)
                binding.chipHaut.chipBackgroundColor = ColorHelper.connectionStatusColor(binding.root.context,networkNodes.connection.status)
            }
            else{
                binding.chipHaut.text = context.getText(R.string.Offline)
                binding.chipHaut.chipBackgroundColor = ColorHelper.connectionStatusColor(binding.root.context,networkNodes.connection.status)
            }






        }

    }

}