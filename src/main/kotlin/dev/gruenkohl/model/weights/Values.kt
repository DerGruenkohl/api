package dev.gruenkohl.model.weights


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Values(
    @SerialName("beetle")
    val beetle: Beetle,
    @SerialName("cricket")
    val cricket: Cricket,
    @SerialName("earthworm")
    val earthworm: Earthworm,
    @SerialName("fly")
    val fly: Fly,
    @SerialName("locust")
    val locust: Locust,
    @SerialName("mite")
    val mite: Mite,
    @SerialName("mosquito")
    val mosquito: Mosquito,
    @SerialName("moth")
    val moth: Moth,
    @SerialName("rat")
    val rat: Rat,
    @SerialName("slug")
    val slug: Slug
)