package dev.gruenkohl.hypixel

import dev.gruenkohl.model.Collections
import dev.gruenkohl.model.PestBE
import kotlinx.serialization.json.Json

class TrackApi: ApiHandler() {

    fun getPestBe(profileId: String, uuid: String): PestBE{
       val kills = hypixelAPI.getSkyBlockProfile(profileId).get()
            .profile["members"]
            .asJsonObject[uuid.replace("-", "")]
            .asJsonObject["bestiary"]
            .asJsonObject["kills"]
           .toString()
        val json = Json{
            ignoreUnknownKeys = true
        }
        return json.decodeFromString<PestBE>(kills)
    }
    fun getCollections(profileId: String, uuid: String): Collections {
        val collections = hypixelAPI.getSkyBlockProfile(profileId).get()
            .profile["members"]
            .asJsonObject[uuid.replace("-", "")]
            .asJsonObject["collection"]
            .toString()
        val json = Json{
            ignoreUnknownKeys = true
        }
        return json.decodeFromString<Collections>(collections)
    }
}