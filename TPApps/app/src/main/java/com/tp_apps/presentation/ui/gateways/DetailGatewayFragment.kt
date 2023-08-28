package com.tp_apps.presentation.ui.gateways

import android.graphics.Color
import android.os.Bundle
import android.provider.Contacts
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tp_apps.R
import com.tp_apps.databinding.FragmentDetailGatewayBinding
import com.tp_apps.helpers.ColorHelper
import com.tp_apps.helpers.Constants
import com.tp_apps.helpers.Resource
import com.tp_apps.helpers.loadFromResource

class DetailGatewayFragment : Fragment(R.layout.fragment_detail_gateway) {

    private val binding: FragmentDetailGatewayBinding by viewBinding()
    private val viewModel: DetailGatewayViewModel by viewModels{
        DetailGatewayViewModel.Factory(args.href)
    }

    private val args : DetailGatewayFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gateway.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {

                }
                is Resource.Success -> {
                    val gateway = it.data!!
                    //binding.chipsStatus!!.text = gateway!!.connection.status
                    when (Constants.ConnectionStatus.valueOf(gateway.connection.status)) {
                        Constants.ConnectionStatus.Online -> {
                            with(binding) {
                                chipsStatus!!.text = getString(R.string.Online)
                                chipsStatus.chipBackgroundColor = ColorHelper.connectionStatusColor(binding.root.context,gateway.connection.status)
                                txvIP!!.text = gateway.connection.ip
                                txvSerialNumber!!.text = gateway.serialNumber
                                txvMac!!.text = gateway.config.mac
                                txvSSID!!.text = "SSID: ${gateway.config.SSID}"
                                txvPin!!.text = "PIN: ${gateway.pin}"
                                txvPing!!.text = "${gateway.connection.ping} ns"
                                txvDownload!!.text = "${String.format("%.3f", gateway.connection.download)} Ebps"
                                txvUpload!!.text = "${String.format("%.3f", gateway.connection.upload)} Ebps"
                                txvSignal!!.text = "${gateway.connection.signal} dBm "
                                imgElem1!!.loadFromResource(binding.root.context, "element_${gateway.config.kernel[0].lowercase()}")
                                imgElem2!!.loadFromResource(binding.root.context, "element_${gateway.config.kernel[1].lowercase()}")
                                imgElem3!!.loadFromResource(binding.root.context, "element_${gateway.config.kernel[2].lowercase()}")
                                imgElem4!!.loadFromResource(binding.root.context, "element_${gateway.config.kernel[3].lowercase()}")
                                imgElem5!!.loadFromResource(binding.root.context, "element_${gateway.config.kernel[4].lowercase()}")
                                txvKernelVersion!!.text = "Kernel revision ${gateway.revision} Version ${gateway.config.version}"
                                binding.txvHash0!!.text = gateway.hash.substring(0,2)
                                binding.txvHash1!!.setBackgroundColor(Color.parseColor("#" + gateway.hash.substring(2, 8)))
                                binding.txvHash2!!.setBackgroundColor(Color.parseColor("#" + gateway.hash.substring(8, 14)))
                                binding.txvHash3!!.setBackgroundColor(Color.parseColor("#" + gateway.hash.substring(14, 20)))
                                binding.txvHash4!!.setBackgroundColor(Color.parseColor("#" + gateway.hash.substring(20, 26)))
                                binding.txvHash5!!.setBackgroundColor(Color.parseColor("#" + gateway.hash.substring(26, 32)))
                                binding.txvHash6!!.setBackgroundColor(Color.parseColor("#" + gateway.hash.substring(32, 38)))
                                binding.txvHash7!!.setBackgroundColor(Color.parseColor("#" + gateway.hash.substring(38, 44)))
                                binding.txvHash8!!.setBackgroundColor(Color.parseColor("#" + gateway.hash.substring(44, 50)))
                                binding.txvHash9!!.setBackgroundColor(Color.parseColor("#" + gateway.hash.substring(50, 56)))
                                binding.txvHash10!!.setBackgroundColor(Color.parseColor("#" + gateway.hash.substring(56, 62)))
                                binding.txvHash11!!.text = gateway.hash.substring(62,64)
                                btnReboot!!.visibility = View.VISIBLE
                                btnUpdate!!.visibility = View.VISIBLE
                            }
                        }
                        Constants.ConnectionStatus.Offline -> {
                            with(binding){
                                chipsStatus!!.text = getString(R.string.Offline)
                                chipsStatus.text = getString(R.string.Offline)
                                chipsStatus.chipBackgroundColor = ColorHelper.connectionStatusColor(binding.root.context,gateway.connection.status)
                                txvIP!!.text = gateway.connection.ip
                                txvSerialNumber!!.text = gateway.serialNumber
                                txvMac!!.text = gateway.config.mac
                                txvSSID!!.text = "SSID: ${gateway.config.SSID}"
                                txvPin!!.text = "PIN: ${gateway.pin}"
                                txvIP!!.visibility = View.INVISIBLE
                                txvPing!!.visibility = View.INVISIBLE
                                txvDownload!!.visibility = View.INVISIBLE
                                txvUpload!!.visibility = View.INVISIBLE
                                txvSignal!!.visibility = View.INVISIBLE
                                txvNA!!.visibility = View.VISIBLE
                                txvNA!!.text = "N/A"
                                imgElem1!!.loadFromResource(binding.root.context, "element_${gateway.config.kernel[0].lowercase()}")
                                imgElem2!!.loadFromResource(binding.root.context, "element_${gateway.config.kernel[1].lowercase()}")
                                imgElem3!!.loadFromResource(binding.root.context, "element_${gateway.config.kernel[2].lowercase()}")
                                imgElem4!!.loadFromResource(binding.root.context, "element_${gateway.config.kernel[3].lowercase()}")
                                imgElem5!!.loadFromResource(binding.root.context, "element_${gateway.config.kernel[4].lowercase()}")
                                txvKernelVersion!!.text = "Kernel revision ${gateway.revision} Version ${gateway.config.version}"
                                binding.btnReboot!!.visibility = View.INVISIBLE
                                binding.btnUpdate!!.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
            }
        }
        binding.btnReboot!!.setOnClickListener{
            viewModel.rebootAGateway()
        }
        binding.btnUpdate!!.setOnClickListener{
            viewModel.updateAGateway()
        }
    }



}