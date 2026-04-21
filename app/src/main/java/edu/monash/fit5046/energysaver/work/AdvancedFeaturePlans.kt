package edu.monash.fit5046.energysaver.work

object WorkManagerFeaturePlan {
    const val workerName = "ContextSensorSyncWorker"
    const val purpose = "Periodically import simulated smart meter readings and API context."
    val inputs = listOf("public smart meter dataset", "weather context", "grid price context")
}

object AlarmManagerFeaturePlan {
    const val alarmName = "DailyEnergyReminderAlarm"
    const val purpose = "Schedule in-app or notification reminders for peak-rate and goal-risk moments."
    val triggers = listOf("daily reminder time", "usage exceeds goal", "peak tariff window")
}
