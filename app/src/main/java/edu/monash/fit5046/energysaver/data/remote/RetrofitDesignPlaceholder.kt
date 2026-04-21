package edu.monash.fit5046.energysaver.data.remote

data class WeatherContextDto(
    val suburb: String,
    val temperatureCelsius: Double,
    val condition: String,
    val retrievedAt: String
)

data class GridPriceDto(
    val pricePerKwh: Double,
    val demandLevel: String,
    val retrievedAt: String
)

interface EnergyContextApiPlan {
    fun getWeatherContext(suburb: String): WeatherContextDto
    fun getGridPriceContext(postcode: String): GridPriceDto
}
