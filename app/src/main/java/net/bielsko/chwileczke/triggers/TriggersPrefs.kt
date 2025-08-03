package net.bielsko.chwileczke.triggers

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("triggers")

object TriggersPrefs {
    private val KEY_MESSAGES_FOR = stringPreferencesKey("messages_for")
    private val KEY_MESSAGES_FROM = stringPreferencesKey("messages_from")

    fun messagesFor(context: Context): Flow<String> =
        context.dataStore.data.map { it[KEY_MESSAGES_FOR] ?: "" }

    fun messagesFrom(context: Context): Flow<String> =
        context.dataStore.data.map { it[KEY_MESSAGES_FROM] ?: "" }

    suspend fun setMessagesFor(context: Context, value: String) {
        context.dataStore.edit { it[KEY_MESSAGES_FOR] = value }
    }

    suspend fun setMessagesFrom(context: Context, value: String) {
        context.dataStore.edit { it[KEY_MESSAGES_FROM] = value }
    }
}