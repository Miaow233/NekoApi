package cafe.nekohouse.nekoapi.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<AuthenticationException> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> { call, cause ->
            call.respond(HttpStatusCode.Forbidden)
        }

    }

    install(AutoHeadResponse)

    routing {
        get("/") {
            call.respondText("还没开发完呢，再等亿下（")
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }




}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
