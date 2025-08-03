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

@Composable
fun HolidayAvailabilitySection() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Odczyt z DataStore, collectAsState z wartościami początkowymi
    val savedChecked by HolidayPrefs.isHolidayChecked(context).collectAsState(initial = false)
    val savedDateFrom by HolidayPrefs.holidayDateFrom(context).collectAsState(initial = "")
    val savedDateTo by HolidayPrefs.holidayDateTo(context).collectAsState(initial = "")

    // Lokalny stan sterowany przez Compose
    var checked by remember { mutableStateOf(false) }
    var dateFrom by remember { mutableStateOf("") }
    var dateTo by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }

    // Synchronizacja przy zmianach w DataStore (np. przy pierwszym uruchomieniu)
    LaunchedEffect(savedChecked, savedDateFrom, savedDateTo) {
        checked = savedChecked
        dateFrom = savedDateFrom
        dateTo = savedDateTo
    }
    LaunchedEffect(dateFrom, dateTo, checked) {
        hasError = checked && (dateFrom.isBlank() || dateTo.isBlank())
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Header1(stringResource(id = R.string.message_holiday_title))

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            stringResource(id = R.string.add_holiday_break_description),
            color = colorResource(id = R.color.gray_text)
        )

        MyCheckbox(
            checked = checked,
            onCheckedChange = { newChecked ->
                checked = newChecked
                scope.launch {
                    HolidayPrefs.setHolidayChecked(context, newChecked)
                    if (!newChecked) {
                        // Jeśli odznaczysz checkbox, czyścimy daty
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
                // Data przerwy od
                DateTimePickerField(
                    label = "Data od",
                    value = dateFrom,
                    onValueChange = {
                        dateFrom = it
                        scope.launch { HolidayPrefs.setHolidayDateFrom(context, it) }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Data przerwy do
                DateTimePickerField(
                    label = "Data do",
                    value = dateTo,
                    onValueChange = {
                        dateTo = it
                        scope.launch { HolidayPrefs.setHolidayDateTo(context, it) }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                if (hasError) {
                    Text(
                        text = stringResource(id = R.string.error_fill_dates), // Dodaj do strings.xml
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}