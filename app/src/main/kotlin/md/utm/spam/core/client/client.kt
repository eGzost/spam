package md.utm.spam.core.client

import android.util.Log
import md.utm.spam.core.cache.Cache
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun client(cache: Cache) = HttpClient {

    expectSuccess = true

    val key = cache.getKey()

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = "mailtrap.io/api"
//                path("/api/")
            header("Api-Token", key)
        }
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
            }
        )
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.d("mailtrap", message)
            }
        }
    }
}