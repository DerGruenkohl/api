package dev.gruenkohl.model

import dev.gruenkohl.sql.UptimeSQLManager
import kotlinx.serialization.Serializable

@Serializable
data class Leaderboard(
    val size: Int,
    val members: Map<String, time>
)

fun buildLeaderboard(): Leaderboard{
    val lb = dev.gruenkohl.feature.Leaderboard()
    val manager = UptimeSQLManager()
    val members = mutableListOf<Member>()
    manager.getAllSavedGuilds().forEach {
        members.addAll(it.members)
    }
    return Leaderboard(members.size,lb.createLeaderboardMap(members))
}