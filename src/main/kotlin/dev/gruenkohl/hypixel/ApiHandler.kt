package dev.gruenkohl.hypixel

import dev.gruenkohl.apikey
import dev.gruenkohl.dsl.getMinecraftUUID
import net.hypixel.api.HypixelAPI
import net.hypixel.api.reactor.ReactorHttpClient
import net.hypixel.api.reply.GuildReply
import java.util.*

open class ApiHandler {
    private val client by lazy {
        ReactorHttpClient(UUID.fromString(apikey))
    }

    protected val hypixelAPI by lazy {
        HypixelAPI(client)
    }

    fun getGuildMembers(name: String): List<GuildReply.Guild.Member> {
      return hypixelAPI.getGuildByName(name).get().guild.members
    }
    fun getSelectedProfile(name: String): String{
        hypixelAPI.getSkyBlockProfiles(name).get().profiles.forEach {

            if(it.asJsonObject["selected"].asBoolean){
                println(it.asJsonObject.keySet())
                return it.asJsonObject["profile_id"].asString
            }
        }
        return ""
    }

    fun getGuildMembersPlayer(name: String):List<GuildReply.Guild.Member>{
        return hypixelAPI.getGuildByPlayer(name).get().guild.members
    }

    fun getGuildID(name: String): String{
        return hypixelAPI.getGuildByName(name).get().guild.id
    }
    fun getGuildIDPlayer(name: String): String{
        return hypixelAPI.getGuildByPlayer(name).get().guild.id
    }

    fun getGuild(name: String): String{
        return hypixelAPI.getGuildByPlayer(getMinecraftUUID(name)).get().guild.name
    }
    fun getLinkedDiscord(name: String): String {
        return hypixelAPI.getPlayerByUuid(name).get().player.getObjectProperty("socialMedia")["links"].asJsonObject["DISCORD"].asString
    }

}