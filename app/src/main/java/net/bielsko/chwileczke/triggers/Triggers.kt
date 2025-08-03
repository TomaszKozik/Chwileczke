package net.bielsko.chwileczke.triggers

import DropdownMenuWithLabel
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.bielsko.chwileczke.R
import net.bielsko.chwileczke.ui.fields.Header1

@Composable
fun Triggers() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Odczyt z DataStore, collectAsState z wartościami początkowymi
    val messagesForStored by TriggersPrefs.messagesFor(context).collectAsState(initial = "")
    val messagesFromStored by TriggersPrefs.messagesFrom(context).collectAsState(initial = "")

    val notAnswered = stringResource(id = R.string.not_answered)
    val notRead = stringResource(id = R.string.not_read)
    val both = "$notAnswered/$notRead"
    val optionsForMassagesFor = listOf(notAnswered, notRead, both)

    val nonNameContact = stringResource(id = R.string.noname_contact)
    val nameContact = stringResource(id = R.string.name_contact)
    val bothContact = "$nonNameContact/$nameContact"
    val optionsForMessagesFrom = listOf(nonNameContact, nameContact, bothContact)

    // Lokalny stan sterowany przez Compose
    var messagesFor by remember { mutableStateOf(both) }
    var messagesFrom by remember { mutableStateOf(bothContact) }

    // Synchronizacja stanu po odczycie DataStore
    LaunchedEffect(messagesForStored) {
        if (messagesForStored.isNotBlank() && messagesForStored != messagesFor) {
            messagesFor = messagesForStored
        }
    }
    LaunchedEffect(messagesFromStored) {
        if (messagesFromStored.isNotBlank() && messagesFromStored != messagesFrom) {
            messagesFrom = messagesFromStored
        }
    }

    Header1(stringResource(id = R.string.triggers))

    Spacer(modifier = Modifier.height(16.dp))

    DropdownMenuWithLabel(
        label = stringResource(id = R.string.messages_for),
        options = optionsForMassagesFor,
        selectedOption = messagesFor,
        onSelect = {
            messagesFor = it
            scope.launch { TriggersPrefs.setMessagesFor(context, it) }
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    DropdownMenuWithLabel(
        label = stringResource(id = R.string.messages_from),
        options = optionsForMessagesFrom,
        selectedOption = messagesFrom,
        onSelect = {
            messagesFrom = it
            scope.launch { TriggersPrefs.setMessagesFrom(context, it) }
        }
    )
}
