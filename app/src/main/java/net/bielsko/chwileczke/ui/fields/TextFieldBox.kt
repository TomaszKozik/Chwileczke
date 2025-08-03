package net.bielsko.chwileczke.ui.fields

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import net.bielsko.chwileczke.R

@Composable
fun TextFieldBox(
    title: String,
    textState: MutableState<String>,
    errorText: String? = null,
    validator: (String) -> Boolean = { true },
    modifier: Modifier
) {
    val isError = !validator(textState.value)
    OutlinedTextField(
        value = textState.value,
        onValueChange = { textState.value = it },
        label = {
            Text(
                title,
                color = colorResource(id = R.color.gray_text)
            )
        },
        singleLine = true,
        modifier = modifier,
        isError = isError,
        supportingText = {
            if (isError && errorText != null) {
                Text(
                    text = errorText,
                    color = colorResource(id = R.color.error_red)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(id = R.color.white),
            unfocusedContainerColor = colorResource(id = R.color.white),
            focusedIndicatorColor = colorResource(id = R.color.primary_blue),
            unfocusedIndicatorColor = colorResource(id = R.color.gray_text)
        )
    )
}