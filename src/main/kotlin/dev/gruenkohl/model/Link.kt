package dev.gruenkohl.model

import dev.gruenkohl.hypixel.ApiHandler
import dev.gruenkohl.hypixel.ExpApi
import dev.gruenkohl.sql.LinkManager
import kotlinx.serialization.Serializable


@Serializable
data class SettingModifier(
    val discordId: Long,
    val modifiedSetting: String
)


@Serializable
data class Link(
    val discordId: Long,
    val uuid: String,
    val discordName: String? = null,
    val settings: Settings = Settings(profileID = ApiHandler().getSelectedProfile(uuid))
)
@Serializable
data class Settings(
    val track: Boolean = false,
    val pestGain: Boolean = false,
    val collectionGain: Boolean = false,
    val uptime: Boolean = true,
    val profileID: String
)

fun modifySetting(link: Link, settingName: String): Link {
    val newSettings = when (settingName) {
        "track" -> link.settings.copy(track = !link.settings.track)
        "pestGain" -> link.settings.copy(pestGain = !link.settings.pestGain)
        "collectionGain" -> link.settings.copy(collectionGain = !link.settings.collectionGain)
        "uptime" -> link.settings.copy(uptime = !link.settings.uptime)
        else -> throw IllegalArgumentException("Invalid setting name: $settingName")
    }
    return link.copy(settings = newSettings)
}

fun getLink(discordId: Long): Link{
    val manager = LinkManager()
    return manager.getLink(discordId)
}

fun addLink(link: Link): Boolean{
    val api = ExpApi()
        if (api.getLinkedDiscord(link.uuid).lowercase() == link.discordName?.lowercase()){
            val manager = LinkManager()
            manager.addLink(link)
            return true
        }
    return false
}