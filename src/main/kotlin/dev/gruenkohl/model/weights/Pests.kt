package dev.gruenkohl.model.weights


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pests(
    @SerialName("brackets")
    val brackets: Brackets,
    @SerialName("values")
    val values: Values
)