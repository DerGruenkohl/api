package dev.gruenkohl.model

import kotlinx.serialization.Serializable

@Serializable
data class PestBE(
    val pest_cricket_1: Int,
    val pest_mite_1: Int,
    val pest_slug_1: Int,
    val pest_moth_1: Int,
    val pest_worm_1: Int,
    val pest_mosquito_1: Int,
    val pest_fly_1: Int,
    val pest_locust_1: Int,
    val pest_beetle_1: Int,
    val pest_rat_1: Int,
)
