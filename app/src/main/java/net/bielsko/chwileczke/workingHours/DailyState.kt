package net.bielsko.chwileczke.workingHours

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyState(schedule: List<WorkingHoursState>) {
    val now = LocalDate.now()
    val currentTime = java.time.LocalTime.now()
    val todayIndex = now.dayOfWeek.value // Poniedziałek = 1, Niedziela = 7

    val todayState = schedule.getOrNull(todayIndex - 1)

    if (todayState != null && todayState.opened) {
        val isWorkingNow = todayState.wholeDay || runCatching {
            val fromTime = java.time.LocalTime.parse(todayState.from)
            val toTime = java.time.LocalTime.parse(todayState.to)
            currentTime.isAfter(fromTime) && currentTime.isBefore(toTime)
        }.getOrDefault(false)

        if (isWorkingNow) {
            Text(
                text = "Jak Ci się pracuje? 😊",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.primary_blue),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}