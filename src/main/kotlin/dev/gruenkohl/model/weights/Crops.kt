package dev.gruenkohl.model.weights


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crops(
    @SerialName("Cactus")
    val cactus: Double,
    @SerialName("Carrot")
    val carrot: Double,
    @SerialName("Cocoa Beans")
    val cocoaBeans: Double,
    @SerialName("Melon")
    val melon: Double,
    @SerialName("Mushroom")
    val mushroom: Double,
    @SerialName("Nether Wart")
    val netherWart: Double,
    @SerialName("Potato")
    val potato: Double,
    @SerialName("Pumpkin")
    val pumpkin: Double,
    @SerialName("Sugar Cane")
    val sugarCane: Double,
    @SerialName("Wheat")
    val wheat: Double
)