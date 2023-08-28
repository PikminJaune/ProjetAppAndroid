package com.tp_apps.data.datasources

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.tp_apps.domain.models.Network
import com.tp_apps.helpers.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class NetworkDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun retrieveAll() : Network{
        return withContext(Dispatchers.IO) {
            val(_,_,result) = Constants.BaseURL.NETWORK.httpGet().responseJson()
            when(result){
                is Result.Success -> {
                    return@withContext json.decodeFromString(result.value.content)
                }
                is Result.Failure -> {
                    throw result.error.exception
                }
            }
        }

    }


}