package dev.gruenkohl.model

interface Pest {
    fun getValueForThreshold(threshold: Int): Double
}