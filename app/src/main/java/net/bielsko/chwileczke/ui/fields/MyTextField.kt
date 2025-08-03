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
fun MyTextField(
    labelText: String,
    textState: MutableState<String>? = null, // używane przy trybie kontrolowanym
    value: String? = null,                   // używane przy trybie zewnętrznym
    onValueChange: ((String) -> Unit)? = null,
    errorText: String? = null,
    validator: ((String) -> Boolean)? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    // Tryb działania
    val displayValue = textState?.value ?: value.orEmpty()
    val handleValueChange: (String) -> Unit = {
        textState?.value = it
        onValueChange?.invoke(it)
    }

    val isError = validator?.let { !it(displayValue) } ?: false

    OutlinedTextField(
        value = displayValue,
        onValueChange = handleValueChange,
        label = {
            Text(
                labelText,
                color = colorResource(id = R.color.gray_text)
            )
        },
        singleLine = true,
        readOnly = readOnly,
        enabled = enabled,
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
            unfocusedIndicatorColor = colorResource(id = R.color.gray_text),
            disabledIndicatorColor = colorResource(id = R.color.gray_text),
            disabledContainerColor = colorResource(id = R.color.white),
            disabledLabelColor = colorResource(id = R.color.gray_text),
            errorContainerColor = colorResource(id = R.color.white),
            disabledTextColor = colorResource(id = R.color.gray_text)
        )
    )
}
