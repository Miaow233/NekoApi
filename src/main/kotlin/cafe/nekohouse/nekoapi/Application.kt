package cafe.nekohouse.nekoapi

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import cafe.nekohouse.nekoapi.plugins.*

fun main() {
    // val port = args[1]?.toInt() ?: 8080
    embeddedServer(Netty, port = 8088, host = "0.0.0.0") {
        // configureSerialization()
        configureSecurity()
        configureRouting()
        configureZone()
        configureAiPoem()
    }.start(wait = true)
}
