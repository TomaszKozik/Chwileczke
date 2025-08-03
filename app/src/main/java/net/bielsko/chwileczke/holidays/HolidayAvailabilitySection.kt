package net.bielsko.chwileczke.holidays

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.bielsko.chwileczke.R
import net.bielsko.chwileczke.ui.fields.DateTimePickerField
import net.bielsko.chwileczke.ui.fields.Header1
import net.bielsko.chwileczke.ui.fields.MyCheckbox
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun HolidayAvailabilitySection() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val savedChecked by HolidayPrefs.isHolidayChecked(context).collectAsState(initial = false)
    val savedDateFrom by HolidayPrefs.holidayDateFrom(context).collectAsState(initial = "")
    val savedDateTo by HolidayPrefs.holidayDateTo(context).collectAsState(initial = "")

    var checked by remember { mutableStateOf(false) }
    var dateFrom by remember { mutableStateOf("") }
    var dateTo by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }

    LaunchedEffect(savedChecked, savedDateFrom, savedDateTo) {
        checked = savedChecked
        dateFrom = savedDateFrom
        dateTo = savedDateTo
    }
    LaunchedEffect(dateFrom, dateTo, checked) {
        hasError = checked && (dateFrom.isBlank() || dateTo.isBlank())
    }

    val formatter = remember { SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()) }

    // Parsowanie dat na timestamp (nullable)
    val dateFromTimestamp = remember(dateFrom) {
        try {
            if (dateFrom.isNotBlank()) formatter.parse(dateFrom)?.time else null
        } catch (e: Exception) {
            null
        }
    }

    val dateToTimestamp = remember(dateTo) {
        try {
            if (dateTo.isNotBlank()) formatter.parse(dateTo)?.time else null
        } catch (e: Exception) {
            null
        }
    }

    // Timestamp na dzisiaj 00:00, żeby nie można było wybrać daty z przeszłości
    val todayTimestamp = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    Header1(stringResource(id = R.string.message_holiday_title))

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        stringResource(id = R.string.add_holiday_break_description),
        color = colorResource(id = R.color.gray_text)
    )

    Column(modifier = Modifier.padding(16.dp)) {

        MyCheckbox(
            checked = checked,
            onCheckedChange = { newChecked ->
                checked = newChecked
                scope.launch {
                    HolidayPrefs.setHolidayChecked(context, newChecked)
                    if (!newChecked) {
                        HolidayPrefs.setHolidayDateFrom(context, "")
                        HolidayPrefs.setHolidayDateTo(context, "")
                    }
                }
            },
            label = stringResource(id = R.string.planned_holiday),
            labelIfChecked = stringResource(id = R.string.no_holiday)
        )

        if (checked) {
            Column {
                DateTimePickerField(
                    label = "Data od",
                    value = dateFrom,
                    onValueChange = {
                        dateFrom = it
                        scope.launch { HolidayPrefs.setHolidayDateFrom(context, it) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minDate = todayTimestamp,
                    maxDate = dateToTimestamp  // Nie pozwalamy na datę "od" późniejszą niż "do"
                )

                DateTimePickerField(
                    label = "Data do",
                    value = dateTo,
                    onValueChange = {
                        dateTo = it
                        scope.launch { HolidayPrefs.setHolidayDateTo(context, it) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minDate = dateFromTimestamp ?: todayTimestamp  // "do" nie może być wcześniejsze niż "od" lub dziś
                )

                if (hasError) {
                    Text(
                        text = stringResource(id = R.string.error_fill_dates),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}