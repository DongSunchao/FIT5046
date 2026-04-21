package edu.monash.fit5046.energysaver.data.local

data class EnergyUsageEntity(
    val id: Int,
    val applianceName: String,
    val category: String,
    val usageKwh: Double,
    val carbonKg: Double,
    val recordedDate: String
)

interface EnergyUsageDaoPlan {
    fun searchUsageRecords(query: String): List<EnergyUsageEntity>
    fun insertUsageRecord(record: EnergyUsageEntity)
    fun updateUsageRecord(record: EnergyUsageEntity)
    fun deleteUsageRecord(recordId: Int)
}

object RoomDatabasePlan {
    const val databaseName = "energy_saver.db"
    val plannedTables = listOf("energy_usage", "household_profile", "context_sample")
}
