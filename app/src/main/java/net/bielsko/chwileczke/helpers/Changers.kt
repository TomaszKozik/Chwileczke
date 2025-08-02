package net.bielsko.chwileczke.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun AddUrlToMessage(
    msgToChange: MutableState<String>,
    linkDesc: MutableState<String>,
    link: MutableState<String>,
    isAddLinkEnabled: MutableState<Boolean>
) {
    val originalMessage = remember { mutableStateOf("") }

    LaunchedEffect(isAddLinkEnabled.value, linkDesc.value, link.value) {
        if (originalMessage.value.isEmpty()) {
            originalMessage.value = msgToChange.value
        }
        if (isAddLinkEnabled.value) {
            msgToChange.value = buildString {
                append(originalMessage.value)
                if (linkDesc.value.isNotBlank() || link.value.isNotBlank()) {
                    append("\n\n")
                    append(linkDesc.value)
                    append("\n")
                    append(link.value)
                }
            }
        } else {
            msgToChange.value = originalMessage.value
        }
    }
}