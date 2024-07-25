package dev.gruenkohl.model.weights


import dev.gruenkohl.model.Pest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Fly(
    @SerialName("0")
    val x0: Double,
    @SerialName("100")
    val x100: Double,
    @SerialName("1000")
    val x1000: Double,
    @SerialName("250")
    val x250: Double,
    @SerialName("50")
    val x50: Double,
    @SerialName("500")
    val x500: Double,
    @SerialName("750")
    val x750: Double
): Pest {
    override fun getValueForThreshold(threshold: Int): Double {
        return when (threshold) {
            0 -> x0
            50 -> x50
            100 -> x100
            250 -> x250
            500 -> x500
            750 -> x750
            1000 -> x1000
            else -> 0.0 // or throw an exception if an invalid threshold is given
        }
    }
}