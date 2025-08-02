package net.bielsko.chwileczke.ui.fields

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.colorResource
import net.bielsko.chwileczke.R

@Composable
fun MessageBox(
    title: String,
    description: String,
    textState: MutableState<String>
) {
    MultiLineTextField(
        value = textState.value,
        label = {
            Text(
                title,
                color = colorResource(id = R.color.gray_text)
            )
        },
        supportingText = {
            Text(
                text = description,
                color = colorResource(id = R.color.gray_text)
            )
        },
        onValueChange = { textState.value = it }
    )
}