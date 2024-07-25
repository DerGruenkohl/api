package dev.gruenkohl.hypixel

import dev.gruenkohl.model.weights.weights
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

object WeightManager {
    val client by lazy {
        HttpClient(CIO) {
            expectSuccess = false
            install(Logging)
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }
    val weights by lazy {
        runBlocking {
            val weight = client.request("https://api.elitebot.dev/weights/all").body<weights>()
            client.close()
            return@runBlocking weight
        }

    }
}