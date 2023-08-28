package com.tp_apps.presentation.ui.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp_apps.data.repositories.NetworkRepository
import com.tp_apps.domain.models.Network
import com.tp_apps.helpers.LoadingResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NetworkViewModel : ViewModel() {

   private val networkRepository = NetworkRepository()

    private val _networks = MutableLiveData<LoadingResource<Network>>()
    val networks : LiveData<LoadingResource<Network>> get() = _networks

    init {
        viewModelScope.launch {
            networkRepository.retrieveAll().collect {
                _networks.value = it
            }
        }
    }


}