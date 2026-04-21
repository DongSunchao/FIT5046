package edu.monash.fit5046.energysaver.simulator

import android.content.Context
import kotlin.random.Random

class EnergySensorSimulator(private val context: Context) {

    private val sampleRows = listOf(
        EnergySensorSample("07:00", 1.8, 18.5, "off-peak"),
        EnergySensorSample("13:00", 3.6, 32.0, "shoulder"),
        EnergySensorSample("18:30", 5.4, 29.5, "peak"),
        EnergySensorSample("22:00", 2.1, 21.0, "off-peak")
    )

    fun nextSample(): EnergySensorSample {
        return sampleRows[Random.nextInt(sampleRows.size)]
    }

    fun recommendationFor(sample: EnergySensorSample, householdGoalKwh: Double): String {
        return when {
            sample.gridTariff == "peak" && sample.usageKwh > householdGoalKwh -> {
                "Shift flexible appliance use away from peak tariff and enable Eco Mode."
            }
            sample.outdoorTemperatureCelsius >= 30 -> {
                "Use cooling only in occupied rooms and raise AC target temperature by 1-2°C."
            }
            else -> {
                "Current context is within target range; keep monitoring usage."
            }
        }
    }
}

data class EnergySensorSample(
    val time: String,
    val usageKwh: Double,
    val outdoorTemperatureCelsius: Double,
    val gridTariff: String
)
