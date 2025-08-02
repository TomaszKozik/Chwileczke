package net.bielsko.chwileczke.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R

@Composable
fun WorkingHours(day: String) {
    var from by remember { mutableStateOf("08:00") }
    var to by remember { mutableStateOf("16:00") }
    var wholeDay by remember { mutableStateOf(false) }
    var openedInDay by remember { mutableStateOf(true) }
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
        // czy otwarte
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = openedInDay,
                onCheckedChange = { openedInDay = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(id = R.color.primary_blue),
                    uncheckedColor = colorResource(id = R.color.gray_text),
                    checkmarkColor = Color.White
                )
            )
            Text("$day:")
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (!openedInDay) stringResource(id = R.string.closed) else stringResource(id = R.string.opened)
            )
        }

        // W jakich godzinach czynne
        if (openedInDay) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Checkbox(
                    checked = wholeDay,
                    onCheckedChange = { wholeDay = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(id = R.color.primary_blue),
                        uncheckedColor = colorResource(id = R.color.gray_text),
                        checkmarkColor = Color.White
                    )
                )
                Text(
                    if (wholeDay) stringResource(id = R.string.whole_day) else stringResource(id = R.string.open_hours),
                    modifier = Modifier.padding(end = 8.dp)
                )
                if (!wholeDay) {
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = from,
                        onValueChange = { from = it },
                        label = { Text(stringResource(id = R.string.from)) },
                        modifier = Modifier
                            .width(80.dp)
                            .height(55.dp),
                        singleLine = true
                    )
                    Text("–", modifier = Modifier.padding(horizontal = 4.dp))
                    OutlinedTextField(
                        value = to,
                        onValueChange = { to = it },
                        label = { Text(stringResource(id = R.string.to)) },
                        modifier = Modifier
                            .width(80.dp)
                            .height(55.dp),
                        singleLine = true
                    )
                }
            }
        }
    }
}