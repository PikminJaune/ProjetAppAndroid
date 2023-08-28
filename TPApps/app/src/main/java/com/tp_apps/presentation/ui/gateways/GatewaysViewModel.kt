package com.tp_apps.presentation.ui.gateways

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp_apps.data.repositories.GatewayRepository
import com.tp_apps.domain.models.Gateway
import com.tp_apps.helpers.LoadingResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GatewaysViewModel : ViewModel() {

    private val gatewayRepository = GatewayRepository()

    private val _gateways = MutableLiveData<LoadingResource<List<Gateway>>>()
    val gateways : LiveData<LoadingResource<List<Gateway>>> get() = _gateways

    init {
        refreshGateways()
    }

    private fun refreshGateways() {
        viewModelScope.launch {
            gatewayRepository.retrieveAll().collect {
                _gateways.value = it
            }
        }
    }



}