package net.bielsko.chwileczke.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WorkingHours(dzien: String) {
    var godzinaOd by remember { mutableStateOf("08:00") }
    var godzinaDo by remember { mutableStateOf("16:00") }
    var pracujemy by remember { mutableStateOf(true) }
    var calodobowo by remember { mutableStateOf(false) }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(dzien, modifier = Modifier.width(30.dp))
            OutlinedTextField(
                value = godzinaOd,
                onValueChange = { godzinaOd = it },
                label = { Text("od") },
                modifier = Modifier.width(80.dp),
                enabled = pracujemy && !calodobowo,
                singleLine = true
            )
            Text("–", modifier = Modifier.padding(horizontal = 4.dp))
            OutlinedTextField(
                value = godzinaDo,
                onValueChange = { godzinaDo = it },
                label = { Text("do") },
                modifier = Modifier.width(80.dp),
                enabled = pracujemy && !calodobowo,
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Switch(checked = pracujemy, onCheckedChange = { pracujemy = it })
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = calodobowo, onCheckedChange = { calodobowo = it })
            Text("Praca całodobowa")
        }
    }
}