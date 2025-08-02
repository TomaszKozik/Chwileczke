package net.bielsko.chwileczke.ui.views

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.ui.fields.DropdownMenuWithLabel

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
            WorkingHours(dzien)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}