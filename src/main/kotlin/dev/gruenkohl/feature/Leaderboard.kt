package dev.gruenkohl.feature

import dev.gruenkohl.model.Member
import dev.gruenkohl.model.time
import kotlin.math.floor

class Leaderboard {
    fun createLeaderboardMap(list: List<Member>): Map<String, time> {
        val expAverageMap = HashMap<String, time>()
        list.forEach { member: Member ->
            val exp = member.expHistory
            var mins = 0
            var hours = 0
            exp.forEach {
                mins += it.value.mins
                hours += it.value.hours
            }
            val avghrs = (hours + mins/60f)/7
            val hoursInt = floor(avghrs).toInt()
            val minutes = ((avghrs - hoursInt) * 60).toInt()
            expAverageMap[member.uuid] = time(
                mins = minutes,
                hours = hoursInt
            )

        }
        return expAverageMap.toList()
            .sortedByDescending { (_, value) -> value }
            .toMap()
    }
}