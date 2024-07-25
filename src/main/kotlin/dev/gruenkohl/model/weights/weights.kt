package dev.gruenkohl.model.weights


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class weights(
    @SerialName("crops")
    val crops: Crops,
    @SerialName("pests")
    val pests: Pests
)