package net.bielsko.chwileczke.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R
import net.bielsko.chwileczke.helpers.isValidUrl
import net.bielsko.chwileczke.ui.fields.MultiLineTextField

@Composable
fun MessageScreen() {
    val messageInWorkDefaultText = stringResource(id = R.string.message_in_work_default_text)
    val messageOutOfWorkDefaultText = stringResource(id = R.string.message_out_of_work_default_text)
    var mutableMessageInWorkDefaultText by remember { mutableStateOf(messageInWorkDefaultText) }
    val messageHolidayDefaultText = stringResource(id = R.string.message_holiday_default_text)
    var mutableMessageOutOfWorkDefaultText by remember { mutableStateOf(messageOutOfWorkDefaultText) }
    var mutableMessageHolidayDefaultText by remember { mutableStateOf(messageHolidayDefaultText) }
    var isHolidayBreakButtonClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Message in work
        MessageBox(
            title = stringResource(id = R.string.message_in_work_title),
            description = stringResource(id = R.string.message_in_work_description),
            textState = remember { mutableStateOf(mutableMessageInWorkDefaultText) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Message out of work
        MessageBox(
            title = stringResource(id = R.string.message_out_of_work_title),
            description = stringResource(id = R.string.message_out_of_work_description),
            textState = remember { mutableStateOf(mutableMessageOutOfWorkDefaultText) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Holiday break message
        MessageBox(
            title = stringResource(id = R.string.message_holiday_title),
            description = stringResource(id = R.string.message_holiday_description),
            textState = remember { mutableStateOf(mutableMessageHolidayDefaultText) }
        )
        // Holiday break message
        Spacer(modifier = Modifier.height(16.dp))


        if (!isHolidayBreakButtonClicked) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary_blue),
                    contentColor = colorResource(id = R.color.white)
                ),
                onClick = { isHolidayBreakButtonClicked = true }) {
                Text(
                    stringResource(id = R.string.add_holiday_break_button),
                    color = Color.White
                )
            }
        } else {
            Text(
                "Zakres dat i godzin do wyboru (TODO: data picker)",
                color = colorResource(id = R.color.gray_text)
            )
            Text(
                stringResource(id = R.string.add_holiday_break_description),
                color = colorResource(id = R.color.gray_text)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary_blue),
                    contentColor = colorResource(id = R.color.white)
                ),
                onClick = { isHolidayBreakButtonClicked = false }) {
                Text(
                    stringResource(id = R.string.remove_holiday_break),
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Link do wiadomości
        var isAddLinkEnabled by remember { mutableStateOf(false) }
        Text(
            stringResource(id = R.string.add_link_title),
            fontWeight = FontWeight.Bold
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = isAddLinkEnabled,
                onCheckedChange = { isAddLinkEnabled = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(id = R.color.primary_blue),
                    uncheckedThumbColor = colorResource(id = R.color.gray_text)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                stringResource(id = R.string.add_link_button_description),
                color = colorResource(id = R.color.gray_text)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (isAddLinkEnabled) {
            val addLinkTextDefault = stringResource(id = R.string.add_link_text_default)

            // TextField for link text
            TextFieldBox(
                title = stringResource(id = R.string.add_link_text_title),
                textState = remember { mutableStateOf(addLinkTextDefault) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // TextField for link URL
            TextFieldBox(
                title = stringResource(id = R.string.add_link_url_title),
                textState = remember { mutableStateOf("https://") }
            )
            Spacer(modifier = Modifier.height(8.dp))

//            if (!isValidUrl(linkUrl)) {
//                Text(
//                    stringResource(id = R.string.add_link_url_error),
//                    color = colorResource(id = R.color.error_red)
//                )
//            }
        }
    }
}

@Composable
fun TextFieldBox(
    title: String,
    textState: MutableState<String>
) {
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
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(id = R.color.white),
            unfocusedContainerColor = colorResource(id = R.color.white),
            focusedIndicatorColor = colorResource(id = R.color.primary_blue),
            unfocusedIndicatorColor = colorResource(id = R.color.gray_text)
        )
    )
}

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
        onValueChange = { textState.value = it }
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        description,
        color = colorResource(id = R.color.gray_text)
    )
}
