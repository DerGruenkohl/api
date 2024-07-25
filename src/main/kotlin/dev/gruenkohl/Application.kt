package dev.gruenkohl


import dev.gruenkohl.model.startTracking
import dev.gruenkohl.plugins.configureRouting
import dev.gruenkohl.plugins.configureSerialization
import io.ktor.server.application.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}


fun Application.module() {
    startTracking()
    configureSerialization()
    configureRouting()

}
