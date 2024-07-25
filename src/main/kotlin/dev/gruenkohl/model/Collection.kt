package dev.gruenkohl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Collections(
    @SerialName("CARROT_ITEM")
    val carrot: Long,
    @SerialName("CACTUS")
    val cactus: Long,
    @SerialName("SUGAR_CANE")
    val cane: Long,
    @SerialName("PUMPKIN")
    val pumpkin: Long,
    @SerialName("WHEAT")
    val wheat: Long,
    @SerialName("SEEDS")
    val seeds: Long,
    @SerialName("MUSHROOM_COLLECTION")
    val mushroom: Long,
    @SerialName("NETHER_STALK")
    val wart: Long,
    @SerialName("MELON")
    val melon: Long,
    @SerialName("POTATO_ITEM")
    val potato: Long
)
