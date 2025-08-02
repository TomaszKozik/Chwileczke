package net.bielsko.chwileczke.ui.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

data class WorkingHoursState(
    val day: Int,
    val opened: Boolean = true,
    val wholeDay: Boolean = false,
    val from: String = "08:00",
    val to: String = "16:00"
)

@RequiresApi(Build.VERSION_CODES.O)
fun isValidTimeFormat(time: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return try {
        val parsed = LocalTime.parse(time, formatter)
        // dodatkowo możesz wymusić zakres jeśli chcesz, np. od 00:00 do 23:59
        true
    } catch (e: DateTimeParseException) {
        false
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkingHoursForm() {
    val days = listOf(
        R.string.monday,
        R.string.tuesday,
        R.string.wednesday,
        R.string.thursday,
        R.string.friday,
        R.string.saturday,
        R.string.sunday
    )

    val schedule = remember {
        mutableStateListOf<WorkingHoursState>().apply {
            days.forEach { day ->
                add(WorkingHoursState(day = day, opened = true))
            }
        }
    }

    Text(
        stringResource(id = R.string.working_hours),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    schedule.forEachIndexed { index, state ->
        WorkingHours(
            state = state,
            onChange = { updated -> schedule[index] = updated }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    WeeklyMessage(schedule)
    WeeklySummary(schedule)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkingHours(
    state: WorkingHoursState,
    onChange: (WorkingHoursState) -> Unit
) {
    var opened by remember { mutableStateOf(state.opened) }
    var wholeDay by remember { mutableStateOf(state.wholeDay) }
    var from by remember { mutableStateOf(state.from) }
    var to by remember { mutableStateOf(state.to) }

    val day = stringResource(id = state.day)
    val primaryBlue = colorResource(id = R.color.primary_blue)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .drawBehind {
                val lineHeight = size.height * 0.9f
                drawLine(
                    color = primaryBlue,
                    start = Offset(0f, size.height * 0.1f),
                    end = Offset(0f, lineHeight),
                    strokeWidth = 2.dp.toPx()
                )
            }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = opened,
                onCheckedChange = {
                    opened = it
                    onChange(state.copy(opened = opened, wholeDay = wholeDay, from = from, to = to))
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = primaryBlue,
                    uncheckedColor = colorResource(id = R.color.gray_text),
                    checkmarkColor = Color.White
                )
            )
            Text("$day: ${if (opened) stringResource(R.string.opened) else stringResource(R.string.closed)}")
        }

        if (opened) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = wholeDay,
                    onCheckedChange = {
                        wholeDay = it
                        onChange(state.copy(opened = opened, wholeDay = wholeDay, from = from, to = to))
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = primaryBlue,
                        uncheckedColor = colorResource(id = R.color.gray_text),
                        checkmarkColor = Color.White
                    )
                )
                Text(
                    if (wholeDay) stringResource(id = R.string.whole_day)
                    else stringResource(id = R.string.open_hours),
                    modifier = Modifier.padding(end = 8.dp)
                )
                if (!wholeDay) {
                    var fromInput by remember { mutableStateOf(from) }
                    var toInput by remember { mutableStateOf(to) }
                    OutlinedTextField(
                        value = fromInput,
                        onValueChange = { newValue ->
                            if (newValue.length <= 5 && newValue.all { it.isDigit() || it == ':' }) {
                                fromInput = newValue
                            }
                        },
                        modifier = Modifier
                            .width(80.dp)
                            .height(55.dp)
                            .onFocusChanged { focusState: FocusState ->
                                if (!focusState.isFocused) {
                                    // Utracono fokus, waliduj i ewentualnie resetuj
                                    if (isValidTimeFormat(fromInput)) {
                                        val parsedFrom = parseTime(fromInput)
                                        val parsedTo = parseTime(toInput)
                                        if (parsedFrom != null && parsedTo != null && parsedFrom <= parsedTo) {
                                            from = fromInput
                                        } else {
                                            // jeśli zła kolejność, reset do domyślnej lub ustaw na to
                                            fromInput = "08:00"
                                            from = fromInput
                                        }
                                    } else {
                                        fromInput = "08:00"
                                        from = fromInput
                                    }
                                    onChange(state.copy(opened = opened, wholeDay = wholeDay, from = from, to = to))
                                }
                            },
                        label = { Text(stringResource(id = R.string.from)) },
                        singleLine = true
                    )
                    Text("–", modifier = Modifier.padding(horizontal = 4.dp))
                    OutlinedTextField(
                        value = toInput,
                        onValueChange = { newValue ->
                            if (newValue.length <= 5 && newValue.all { it.isDigit() || it == ':' }) {
                                toInput = newValue
                            }
                        },
                        modifier = Modifier
                            .width(80.dp)
                            .height(55.dp)
                            .onFocusChanged { focusState ->
                                if (!focusState.isFocused) {
                                    if (isValidTimeFormat(toInput)) {
                                        val parsedFrom = parseTime(fromInput)
                                        val parsedTo = parseTime(toInput)
                                        if (parsedFrom != null && parsedTo != null && parsedTo >= parsedFrom) {
                                            to = toInput
                                        } else {
                                            // jeśli zła kolejność, reset do domyślnej lub ustaw na from
                                            toInput = "16:00"
                                            to = toInput
                                        }
                                    } else {
                                        toInput = "16:00"
                                        to = toInput
                                    }
                                    onChange(state.copy(opened = opened, wholeDay = wholeDay, from = from, to = to))
                                }
                            },
                        label = { Text(stringResource(id = R.string.to)) },
                        singleLine = true
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyMessage(schedule: List<WorkingHoursState>) {
    val now = LocalDate.now()
    val currentTime = java.time.LocalTime.now()
    val todayIndex = now.dayOfWeek.value // Poniedziałek = 1, Niedziela = 7

    val todayState = schedule.getOrNull(todayIndex - 1)

    if (todayState != null && todayState.opened) {
        val showGreeting = todayState.wholeDay || runCatching {
            val fromTime = java.time.LocalTime.parse(todayState.from)
            val toTime = java.time.LocalTime.parse(todayState.to)
            currentTime.isAfter(fromTime) && currentTime.isBefore(toTime)
        }.getOrDefault(false)

        if (showGreeting) {
            Text(
                text = "Dziś jest dobry dzień 😊",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.primary_blue),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}



@Composable
fun WeeklySummary(schedule: List<WorkingHoursState>) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = "Podsumowanie tygodnia:",
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.primary_blue),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        schedule.forEach { day ->
            val dayName = stringResource(id = day.day)
            val status = when {
                !day.opened -> "Zamknięte"
                day.wholeDay -> "Całodobowo"
                else -> "${day.from} – ${day.to}"
            }

            Text(
                text = "$dayName: $status",
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

fun parseTime(time: String): LocalTime? = try {
    LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
} catch (e: Exception) {
    null
}
