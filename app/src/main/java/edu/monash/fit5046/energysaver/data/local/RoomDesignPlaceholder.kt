package edu.monash.fit5046.energysaver.data.local

data class EnergyUsageRecordPlan(
    val id: Int,
    val applianceName: String,
    val category: String,
    val usageKwh: Double,
    val carbonKg: Double,
    val recordedDate: String
)

interface EnergyUsageRepositoryPlan {
    fun searchUsageRecords(query: String): List<EnergyUsageRecordPlan>
    fun insertUsageRecord(record: EnergyUsageRecordPlan)
    fun updateUsageRecord(record: EnergyUsageRecordPlan)
    fun deleteUsageRecord(recordId: Int)
}

object RoomDatabasePlan {
    const val databaseName = "energy_saver.db"
    val plannedTables = listOf("energy_usage", "household_profile", "context_sample")
}
