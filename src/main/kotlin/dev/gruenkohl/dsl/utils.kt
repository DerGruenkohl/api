package dev.gruenkohl.dsl

import dev.gruenkohl.model.expHistory
import dev.gruenkohl.model.time
import kong.unirest.json.JSONObject
import net.hypixel.api.reply.GuildReply
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.net.URL
import java.time.LocalDate
import java.time.ZoneId
import kotlin.math.floor

@Throws(IOException::class)
fun getMinecraftUsername(uuid: String): String {
    val url = URL("https://sessionserver.mojang.com/session/minecraft/profile/$uuid")
    val input = url.openConnection().getInputStream()
    val jsonText = IOUtils.toString(input, "UTF-8")
    val json = JSONObject(jsonText)
    return json.getString("name")
}

@Throws(IOException::class)
fun getMinecraftUUID(name: String): String {
    val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")
    val input = url.openConnection().getInputStream()
    val jsonText = IOUtils.toString(input, "UTF-8")
    val json = JSONObject(jsonText)
    return json.getString("id")
}
fun GuildReply.Guild.Member.getExpHistory(): LinkedHashMap<Long, Int>{
    val map = LinkedHashMap<Long, Int>()
    for (i in 0..6){
        val date = LocalDate.now(ZoneId.of("America/New_York")).minusDays(i.toLong())
        val timestamp = date.atStartOfDay(ZoneId.of("America/New_York")).toLocalDate().toEpochDay()
        map[timestamp] = this.getExperienceEarned(date)
    }
    return map
}
fun GuildReply.Guild.Member.getFarmingUptime(): expHistory{
    val map = this.getExpHistory()
    val newMap = expHistory()
    map.forEach {
        val hours = it.value/9000f
        val hoursInt = floor(hours).toInt()
        val minutes = ((hours - hoursInt) * 60).toInt()
        val pair = time(hoursInt, minutes)
        newMap[it.key] = pair
    }
    return newMap
}