package dev.gruenkohl.model

import dev.gruenkohl.dsl.getFarmingUptime
import dev.gruenkohl.hypixel.ExpApi
import kotlinx.serialization.Serializable
import net.hypixel.api.reply.GuildReply

@Serializable
data class Guild(
    val guildID: String,
    val members: List<Member>
)
@Serializable
data class Member(
    val uuid: String,
    val expHistory: expHistory
)
typealias expHistory = LinkedHashMap<Long, time>
@Serializable
data class time(
    val hours: Int,
    val mins: Int
) : Comparable<time> {
    override fun compareTo(other: time): Int {
        return compareValuesBy(this, other, time::hours, time::mins)
    }
}

fun getGuild(guildName: String): Guild {
    val api = ExpApi()
    val id = api.getGuildID(guildName)
    api.getExpHistory(id)
    return createGuild(api.getGuildMembers(guildName), id)
}
fun getMember(guildName: String): Member? {
    val api = ExpApi()
    val id = api.getGuildIDPlayer(guildName)
    val guild = createGuild(api.getGuildMembersPlayer(guildName), id)
    api.getExpHistory(id)
    guild.members.forEach {
        if (it.uuid.replace("-","") == guildName){
            return it
        }
    }
    return null
}

fun createGuild(members: List<GuildReply.Guild.Member>, guildID: String): Guild{
    val newMembers = mutableListOf<Member>()
    members.forEach {
        newMembers.add(
            Member(
                it.uuid.toString(),
                it.getFarmingUptime()
            )
        )
    }
    return Guild(
        guildID,
        newMembers
    )
}