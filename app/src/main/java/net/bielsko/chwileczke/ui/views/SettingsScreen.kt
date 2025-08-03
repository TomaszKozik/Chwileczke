package net.bielsko.chwileczke.ui.views

import DropdownMenuWithLabel
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R
import net.bielsko.chwileczke.ui.fields.Header1
import net.bielsko.chwileczke.ui.fields.Header2
import net.bielsko.chwileczke.ui.fields.MyCheckbox
import net.bielsko.chwileczke.workingHours.WorkingHoursDisplay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen() {
    var isHolidayBreakButtonClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Header1(stringResource(id = R.string.message_holiday_title))
        Text(
            stringResource(id = R.string.add_holiday_break_description),
            color = colorResource(id = R.color.gray_text)
        )
        HolidayAvailabilitySection()


        Spacer(modifier = Modifier.height(16.dp))




        WorkingHoursDisplay()

        Header1("Wyzwalacze")
        // Telefon/SMS
        Spacer(modifier = Modifier.height(8.dp))
        val notAnswered = stringResource(id = R.string.not_answered)
        val notRead = stringResource(id = R.string.not_read)
        val both = "$notAnswered/$notRead"
        var messagesFor by remember { mutableStateOf(both) }
        val optionsForMassagesFor = listOf(
            notAnswered,
            notRead,
            both
        )
        DropdownMenuWithLabel(
            stringResource(id = R.string.messages_for),
            optionsForMassagesFor,
            messagesFor,
            onSelect = { messagesFor = it })
        Spacer(modifier = Modifier.height(16.dp))

        // Znany/nieznany kontakt
        Spacer(modifier = Modifier.height(8.dp))
        val nonNameContact = stringResource(id = R.string.noname_contact)
        val nameContact = stringResource(id = R.string.name_contact)
        val bothContact = "$nonNameContact/$nameContact"
        var messagesFrom by remember { mutableStateOf("Nieznany i znany numer") }
        val optionsForMessagesFrom = listOf(
            nonNameContact,
            nameContact,
            bothContact
        )
        DropdownMenuWithLabel(
            stringResource(id = R.string.messages_from),
            optionsForMessagesFrom,
            messagesFrom,
            onSelect = { messagesFrom = it })
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HolidayAvailabilitySection() {
    var checked by remember { mutableStateOf(false) }
    var dateFrom by remember { mutableStateOf("") }
    var dateTo by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        MyCheckbox(
            checked = checked, onCheckedChange = { checked = it },
            label = stringResource(id = R.string.planned_holiday),
            labelIfChecked = stringResource(id = R.string.no_holiday)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (checked) {
            Column {
                DateTimePickerField(
                    label = "Data od",
                    value = dateFrom,
                    onValueChange = { dateFrom = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                DateTimePickerField(
                    label = "Data od",
                    value = dateTo,
                    onValueChange = { dateTo = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                val summaryText = formatHolidayText(dateFrom, dateTo)

                if (summaryText.isNotBlank()) {
                    Text(
                        text = summaryText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun DateTimePickerField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    // 🧠 Klikamy nie TextField, tylko Box
    Box(modifier = modifier
        .clickable {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Po dacie wybierzmy godzinę
                val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)

                    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                    onValueChange(formatter.format(calendar.time))
                }

                TimePickerDialog(
                    context,
                    timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            }

            DatePickerDialog(
                context,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {}, // brak ręcznej edycji
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false // 👈 WAŻNE: wyłącza tap i wskazuje, że nie można pisać
        )
    }
}

fun formatHolidayText(dateFrom: String, dateTo: String): String {
    return if (dateFrom.isNotBlank() && dateTo.isNotBlank()) {
        "Zabrałeś urlop od $dateFrom do $dateTo."
    } else {
        ""
    }
}

