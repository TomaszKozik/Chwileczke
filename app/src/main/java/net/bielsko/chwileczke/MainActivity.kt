package net.bielsko.chwileczke

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import net.bielsko.chwileczke.ui.fields.MultiLineTextField
import net.bielsko.chwileczke.ui.views.MainView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()
        }
    }
}

// Messages Screen
@SuppressLint("ResourceAsColor")
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
        MultiLineTextField(
            value = mutableMessageInWorkDefaultText,
            label = {
                Text(
                    stringResource(id = R.string.message_in_work_title),
                    color = colorResource(id = R.color.gray_text)
                )
            },
            onValueChange = { mutableMessageInWorkDefaultText = it })
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            stringResource(id = R.string.message_in_work_description),
            color = colorResource(id = R.color.gray_text)
        )

        // Message out of work
        Spacer(modifier = Modifier.height(16.dp))
        MultiLineTextField(
            value = mutableMessageOutOfWorkDefaultText,
            label = {
                Text(
                    stringResource(id = R.string.message_out_of_work_title),
                    color = colorResource(id = R.color.gray_text)
                )
            },
            onValueChange = { mutableMessageOutOfWorkDefaultText = it })
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            stringResource(id = R.string.message_out_of_work_description),
            color = colorResource(id = R.color.gray_text)
        )

        // Holiday break message
        Spacer(modifier = Modifier.height(16.dp))
        MultiLineTextField(
            value = mutableMessageHolidayDefaultText,
            label = {
                Text(
                    stringResource(id = R.string.message_holiday_title),
                    color = colorResource(id = R.color.gray_text)
                )
            },
            onValueChange = { mutableMessageHolidayDefaultText = it })
        Text(
            stringResource(id = R.string.message_holiday_description),
            color = colorResource(id = R.color.gray_text)
        )

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

        if (isAddLinkEnabled) {
            val addLinkTextDefault = stringResource(id = R.string.add_link_text_default)
            var mutableAddLinkTextDefault by remember { mutableStateOf(addLinkTextDefault) }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = mutableAddLinkTextDefault,
                onValueChange = { mutableAddLinkTextDefault = it },
                label = {
                    Text(
                        stringResource(id = R.string.add_link_text_description),
                        color = colorResource(id = R.color.gray_text)
                    )
                },
                enabled = isAddLinkEnabled,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.white),
                    unfocusedContainerColor = colorResource(id = R.color.white),
                    focusedIndicatorColor = colorResource(id = R.color.primary_blue),
                    unfocusedIndicatorColor = colorResource(id = R.color.gray_text)
                )
            )


            var linkUrl by remember { mutableStateOf("") }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = linkUrl,
                onValueChange = { linkUrl = it },
                label = {
                    Text(
                        stringResource(id = R.string.add_link_url_description),
                        color = colorResource(id = R.color.gray_text)
                    )
                },
                enabled = isAddLinkEnabled,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.white),
                    unfocusedContainerColor = colorResource(id = R.color.white),
                    focusedIndicatorColor = colorResource(id = R.color.primary_blue),
                    unfocusedIndicatorColor = colorResource(id = R.color.gray_text)
                ),
                isError = isAddLinkEnabled && !isValidUrl(linkUrl)

            )
            if (isAddLinkEnabled && !isValidUrl(linkUrl)) {
                Text(
                    stringResource(id = R.string.add_link_url_error),
                    color = colorResource(id = R.color.error_red))
            }
        }
    }
}

// Ustawienia
@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text("Wiadomość dla", fontWeight = FontWeight.Bold)
        var wiadomoscDla by remember { mutableStateOf("Nieodebrane połączenie/Nieodczytany SMS") }
        val wiadomoscDlaOpcje = listOf(
            "Nieodebrane połączenie",
            "Nieodczytany SMS",
            "Nieodebrane połączenie/Nieodczytany SMS"
        )

        DropdownMenuWithLabel(wiadomoscDlaOpcje, wiadomoscDla, onSelect = { wiadomoscDla = it })

        Spacer(modifier = Modifier.height(16.dp))

        Text("Wiadomość od", fontWeight = FontWeight.Bold)
        var wiadomoscOd by remember { mutableStateOf("Nieznany i znany numer") }
        val wiadomoscOdOpcje = listOf("Nieznany numer", "Znany numer", "Nieznany i znany numer")

        DropdownMenuWithLabel(wiadomoscOdOpcje, wiadomoscOd, onSelect = { wiadomoscOd = it })

        Spacer(modifier = Modifier.height(16.dp))

        Text("Godziny pracy", fontWeight = FontWeight.Bold)
        val dni = listOf("Pn", "Wt", "Śr", "Cz", "Pt", "Sb", "Nd")
        dni.forEach { dzien ->
            DzienGodzinyPracy(dzien)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun DropdownMenuWithLabel(options: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            label = { Text("Wybierz") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            enabled = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Dropdown Icon"
                )
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DzienGodzinyPracy(dzien: String) {
    var godzinaOd by remember { mutableStateOf("08:00") }
    var godzinaDo by remember { mutableStateOf("16:00") }
    var pracujemy by remember { mutableStateOf(true) }
    var calodobowo by remember { mutableStateOf(false) }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(dzien, modifier = Modifier.width(30.dp))
            OutlinedTextField(
                value = godzinaOd,
                onValueChange = { godzinaOd = it },
                label = { Text("od") },
                modifier = Modifier.width(80.dp),
                enabled = pracujemy && !calodobowo,
                singleLine = true
            )
            Text("–", modifier = Modifier.padding(horizontal = 4.dp))
            OutlinedTextField(
                value = godzinaDo,
                onValueChange = { godzinaDo = it },
                label = { Text("do") },
                modifier = Modifier.width(80.dp),
                enabled = pracujemy && !calodobowo,
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Switch(checked = pracujemy, onCheckedChange = { pracujemy = it })
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = calodobowo, onCheckedChange = { calodobowo = it })
            Text("Praca całodobowa")
        }
    }
}

fun isValidUrl(url: String): Boolean {
    return url.startsWith("http://") || url.startsWith("https://")
}
