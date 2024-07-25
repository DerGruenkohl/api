package dev.gruenkohl.feature

import dev.gruenkohl.hypixel.TrackApi
import dev.gruenkohl.model.*
import dev.gruenkohl.sql.TrackSQLManager

class TrackManager(private val link : Link) {
    private val manager = TrackSQLManager(link)
    private fun collectPests(): List<PestGain>{
        val pests = mutableListOf<PestGain>()
        val man = PestManager(link)
        val pestData = man.getPestData()
        pestData.forEach{
            pests.add(
                PestGain(
                    pestName = it.key,
                    collection = it.value
                )
            )
        }
        return pests
    }
    private fun getCollections(): Collections{
        val trackApi = TrackApi()
        return trackApi.getCollections(link.settings.profileID, link.uuid)
    }
    private fun getUptime(): Member?{
        return getMember(link.uuid)
    }
    fun buildTracker(){
        val data = mutableListOf(
            TrackedData(
                timeStamp = System.currentTimeMillis(),
                pest = collectPests(),
                collections = getCollections(),
                uptime = getUptime()
            )
        )
        if (manager.checkTrack()){
            data.addAll(manager.getTrack().data)
        }

        val track = Tracking(
            uuid = link.uuid,
            data = data
        )
        manager.addTrack(track)
    }
    fun getTrack(): Tracking{
        return manager.getTrack()
    }
}