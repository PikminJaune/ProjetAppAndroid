package com.tp_apps.presentation.ui.tickets

import androidx.lifecycle.*
import com.tp_apps.data.repositories.GatewayRepository
import com.tp_apps.data.repositories.TicketRepository
import com.tp_apps.domain.models.Borne
import com.tp_apps.domain.models.Gateway
import com.tp_apps.domain.models.Ticket
import com.tp_apps.helpers.Constants.TICKET_STATUS_OPEN
import com.tp_apps.helpers.Constants.TICKET_STATUS_SOLVE
import com.tp_apps.helpers.LoadingResource
import com.tp_apps.helpers.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class DetailTicketViewModel(private val href: String) : ViewModel() {

    private val ticketRepository = TicketRepository()
    private val _ticket = MutableLiveData<Resource<Ticket>>()
    val ticket: LiveData<Resource<Ticket>> get() = _ticket

    private val gatewayRepository = GatewayRepository()

    private val _gateways = MutableLiveData<LoadingResource<List<Gateway>>>()
    val gateways: LiveData<LoadingResource<List<Gateway>>> get() = _gateways

    private val _gateway = MutableLiveData<Resource<Gateway>>()
    val gateway: LiveData<Resource<Gateway>> get() = _gateway

//-------------------------------------------------------------------------------------------------

    fun addGateway(rawValue: String, href: String) {

        val borne : Borne = Json.decodeFromString(rawValue)

        viewModelScope.launch {
            //Ajouter le gateway au client
            _gateway.value = gatewayRepository.postOne(borne, href)

            //TODO Ajouter la nouvelle Borne à Gateways
            gatewayRepository.retrieveCustomerGatewaysNow(href).collect {
                _gateways.value = it
            }
        }
    }

    //-------------------------------------------------------------------------------------------------


    fun solveATicket() {
        viewModelScope.launch {
            _ticket.value = ticketRepository.changedStatus(href, TICKET_STATUS_SOLVE)
        }
    }

    fun openATicket() {
        viewModelScope.launch {
            _ticket.value = ticketRepository.changedStatus(href, TICKET_STATUS_OPEN)
        }
    }

//-------------------------------------------------------------------------------------------------

    init {
        viewModelScope.launch {
            _ticket.value = ticketRepository.retrieveOne(href)
        }
    }

    fun retrieveCustomerGateways(href: String) {
        viewModelScope.launch {
            gatewayRepository.retrieveCustomerGateways(href).collect {
                _gateways.value = it
            }
        }

    }

    class Factory(private val href: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(String::class.java).newInstance(href)
        }

    }

//-------------------------------------------------------------------------------------------------


}