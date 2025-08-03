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
import net.bielsko.chwileczke.helpers.isValidTimeFormat
import net.bielsko.chwileczke.ui.fields.TextFieldBox
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkingHoursForm(
    state: WorkingHoursState,
    onChange: (WorkingHoursState) -> Unit
) {
    var opened by remember { mutableStateOf(state.opened) }
    var wholeDay by remember { mutableStateOf(state.wholeDay) }
    var from by remember { mutableStateOf(state.from) }
    var to by remember { mutableStateOf(state.to) }
    var fromInput = remember { mutableStateOf(from) }
    var toInput = remember { mutableStateOf(to) }

    val day = stringResource(id = state.day)
    val primaryBlue = colorResource(id = R.color.primary_blue)

    val isFromValid = isValidTimeFormat(fromInput.value)
    val isToValid = isValidTimeFormat(toInput.value)
    val isOrderValid = if (isFromValid && isToValid) {
        parseTime(fromInput.value)?.isBefore(parseTime(toInput.value)) ?: true
    } else true

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
                    else stringResource(id = R.string.open_hours)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                if (!wholeDay) {
                    TextFieldBox(
                        title = stringResource(id = R.string.from),
                        textState = fromInput,
                        errorText = when {
                            !isFromValid -> stringResource(R.string.error_incorrect_hour)
                            !isOrderValid -> stringResource(R.string.error_incorrect_order_hour)
                            else -> null
                        },
                        validator = { isValidTimeFormat(it) && isOrderValid },
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp)
                    )
                    Text("–", modifier = Modifier.padding(horizontal = 4.dp))
                    TextFieldBox(
                        title = stringResource(id = R.string.to),
                        textState = toInput,
                        errorText = when {
                            !isToValid -> stringResource(R.string.error_incorrect_hour)
                            !isOrderValid -> stringResource(R.string.error_incorrect_order_hour)
                            else -> null
                        },
                        validator = { isValidTimeFormat(it) && isOrderValid },
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp)
                    )

                    // Aktualizujemy stan tylko jeśli oba czasy są poprawne
                    if (isFromValid && isToValid && isOrderValid) {
                        from = fromInput.value
                        to = toInput.value
                        onChange(state.copy(opened = opened, wholeDay = false, from = from, to = to))
                    }
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