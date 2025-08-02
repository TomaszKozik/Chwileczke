package net.bielsko.chwileczke.ui.views

import DropdownMenuWithLabel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.R

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            stringResource(id = R.string.working_hours),
            fontWeight = FontWeight.Bold,
        )
        val days = listOf(
            R.string.monday,
            R.string.tuesday,
            R.string.wednesday,
            R.string.thursday,
            R.string.friday,
            R.string.saturday,
            R.string.sunday)
        days.forEach { dayResId ->
            val day = stringResource(id = dayResId)
            WorkingHours(day)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Telefon/SMS
        Text(
            stringResource(id = R.string.messages_for),
            fontWeight = FontWeight.Bold)
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
        DropdownMenuWithLabel(optionsForMassagesFor, messagesFor, onSelect = { messagesFor = it })
        Spacer(modifier = Modifier.height(16.dp))

        // Znany/nieznany kontakt
        Text(
            stringResource(id = R.string.messages_from),
            fontWeight = FontWeight.Bold)
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
        DropdownMenuWithLabel(optionsForMessagesFrom, messagesFrom, onSelect = { messagesFrom = it })
        Spacer(modifier = Modifier.height(16.dp))
    }
}