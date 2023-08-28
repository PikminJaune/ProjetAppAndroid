package com.tp_apps.presentation.ui.tickets


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp_apps.data.repositories.TicketRepository
import com.tp_apps.domain.models.Ticket
import com.tp_apps.helpers.LoadingResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TicketsViewModel : ViewModel() {

    private val ticketRepository = TicketRepository()

    private val _tickets = MutableLiveData<LoadingResource<List<Ticket>>>()
    val tickets: LiveData<LoadingResource<List<Ticket>>> get() = _tickets

    init {
        viewModelScope.launch {
            ticketRepository.retrieveAll().collect{
                _tickets.value = it
            }
        }
    }

}