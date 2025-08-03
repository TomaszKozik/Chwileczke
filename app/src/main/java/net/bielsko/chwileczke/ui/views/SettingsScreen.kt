package net.bielsko.chwileczke.ui.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.bielsko.chwileczke.holidays.HolidayAvailabilitySection
import net.bielsko.chwileczke.triggers.Triggers
import net.bielsko.chwileczke.workingHours.WorkingHoursDisplay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        HolidayAvailabilitySection()

        Spacer(modifier = Modifier.height(16.dp))

        WorkingHoursDisplay()

        Triggers()

        Spacer(modifier = Modifier.height(16.dp))
    }
}
