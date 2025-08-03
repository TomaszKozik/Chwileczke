package net.bielsko.chwileczke.ui.fields

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R

@Composable
fun MyCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    labelIfChecked: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .toggleable( // cała powierzchnia jest dotykowa i czytelna dla czytnika ekranu
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Checkbox
            )
            .padding(8.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(id = R.color.primary_blue),
                uncheckedColor = colorResource(id = R.color.gray_text),
                checkmarkColor = colorResource(id = R.color.white)
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        (if (checked) {label} else {labelIfChecked})?.let { Text(it) }
    }
}
