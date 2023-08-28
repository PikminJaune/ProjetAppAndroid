package com.tp_apps.data.datasources


import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.tp_apps.domain.models.*
import com.tp_apps.helpers.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GatewayDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun retrieveAll() : List<Gateway> {
        return withContext(Dispatchers.IO) {
            val (_, _, result) = Constants.BaseURL.GATEWAYS.httpGet().responseJson()
            when(result) {
                is Result.Success -> {
                    return@withContext json.decodeFromString(result.value.content)
                }
                is Result.Failure -> {
                    throw result.error.exception
                }
            }
        }
    }

    suspend fun postOne(borne: Borne, href: String): Gateway {
        return withContext(Dispatchers.IO) {
            val body = json.encodeToString(borne)
            val (_, _, result) = "${href}/gateways".httpPost().jsonBody(body).responseJson()
            when (result) {
                is Result.Success -> {
                    return@withContext json.decodeFromString<Gateway>(result.value.content)
                }
                is Result.Failure -> {
                    throw result.error.exception
                }
            }
        }
    }

    suspend fun retrieveCustomerGateways(href: String) : List<Gateway>{
        return withContext(Dispatchers.IO) {
            val (_, _, result) = "${href}/gateways".httpGet().responseJson()
            when(result) {
                is Result.Success -> {
                    return@withContext json.decodeFromString(result.value.content)

                }
                is Result.Failure -> {
                    throw result.error.exception

                }
            }
        }
    }

    suspend fun changeGateway(href: String, status : String): Gateway {
        return withContext(Dispatchers.IO) {
            val (_, _, result) = "${href}/actions?type=${status}".httpPost().responseJson()
            when (result) {
                is Result.Success -> {
                    return@withContext json.decodeFromString<Gateway>(result.value.content)
                }
                is Result.Failure -> {
                    throw result.error.exception
                }
            }
        }
    }

    suspend fun retrieveOneGateway(href: String): Gateway {
        return withContext(Dispatchers.IO) {
            val (_, _, result) = href.httpGet().responseJson()
            when (result) {
                is Result.Success -> {
                    return@withContext json.decodeFromString<Gateway>(result.value.content)
                }
                is Result.Failure -> {
                    throw result.error.exception
                }
            }
        }
    }


}