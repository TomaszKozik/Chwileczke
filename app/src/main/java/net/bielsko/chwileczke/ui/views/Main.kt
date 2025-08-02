package net.bielsko.chwileczke.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.bielsko.chwileczke.MessageScreen
import net.bielsko.chwileczke.R
import net.bielsko.chwileczke.SettingsScreen

@Composable
fun MainView() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(R.string.tab_messages, R.string.tab_settings)

    Scaffold(
        topBar = {
            NavBar()
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                TabRow(
                    selectedTabIndex = selectedTab,
                    backgroundColor = colorResource(id = R.color.white),
                    contentColor = colorResource(id = R.color.primary_blue)
                ) {
                    tabs.forEachIndexed { index, titleResId ->
                        val title = stringResource(id = titleResId)
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    modifier = Modifier.semantics {
                                        contentDescription = title
                                    }
                                )
                            }
                        )
                    }

                }
                when (selectedTab) {
                    0 -> MessageScreen()
                    1 -> SettingsScreen()
                }
            }
        }
    )
}

@Composable
fun NavBar() {
    val appName = stringResource(id = R.string.app_name)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.primary_blue))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = stringResource(id = R.string.logo, appName),
            tint = colorResource(id = R.color.white),
            modifier = Modifier
                .size(32.dp)
                .padding(end = 8.dp)
        )
        Text(
            text = appName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            modifier = Modifier.semantics {
                heading()
                this.contentDescription = appName
            }
        )

    }
}

