package dev.gruenkohl.model.weights


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Brackets(
    @SerialName("0")
    val x0: Int,
    @SerialName("100")
    val x100: Int,
    @SerialName("1000")
    val x1000: Int,
    @SerialName("250")
    val x250: Int,
    @SerialName("50")
    val x50: Int,
    @SerialName("500")
    val x500: Int,
    @SerialName("750")
    val x750: Int
)
fun Brackets.toList(): List<Pair<String, Int>> {
    return listOf(
        "x0" to x0,
        "x50" to x50,
        "x100" to x100,
        "x250" to x250,
        "x500" to x500,
        "x750" to x750,
        "x1000" to x1000
    )
}