package dev.gruenkohl.hypixel

import dev.gruenkohl.dsl.getMinecraftUUID
import dev.gruenkohl.model.Guild
import dev.gruenkohl.model.createGuild
import dev.gruenkohl.sql.UptimeSQLManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime


class ExpApi : ApiHandler() {

    fun getGuildIdFromUsername(name: String): String{
        return hypixelAPI.getGuildByPlayer(getMinecraftUUID(name)).get().guild.id
    }

    fun getExpHistory(id: String): Guild {
        return Json.decodeFromString<Guild>(getExpHistoryJson(id))
    }
    private fun getExpHistoryJson(id: String): String{
        val manager = UptimeSQLManager()
        if (manager.isGuildSaved(id)){
            val dtbexp = manager.getExpFromDtb(id)
            if (dtbexp.second.plusHours(6).isBefore(LocalDateTime.now())){
                manager.addOrUpdateGuild(id)
            }
            else{
                return dtbexp.first
            }

        }else{
            manager.addOrUpdateGuild(id)
        }
        return getExpHistoryFromApi(id)

    }

    fun getExpHistoryFromApi(id: String): String{
        println("apiHistory call")
        hypixelAPI.getGuildById(id).get().guild.let {
            println(it.name)
            val guild = createGuild(it.members, it.id)
            return Json.encodeToString(guild)
        }
    }
}