package net.bielsko.chwileczke.workingHours

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R

@Composable
fun WeeklySummary(schedule: List<WorkingHoursState>) {
    val closed = stringResource(id = R.string.closed)
    val wholeDay = stringResource(id = R.string.whole_day).lowercase()

    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = stringResource(id = R.string.weekly_summary),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.primary_blue),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        schedule.forEach { day ->
            val dayName = stringResource(id = day.day)
            val status = when {
                !day.opened -> closed
                day.wholeDay -> wholeDay
                else -> "${day.from} – ${day.to}"
            }

            Text(
                text = "$dayName: $status",
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}