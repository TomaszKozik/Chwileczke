package net.bielsko.chwileczke.ui.fields

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R

@Composable
fun MultiLineTextField(
    value: String,
    label: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    maxLinesAllowed: Int = 12,
    colors: TextFieldColors? = null,
    supportingText: @Composable (() -> Unit)? = null
) {
    val actualColors = colors ?: TextFieldDefaults.colors(
        focusedContainerColor = colorResource(id = R.color.white),
        unfocusedContainerColor = colorResource(id = R.color.white),
        focusedIndicatorColor = colorResource(id = R.color.primary_blue),
        unfocusedIndicatorColor = colorResource(id = R.color.gray_text)
    )
    OutlinedTextField(
        value = value,
        label = label,
        onValueChange = { newValue ->
            val lines = newValue.lines()
            if (lines.size <= maxLinesAllowed) {
                onValueChange(newValue)
            } else {
                onValueChange(lines.take(maxLinesAllowed).joinToString("\n"))
            }
        },
        colors = actualColors,
        supportingText = supportingText,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 125.dp, max = 200.dp),
        enabled = enabled,
        maxLines = maxLinesAllowed
    )
}
