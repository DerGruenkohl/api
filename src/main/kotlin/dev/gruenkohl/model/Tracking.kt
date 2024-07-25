package dev.gruenkohl.model

import dev.gruenkohl.feature.TrackManager
import dev.gruenkohl.sql.LinkManager
import io.ktor.util.logging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import util.DiscordWebhook
import utils.dsl.scheduleRepeating
import java.util.concurrent.TimeUnit

internal val LOGGER = KtorSimpleLogger("dev.gruenkohl.Tracking")
@Serializable
data class Tracking(
    val uuid: String,
    val data: List<TrackedData>
)
@Serializable
data class TrackedData(
    val timeStamp: Long,
    val pest: List<PestGain>,
    val collections: Collections,
    val uptime: Member? = null
)
@Serializable
data class PestGain(
    val pestName: String,
    val collection: Long,
)

fun startTracking() {
    val scope = CoroutineScope(Dispatchers.Default)

    scheduleRepeating(0, 12, TimeUnit.HOURS){
        val linkManager = LinkManager()
        LOGGER.info("Tracking")
        scope.launch {
            val hook = DiscordWebhook("https://discord.com/api/webhooks/1264254783116673075/M-r8TtWMcQkfBrf4CyVIxe7DlPfTvhGvH23olAo5gTEHs6z1xyU89JYiAICV-w1WW0aM")
            hook.setContent("tracking now")
            hook.execute()
            linkManager.getAllLinks().map {
                val man = TrackManager(it)
                if (it.settings.track){
                    man.buildTracker()
                }

            }
        }

    }
}
