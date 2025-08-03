package net.bielsko.chwileczke.workingHours

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.bielsko.chwileczke.R
import net.bielsko.chwileczke.ui.fields.Header1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkingHoursDisplay() {
    val days = listOf(
        R.string.monday,
        R.string.tuesday,
        R.string.wednesday,
        R.string.thursday,
        R.string.friday,
        R.string.saturday,
        R.string.sunday
    )

    Header1(stringResource(id = R.string.working_hours))

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val schedule = remember { mutableStateListOf<WorkingHoursState>() }

    // Załaduj stany z DataStore jednorazowo
    LaunchedEffect(Unit) {
        schedule.clear()
        days.forEach { dayResId ->
            val dayState = WorkingHoursPrefs.getWorkingHoursOnce(context, dayResId)
            schedule.add(dayState)
        }
    }

    schedule.forEachIndexed { index, state ->
        WorkingHoursForm(
            workingHoursState = state,
            onChange = { updated ->
                schedule[index] = updated
                scope.launch {
                    WorkingHoursPrefs.setWorkingHours(context, updated)
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    DailyState(schedule)
    WeeklySummary(schedule)
}
