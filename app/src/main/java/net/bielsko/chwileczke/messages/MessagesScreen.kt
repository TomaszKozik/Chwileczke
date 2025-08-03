package net.bielsko.chwileczke.messages

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.bielsko.chwileczke.R
import net.bielsko.chwileczke.helpers.AddUrlToMessage
import net.bielsko.chwileczke.messages.MessagePrefs.initDefaultMessageIfEmpty
import net.bielsko.chwileczke.ui.fields.Header3
import net.bielsko.chwileczke.ui.fields.MessageBox
import net.bielsko.chwileczke.ui.fields.SwitchBox
import net.bielsko.chwileczke.ui.fields.MyTextField

@Composable
fun MessageScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val messageInWorkDefaultText = stringResource(id = R.string.message_in_work_default_text)
    val messageOutOfWorkDefaultText = stringResource(id = R.string.message_out_of_work_default_text)
    val messageHolidayDefaultText = stringResource(id = R.string.message_holiday_default_text)
    val addLinkTextDefault = stringResource(id = R.string.add_link_text_default)
    val addLinkUrlDefault = stringResource(id = R.string.add_link_url_default)

    // Inicjalizacja defaultów przy starcie
    LaunchedEffect(Unit) {
        initDefaultMessageIfEmpty(
            context,
            MessagePrefs::getInWorkMessage,
            MessagePrefs::setInWorkMessage,
            messageInWorkDefaultText
        )
        initDefaultMessageIfEmpty(
            context,
            MessagePrefs::getOutOfWorkMessage,
            MessagePrefs::setOutOfWorkMessage,
            messageOutOfWorkDefaultText
        )
        initDefaultMessageIfEmpty(
            context,
            MessagePrefs::getHolidayMessage,
            MessagePrefs::setHolidayMessage,
            messageHolidayDefaultText
        )
        initDefaultMessageIfEmpty(
            context,
            MessagePrefs::getAddLinkText,
            MessagePrefs::setAddLinkText,
            addLinkTextDefault
        )
        initDefaultMessageIfEmpty(
            context,
            MessagePrefs::getAddLinkUrl,
            MessagePrefs::setAddLinkUrl,
            addLinkUrlDefault
        )
    }

    // Teraz pobieramy dane z DataStore
    val savedInWorkMessage by MessagePrefs.getInWorkMessage(context).collectAsState(initial = "")
    val savedOutOfWorkMessage by MessagePrefs.getOutOfWorkMessage(context).collectAsState(initial = "")
    val savedHolidayMessage by MessagePrefs.getHolidayMessage(context).collectAsState(initial = "")
    val savedAddLinkText by MessagePrefs.getAddLinkText(context).collectAsState(initial = "")
    val savedAddLinkUrl by MessagePrefs.getAddLinkUrl(context).collectAsState(initial = "")
    val savedAddLinkEnabled by MessagePrefs.isAddLinkEnabled(context).collectAsState(initial = false)

    var mutInWorkMessage = remember { mutableStateOf(savedInWorkMessage) }
    var mutOutOfWorkMessage = remember { mutableStateOf(savedOutOfWorkMessage) }
    var mutHolidayMessage = remember { mutableStateOf(savedHolidayMessage) }
    var mutAddLinkText = remember { mutableStateOf(savedAddLinkText) }
    var mutAddLinkUrl = remember { mutableStateOf(savedAddLinkUrl) }
    var isAddLinkEnabled = remember { mutableStateOf(savedAddLinkEnabled) }

    // Synchronizacja przy odczycie
    LaunchedEffect(savedInWorkMessage) { mutInWorkMessage.value = savedInWorkMessage }
    LaunchedEffect(savedOutOfWorkMessage) { mutOutOfWorkMessage.value = savedOutOfWorkMessage }
    LaunchedEffect(savedHolidayMessage) { mutHolidayMessage.value = savedHolidayMessage }
    LaunchedEffect(savedAddLinkText) { mutAddLinkText.value = savedAddLinkText }
    LaunchedEffect(savedAddLinkUrl) { mutAddLinkUrl.value = savedAddLinkUrl }
    LaunchedEffect(savedAddLinkEnabled) { isAddLinkEnabled.value = savedAddLinkEnabled }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        MessageBox(
            title = stringResource(id = R.string.message_in_work_title),
            description = stringResource(id = R.string.message_in_work_description),
            textState = mutInWorkMessage,
            onTextChanged = { newText ->
                mutInWorkMessage.value = newText
                scope.launch { MessagePrefs.setInWorkMessage(context, newText) }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        MessageBox(
            title = stringResource(id = R.string.message_out_of_work_title),
            description = stringResource(id = R.string.message_out_of_work_description),
            textState = mutOutOfWorkMessage,
            onTextChanged = { newText ->
                mutOutOfWorkMessage.value = newText
                scope.launch { MessagePrefs.setOutOfWorkMessage(context, newText) }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        MessageBox(
            title = stringResource(id = R.string.message_holiday_title),
            description = stringResource(id = R.string.message_holiday_description),
            textState = mutHolidayMessage,
            onTextChanged = { newText ->
                mutHolidayMessage.value = newText
                scope.launch { MessagePrefs.setHolidayMessage(context, newText) }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Header3(stringResource(id = R.string.add_link_title))

        SwitchBox(
            description = stringResource(id = R.string.add_link_button_description),
            switchState = isAddLinkEnabled,
            onCheckedChange = { newChecked ->
                isAddLinkEnabled.value = newChecked
                scope.launch { MessagePrefs.setAddLinkEnabled(context, newChecked) }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (isAddLinkEnabled.value) {
            MyTextField(
                labelText = stringResource(id = R.string.add_link_text_title),
                textState = mutAddLinkText,
                onValueChange = { newText ->
                    mutAddLinkText.value = newText
                    scope.launch { MessagePrefs.setAddLinkText(context, newText) }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            MyTextField(
                labelText = stringResource(id = R.string.add_link_url_title),
                textState = mutAddLinkUrl,
                errorText = if (URLUtil.isValidUrl(mutAddLinkUrl.value)) null else stringResource(id = R.string.add_link_url_error),
                validator = { url -> URLUtil.isValidUrl(url) },
                onValueChange = { newText ->
                    mutAddLinkUrl.value = newText
                    scope.launch { MessagePrefs.setAddLinkUrl(context, newText) }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        AddUrlToMessage(mutInWorkMessage, mutAddLinkText, mutAddLinkUrl, isAddLinkEnabled)
        AddUrlToMessage(mutOutOfWorkMessage, mutAddLinkText, mutAddLinkUrl, isAddLinkEnabled)
        AddUrlToMessage(mutHolidayMessage, mutAddLinkText, mutAddLinkUrl, isAddLinkEnabled)
    }
}
