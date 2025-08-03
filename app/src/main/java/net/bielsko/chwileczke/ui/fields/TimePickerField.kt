package net.bielsko.chwileczke.ui.fields

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@SuppressLint("DefaultLocale")
@Composable
fun TimePickerField(
    label: String,
    time: String, // "HH:mm"
    onTimeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    minTimeMinutes: Int? = null,  // min limit w minutach od północy
    maxTimeMinutes: Int? = null   // max limit w minutach od północy
) {
    val context = LocalContext.current
    val calendar = remember { java.util.Calendar.getInstance() }

    Box(modifier = modifier.clickable {
        val initialHour = time.split(":").getOrNull(0)?.toIntOrNull() ?: calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val initialMinute = time.split(":").getOrNull(1)?.toIntOrNull() ?: calendar.get(java.util.Calendar.MINUTE)

        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            val selectedMinutes = hour * 60 + minute
            val minOk = minTimeMinutes?.let { selectedMinutes >= it } ?: true
            val maxOk = maxTimeMinutes?.let { selectedMinutes <= it } ?: true
            if (minOk && maxOk) {
                onTimeChange(String.format("%02d:%02d", hour, minute))
            } else {
                // Można tu dać Toast, że wybrano niedozwoloną godzinę
            }
        }

        TimePickerDialog(
            context,
            timeSetListener,
            initialHour,
            initialMinute,
            true
        ).show()
    }) {
        MyTextField(
            value = time,
            onValueChange = {},
            labelText = label,
            readOnly = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
