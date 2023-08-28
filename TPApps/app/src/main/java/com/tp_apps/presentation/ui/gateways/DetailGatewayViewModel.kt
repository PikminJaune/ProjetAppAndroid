package com.tp_apps.presentation.ui.gateways

import androidx.lifecycle.*
import com.tp_apps.data.repositories.GatewayRepository
import com.tp_apps.domain.models.Gateway
import com.tp_apps.helpers.Constants.GATEWAY_CHANGE_REBOOT
import com.tp_apps.helpers.Constants.GATEWAY_CHANGE_UPDATE
import com.tp_apps.helpers.LoadingResource
import com.tp_apps.helpers.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailGatewayViewModel(private val href:String) : ViewModel() {

    private val gatewayRepository = GatewayRepository()

    private val _gateway = MutableLiveData<Resource<Gateway>>()
    val gateway: LiveData<Resource<Gateway>> get() = _gateway

     fun rebootAGateway(){
        viewModelScope.launch {
            _gateway.value = gatewayRepository.changeGateway(href, GATEWAY_CHANGE_REBOOT)
        }
    }

    fun updateAGateway(){
        viewModelScope.launch {
            _gateway.value = gatewayRepository.changeGateway(href,GATEWAY_CHANGE_UPDATE)
        }
    }



    class Factory(private val href: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(String::class.java).newInstance(href)
        }

    }

    init {
        viewModelScope.launch {
            gatewayRepository.retrieveOneGateway(href).collect {
                _gateway.value = it
            }
        }
    }
}