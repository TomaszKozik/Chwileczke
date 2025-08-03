package net.bielsko.chwileczke.workingHours

const val DEFAULT_START_HOUR = "08:00"
const val DEFAULT_END_HOUR = "16:00"

data class WorkingHoursState(
    val day: Int,
    val opened: Boolean = true,
    val wholeDay: Boolean = false,
    val from: String = DEFAULT_START_HOUR,
    val to: String = DEFAULT_END_HOUR
)