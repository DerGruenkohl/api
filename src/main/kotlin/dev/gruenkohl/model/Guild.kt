package dev.gruenkohl.model

import dev.gruenkohl.dsl.getExpHistory
import kotlinx.serialization.Serializable
import net.hypixel.api.reply.GuildReply


enum class Priority {
    Low, Medium, High, Vital
}

fun transformGuildMember(members: List<GuildReply.Guild.Member>): MutableList<GuildEntry> {
    val entrys = mutableListOf<GuildEntry>()
    members.forEach {
        entrys.add(GuildEntry(
            it.uuid.toString(),
            it.rank,
            it.joinDate.toEpochSecond(),
            it.getExpHistory()
        ))
    }
    return entrys
}
@Serializable
data class Task(
    val name: String,
    val description: String,
    val priority: Priority
)
@Serializable
data class GuildEntry(
    val uuid: String,
    val rank: String,
    val joined: Long,
    val expHistory: LinkedHashMap<Long, Int>
)
