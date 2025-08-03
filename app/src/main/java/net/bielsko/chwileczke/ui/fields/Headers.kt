package net.bielsko.chwileczke.ui.fields

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import net.bielsko.chwileczke.R

@Composable
fun Header1(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleLarge,
        color = colorResource(id = R.color.primary_blue)
    )
}

@Composable
fun Header2(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleMedium,
        color = colorResource(id = R.color.primary_blue)
    )
}


@Composable
fun Header3(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleSmall,
        color = colorResource(id = R.color.primary_blue)
    )
}