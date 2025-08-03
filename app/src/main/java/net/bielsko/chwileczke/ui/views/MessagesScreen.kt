package net.bielsko.chwileczke.ui.views

import android.webkit.URLUtil
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R
import net.bielsko.chwileczke.helpers.AddUrlToMessage
import net.bielsko.chwileczke.ui.fields.MessageBox
import net.bielsko.chwileczke.ui.fields.SwitchBox
import net.bielsko.chwileczke.ui.fields.MyTextField

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
            MyTextField(
                labelText = stringResource(id = R.string.add_link_text_title),
                textState = mutAddLinkText,
                modifier = Modifier.fillMaxWidth(),
//                value = value,
                onValueChange = {},
                readOnly = true,
                enabled = false
            )
            Spacer(modifier = Modifier.height(8.dp))

            // TextField for link URL
            MyTextField(
                labelText = stringResource(id = R.string.add_link_url_title),
                textState = mutAddLinkUrl,
                errorText = stringResource(id = R.string.add_link_url_error),
                validator = { url -> URLUtil.isValidUrl(url) },
                modifier = Modifier.fillMaxWidth(),
//                value = value,
                onValueChange = {},
                readOnly = true,
                enabled = false
            )
            Spacer(modifier = Modifier.height(8.dp))

        }

        AddUrlToMessage(mutInWorkMessage, mutAddLinkText, mutAddLinkUrl, isAddLinkEnabled)
        AddUrlToMessage(mutOutOfWorkMessage, mutAddLinkText, mutAddLinkUrl, isAddLinkEnabled)
        AddUrlToMessage(mutHolidayMessage, mutAddLinkText, mutAddLinkUrl, isAddLinkEnabled)
    }
}