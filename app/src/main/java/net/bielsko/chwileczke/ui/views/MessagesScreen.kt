package net.bielsko.chwileczke.ui.views

import android.webkit.URLUtil
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R
import net.bielsko.chwileczke.helpers.AddUrlToMessage
import net.bielsko.chwileczke.ui.fields.MessageBox
import net.bielsko.chwileczke.ui.fields.SwitchBox
import net.bielsko.chwileczke.ui.fields.TextFieldBox

@Composable
fun MessageScreen() {
    val messageInWorkDefaultText = stringResource(id = R.string.message_in_work_default_text)
    val messageOutOfWorkDefaultText = stringResource(id = R.string.message_out_of_work_default_text)
    val messageHolidayDefaultText = stringResource(id = R.string.message_holiday_default_text)
    val addLinkTextDefault = stringResource(id = R.string.add_link_text_default)
    val addLinkUrl = stringResource(id = R.string.add_link_url_default)
    val mutInWorkMessage = remember { mutableStateOf(messageInWorkDefaultText) }
    val mutOutOfWorkMessage = remember { mutableStateOf(messageOutOfWorkDefaultText) }
    val mutHolidayMessage = remember { mutableStateOf(messageHolidayDefaultText) }
    val mutAddLinkText = remember { mutableStateOf(addLinkTextDefault) }
    val mutAddLinkUrl = remember { mutableStateOf(addLinkUrl) }
    val isAddLinkEnabled = remember { mutableStateOf(false) }
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
            textState = mutInWorkMessage
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Message out of work
        MessageBox(
            title = stringResource(id = R.string.message_out_of_work_title),
            description = stringResource(id = R.string.message_out_of_work_description),
            textState = mutOutOfWorkMessage
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Holiday break message
        MessageBox(
            title = stringResource(id = R.string.message_holiday_title),
            description = stringResource(id = R.string.message_holiday_description),
            textState = mutHolidayMessage
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
        Text(
            stringResource(id = R.string.add_link_title),
            fontWeight = FontWeight.Bold
        )
        SwitchBox(
            description = stringResource(id = R.string.add_link_button_description),
            switchState = isAddLinkEnabled
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (isAddLinkEnabled.value) {
            // TextField for link text
            TextFieldBox(
                title = stringResource(id = R.string.add_link_text_title),
                textState = mutAddLinkText
            )
            Spacer(modifier = Modifier.height(8.dp))

            // TextField for link URL
            TextFieldBox(
                title = stringResource(id = R.string.add_link_url_title),
                textState = mutAddLinkUrl,
                errorText = stringResource(id = R.string.add_link_url_error),
                validator = { url -> URLUtil.isValidUrl(url) }
            )
            Spacer(modifier = Modifier.height(8.dp))

        }

        AddUrlToMessage(mutInWorkMessage, mutAddLinkText, mutAddLinkUrl, isAddLinkEnabled)
        AddUrlToMessage(mutOutOfWorkMessage, mutAddLinkText, mutAddLinkUrl, isAddLinkEnabled)
        AddUrlToMessage(mutHolidayMessage, mutAddLinkText, mutAddLinkUrl, isAddLinkEnabled)
    }
}