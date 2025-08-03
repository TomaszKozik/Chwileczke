package net.bielsko.chwileczke.workingHours

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R
import net.bielsko.chwileczke.ui.fields.TimePickerField

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkingHoursForm(
    workingHoursState: WorkingHoursState,
    onChange: (WorkingHoursState) -> Unit
) {
    val primaryBlue = colorResource(id = R.color.primary_blue)
    val dayName = stringResource(id = workingHoursState.day)

    var opened by remember { mutableStateOf(workingHoursState.opened) }
    var wholeDay by remember { mutableStateOf(workingHoursState.wholeDay) }
    var fromTime by remember { mutableStateOf(workingHoursState.from) }
    var toTime by remember { mutableStateOf(workingHoursState.to) }

    fun timeToMinutes(time: String): Int? = try {
        val parts = time.split(":")
        parts[0].toInt() * 60 + parts[1].toInt()
    } catch (e: Exception) {
        null
    }

    LaunchedEffect(workingHoursState) {
        if (workingHoursState.opened != opened) opened = workingHoursState.opened
        if (workingHoursState.wholeDay != wholeDay) wholeDay = workingHoursState.wholeDay
        if (workingHoursState.from != fromTime) fromTime = workingHoursState.from
        if (workingHoursState.to != toTime) toTime = workingHoursState.to
    }

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
                    onChange(
                        workingHoursState.copy(
                            opened = opened,
                            wholeDay = wholeDay,
                            from = fromTime,
                            to = toTime
                        )
                    )
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = primaryBlue,
                    uncheckedColor = colorResource(id = R.color.gray_text),
                    checkmarkColor = Color.White
                )
            )
            Text("$dayName: ${if (opened) stringResource(R.string.opened) else stringResource(R.string.closed)}")
        }

        if (opened) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = wholeDay,
                    onCheckedChange = {
                        wholeDay = it
                        onChange(
                            workingHoursState.copy(
                                opened = opened,
                                wholeDay = wholeDay,
                                from = fromTime,
                                to = toTime
                            )
                        )
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = primaryBlue,
                        uncheckedColor = colorResource(id = R.color.gray_text),
                        checkmarkColor = Color.White
                    )
                )
                Text(
                    if (wholeDay) stringResource(id = R.string.whole_day)
                    else stringResource(id = R.string.open_hours)
                )

                if (!wholeDay) {
                    Spacer(modifier = Modifier.width(8.dp))

                    TimePickerField(
                        label = stringResource(id = R.string.from),
                        time = fromTime,
                        minTimeMinutes = null,
                        maxTimeMinutes = timeToMinutes(toTime),
                        modifier = Modifier
                            .weight(1f)
                            .height(70.dp),
                        onTimeChange = { newFrom ->
                            val newFromMinutes = timeToMinutes(newFrom)
                            val toMinutes = timeToMinutes(toTime)
                            val orderValid =
                                if (newFromMinutes != null && toMinutes != null) newFromMinutes < toMinutes else true
                            fromTime = newFrom
                            if (orderValid) {
                                onChange(
                                    workingHoursState.copy(
                                        opened = opened,
                                        wholeDay = wholeDay,
                                        from = newFrom,
                                        to = toTime
                                    )
                                )
                            }
                        }
                    )
                    Text("–", modifier = Modifier.padding(horizontal = 4.dp))
                    TimePickerField(
                        label = stringResource(id = R.string.to),
                        time = toTime,
                        minTimeMinutes = timeToMinutes(fromTime),
                        maxTimeMinutes = null,
                        modifier = Modifier
                            .weight(1f)
                            .height(70.dp),
                        onTimeChange = { newTo ->
                            val fromMinutes = timeToMinutes(fromTime)
                            val newToMinutes = timeToMinutes(newTo)
                            val orderValid =
                                if (fromMinutes != null && newToMinutes != null) fromMinutes < newToMinutes else true
                            toTime = newTo
                            if (orderValid) {
                                onChange(
                                    workingHoursState.copy(
                                        opened = opened,
                                        wholeDay = wholeDay,
                                        from = fromTime,
                                        to = newTo
                                    )
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}