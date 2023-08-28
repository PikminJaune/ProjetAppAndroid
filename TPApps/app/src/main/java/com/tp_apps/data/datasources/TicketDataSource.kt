package com.tp_apps.data.datasources


import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.tp_apps.helpers.Constants.BaseURL.TICKETS_URL
import com.tp_apps.domain.models.Ticket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class TicketDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun retrieveAll(): List<Ticket> {

         return withContext(Dispatchers.IO) {
            val (_, _, result) = TICKETS_URL.httpGet().responseJson()
            when(result) {
                is com.github.kittinunf.result.Result.Success -> {
                    return@withContext json.decodeFromString(result.value.content)
                }
                is com.github.kittinunf.result.Result.Failure -> {
                    throw result.error.exception
                }
            }
        }
    }

    suspend fun retrieveOne(href: String) : Ticket {
        return withContext(Dispatchers.IO){
            val (_,_,result) = href.httpGet().responseJson()
            when(result){
                is Result.Success -> {
                    return@withContext json.decodeFromString<Ticket>(result.value.content)
                }
                is Result.Failure -> {
                    throw result.error.exception
                }
            }
        }
    }

    suspend fun changedStatus(href: String, status : String): Ticket {
        return withContext(Dispatchers.IO) {
            val (_, _, result) = "${href}/actions?type=${status}".httpPost().responseJson()
            when (result) {
                is Result.Success -> {
                    return@withContext json.decodeFromString<Ticket>(result.value.content)
                }
                is Result.Failure -> {
                    throw result.error.exception
                }
            }
        }
    }


}