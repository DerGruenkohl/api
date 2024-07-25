package dev.gruenkohl.feature

import dev.gruenkohl.hypixel.TrackApi
import dev.gruenkohl.hypixel.WeightManager
import dev.gruenkohl.model.Link
import dev.gruenkohl.model.Pest
import dev.gruenkohl.model.PestBE
import kotlin.math.roundToLong
import kotlin.reflect.full.declaredMemberProperties

class PestManager(link: Link) {
    private val weights = WeightManager.weights.pests.values
    private val pests = TrackApi().getPestBe(link.settings.profileID, link.uuid)

    private fun processPestData(pestBE: PestBE, pestData: Map<String, Pest>): Map<String, Long> {
        val pests = mutableMapOf<String, Long>()
        pestBE::class.declaredMemberProperties.forEach { prop ->
            val pestValue = prop.getter.call(pestBE) as Int
            val pestName = prop.name.removePrefix("pest_")
            val pest = pestData[pestName]!!
            val progressiveSum = calculateSum(pest, pestValue)
            //println("Pest: $pestName, Value: $pestValue, Progressive Sum: $progressiveSum")
            pests[pestName] = progressiveSum
        }
        return pests
    }

    private fun calculateSum(pest: Pest, kills: Int): Long {
        var sum = 0.0
        var counter = 0
        while (kills > counter){
            counter++
            if (counter > 1000){sum += pest.getValueForThreshold(1000);}
            else if (counter > 750){sum += pest.getValueForThreshold(750)}
            else if (counter > 500){sum += pest.getValueForThreshold(500)}
            else if (counter > 250){sum += pest.getValueForThreshold(250)}
            else if (counter > 100){sum += pest.getValueForThreshold(100)}
            else if (counter > 50){sum += pest.getValueForThreshold(50)}
        }
        return sum.roundToLong()
    }
    fun getPestData(): Map<String, Long>{
        val pestDataMap = mapOf(
            "cricket_1" to weights.cricket,
            "mite_1" to weights.mite,
            "slug_1" to weights.slug,
            "moth_1" to weights.moth,
            "worm_1" to weights.earthworm,
            "mosquito_1" to weights.mosquito,
            "fly_1" to weights.fly,
            "locust_1" to weights.locust,
            "beetle_1" to weights.beetle,
            "rat_1" to weights.rat
            // Add other pest data instances to the map
        )
        return processPestData(pests, pestDataMap)
    }
}