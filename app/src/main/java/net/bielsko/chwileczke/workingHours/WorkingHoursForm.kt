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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R
import net.bielsko.chwileczke.helpers.isValidTimeFormat
import net.bielsko.chwileczke.ui.fields.MyTextField
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
    var fromInput = remember { mutableStateOf(workingHoursState.from) }
    var toInput = remember { mutableStateOf(workingHoursState.to) }

    val isFromValid = isValidTimeFormat(fromInput.value)
    val isToValid = isValidTimeFormat(toInput.value)
    val isOrderValid = if (isFromValid && isToValid) {
        parseTime(fromInput.value)?.isBefore(parseTime(toInput.value)) ?: true
    } else true

    LaunchedEffect(workingHoursState) {
        if (workingHoursState.opened != opened) opened = workingHoursState.opened
        if (workingHoursState.wholeDay != wholeDay) wholeDay = workingHoursState.wholeDay
        if (workingHoursState.from != fromInput.value) fromInput.value = workingHoursState.from
        if (workingHoursState.to != toInput.value) toInput.value = workingHoursState.to
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .drawBehind {
                val lineHeight = size.height * 0.9f
                drawLine(
                    color = primaryBlue,
                    start = Offset(0f, 0f),
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
                            from = fromInput.value,
                            to = toInput.value
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
                                from = fromInput.value,
                                to = toInput.value
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
            }
            if (!wholeDay) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    MyTextField(
                        labelText = stringResource(id = R.string.from),
                        textState = fromInput,
                        errorText = when {
                            !isFromValid -> stringResource(R.string.error_incorrect_hour)
                            !isOrderValid -> stringResource(R.string.error_incorrect_order_hour)
                            else -> null
                        },
                        validator = { isValidTimeFormat(it) && isOrderValid },
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp),
                        onValueChange = { newValue ->
                            fromInput.value = newValue
                            if (isValidTimeFormat(newValue) && isOrderValid) {
                                onChange(
                                    workingHoursState.copy(
                                        opened = opened,
                                        wholeDay = wholeDay,
                                        from = newValue,
                                        to = toInput.value
                                    )
                                )
                            }
                        },
                        readOnly = false,
                        enabled = true
                    )
                    Text("–", modifier = Modifier.padding(horizontal = 4.dp))
                    MyTextField(
                        labelText = stringResource(id = R.string.to),
                        textState = toInput,
                        errorText = when {
                            !isToValid -> stringResource(R.string.error_incorrect_hour)
                            !isOrderValid -> stringResource(R.string.error_incorrect_order_hour)
                            else -> null
                        },
                        validator = { isValidTimeFormat(it) && isOrderValid },
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp),
                        onValueChange = { newValue ->
                            toInput.value = newValue
                            if (isValidTimeFormat(newValue) && isOrderValid) {
                                onChange(
                                    workingHoursState.copy(
                                        opened = opened,
                                        wholeDay = wholeDay,
                                        from = fromInput.value,
                                        to = newValue
                                    )
                                )
                            }
                        },
                        readOnly = false,
                        enabled = true
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseTime(time: String): LocalTime? = try {
    LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
} catch (e: Exception) {
    null
}