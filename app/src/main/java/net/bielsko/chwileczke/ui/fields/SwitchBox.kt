package net.bielsko.chwileczke.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R

@Composable
fun SwitchBox(
    description: String,
    switchState: MutableState<Boolean>,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Switch(
            checked = switchState.value,
            onCheckedChange = {
                switchState.value = it
                onCheckedChange(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = colorResource(id = R.color.link_blue),
                uncheckedThumbColor = colorResource(id = R.color.gray_text),
                checkedTrackColor = colorResource(id = R.color.primary_blue),
                uncheckedTrackColor = colorResource(id = R.color.white)
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = description,
            color = colorResource(id = R.color.gray_text)
        )
    }
}
