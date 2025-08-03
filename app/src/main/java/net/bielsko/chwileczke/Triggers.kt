package net.bielsko.chwileczke

import DropdownMenuWithLabel
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun Triggers() {
    Spacer(modifier = Modifier.height(8.dp))
    val notAnswered = stringResource(id = R.string.not_answered)
    val notRead = stringResource(id = R.string.not_read)
    val both = "$notAnswered/$notRead"
    var messagesFor by remember { mutableStateOf(both) }
    val optionsForMassagesFor = listOf(
        notAnswered,
        notRead,
        both
    )
    DropdownMenuWithLabel(
        stringResource(id = R.string.messages_for),
        optionsForMassagesFor,
        messagesFor,
        onSelect = { messagesFor = it })
    Spacer(modifier = Modifier.height(16.dp))

    // Znany/nieznany kontakt
    Spacer(modifier = Modifier.height(8.dp))
    val nonNameContact = stringResource(id = R.string.noname_contact)
    val nameContact = stringResource(id = R.string.name_contact)
    val bothContact = "$nonNameContact/$nameContact"
    var messagesFrom by remember { mutableStateOf("Nieznany i znany numer") }
    val optionsForMessagesFrom = listOf(
        nonNameContact,
        nameContact,
        bothContact
    )
    DropdownMenuWithLabel(
        stringResource(id = R.string.messages_from),
        optionsForMessagesFrom,
        messagesFrom,
        onSelect = { messagesFrom = it })
    Spacer(modifier = Modifier.height(16.dp))
}