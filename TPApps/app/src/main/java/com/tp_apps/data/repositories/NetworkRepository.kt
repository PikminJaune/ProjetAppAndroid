package com.tp_apps.data.repositories

import com.tp_apps.data.datasources.NetworkDataSource
import com.tp_apps.domain.models.Network
import com.tp_apps.domain.models.NetworkNode
import com.tp_apps.helpers.Constants
import com.tp_apps.helpers.LoadingResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkRepository {

    private val networkDataSource = NetworkDataSource()

    suspend fun retrieveAll() : Flow<LoadingResource<Network>>{
        return flow {
            while (true){
                try {
                    emit(LoadingResource.Loading())
                    delay(2000)
                    emit(LoadingResource.Success(networkDataSource.retrieveAll()))
                } catch(ex : Exception){
                    emit(LoadingResource.Error(ex,ex.message))
                }
                delay(Constants.REFRESH_NETWORK_DELAY)
            }
        }
    }
}