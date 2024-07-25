package dev.gruenkohl.plugins

import dev.gruenkohl.dsl.getMinecraftUUID
import dev.gruenkohl.feature.TrackManager
import dev.gruenkohl.hypixel.ExpApi
import dev.gruenkohl.model.*
import dev.gruenkohl.sql.LinkManager
import dev.gruenkohl.sql.TrackSQLManager
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {
    routing {
        staticResources("static", "static")
        get("/tasks") {
            call.respond(
                listOf(
                    Task("cleaning", "Clean the house", Priority.Low),
                    Task("gardening", "Mow the lawn", Priority.Medium),
                    Task("shopping", "Buy the groceries", Priority.High),
                    Task("painting", "Paint the fence", Priority.Medium)
                )
            )
        }
        route("/api"){
            get("/leaderboard/uptime"){
                call.respond(buildLeaderboard())
            }
            route("/uptime"){
                get("/player/{player}"){
                    val name = call.parameters["player"]
                    name?.let {
                        call.respond(getMember(getMinecraftUUID(name))!!)
                        return@get
                    }
                    call.respond(HttpStatusCode.NotFound, "Not Found/Not entered")
                }
                get("/guild/{player}"){
                    val name = call.parameters["player"]
                    name?.let {
                        call.respond(getGuild((name)))
                        return@get
                    }
                    call.respond(HttpStatusCode.NotFound, "Not Found/Not entered")
                }
            }
            route("link"){
                get("/get/{discordId}"){
                    val id = call.parameters["discordId"]
                    id?.let {
                        val discordId = id.toLong()
                        val link = getLink(discordId)
                        if (link.uuid==""){
                            call.respond(HttpStatusCode.NotFound, "Not Linked")
                        }
                        call.respond(getLink(discordId))
                        return@get
                    }
                    call.respond(HttpStatusCode.NotFound, "Not Found/Not entered")
                    return@get
                }
                post("/add") {
                    try {
                        val link = call.receive<Link>()
                        val res =addLink(link)
                        if (res){
                            call.respond(HttpStatusCode.NoContent)
                        }else{
                            call.respond(HttpStatusCode.Unauthorized, "Discord incorrectly linked")
                        }

                    } catch (ex: IllegalStateException) {
                        call.respond(HttpStatusCode.BadRequest)
                    } catch (ex: JsonConvertException) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
                post("/update") {
                    val setting = call.receive<SettingModifier>()
                    val linkManager = LinkManager()
                    val oldLink = linkManager.getLink(setting.discordId)
                    try {
                        val newLink = modifySetting(oldLink, setting.modifiedSetting)
                        linkManager.addLink(newLink)
                        call.respond(HttpStatusCode.NoContent)
                    }catch (e: IllegalArgumentException){
                        call.respond(HttpStatusCode.BadRequest)
                    }

                }
            }
            route("/track"){
                get("/temp"){
                    val link = getLink(758041565099851867)
                    val man = TrackManager(link)
                    man.buildTracker()
                    call.respond("meow")
                }
                get("/get/{discordID}"){
                    val id = call.parameters["discordID"]
                    id?.let {
                        val discordId = id.toLong()
                        val link = getLink(discordId)
                        val trackManager = TrackManager(link)
                        val manager = TrackSQLManager(link)
                        if (!manager.checkTrack()){
                            call.respond(HttpStatusCode.NotFound, "Not tracked")
                        }


                        val track = trackManager.getTrack()
                        call.respond(track)
                        return@get
                    }
                    call.respond(HttpStatusCode.NotFound, "Not Found/Not entered")
                    return@get
                }
            }
        }

        get("/guild/{guildname}"){
            val name = call.parameters["guildname"]
            name?.let {
                if(name.contains("goodz", ignoreCase = true)){
                    call.respond(
                        transformGuildMember(ExpApi().getGuildMembers(name))
                    )
                }
                else{
                    call.respond(HttpStatusCode.BadRequest, "Not a GooDz Guild")
                }
            }

        }
        get("player/{playername}"){
            val name = call.parameters["playername"]
            name?.let {
                call.respond(ExpApi().getGuild(name))
            }
        }

    }
}
