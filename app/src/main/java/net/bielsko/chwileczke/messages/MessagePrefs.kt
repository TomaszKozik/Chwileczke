package net.bielsko.chwileczke.messages

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.messageDataStore by preferencesDataStore(name = "message_prefs")

object MessagePrefs {
    private val IN_WORK_MESSAGE_KEY = stringPreferencesKey("in_work_message")
    private val OUT_OF_WORK_MESSAGE_KEY = stringPreferencesKey("out_of_work_message")
    private val HOLIDAY_MESSAGE_KEY = stringPreferencesKey("holiday_message")
    private val ADD_LINK_TEXT_KEY = stringPreferencesKey("add_link_text")
    private val ADD_LINK_URL_KEY = stringPreferencesKey("add_link_url")
    private val ADD_LINK_ENABLED_KEY = booleanPreferencesKey("add_link_enabled")

    fun getInWorkMessage(context: Context): Flow<String> =
        context.messageDataStore.data.map { it[IN_WORK_MESSAGE_KEY] ?: "" }

    suspend fun setInWorkMessage(context: Context, message: String) {
        context.messageDataStore.edit { prefs -> prefs[IN_WORK_MESSAGE_KEY] = message }
    }

    fun getOutOfWorkMessage(context: Context): Flow<String> =
        context.messageDataStore.data.map { it[OUT_OF_WORK_MESSAGE_KEY] ?: "" }

    suspend fun setOutOfWorkMessage(context: Context, message: String) {
        context.messageDataStore.edit { prefs -> prefs[OUT_OF_WORK_MESSAGE_KEY] = message }
    }

    fun getHolidayMessage(context: Context): Flow<String> =
        context.messageDataStore.data.map { it[HOLIDAY_MESSAGE_KEY] ?: "" }

    suspend fun setHolidayMessage(context: Context, message: String) {
        context.messageDataStore.edit { prefs -> prefs[HOLIDAY_MESSAGE_KEY] = message }
    }

    fun getAddLinkText(context: Context): Flow<String> =
        context.messageDataStore.data.map { it[ADD_LINK_TEXT_KEY] ?: "" }

    suspend fun setAddLinkText(context: Context, text: String) {
        context.messageDataStore.edit { prefs -> prefs[ADD_LINK_TEXT_KEY] = text }
    }

    fun getAddLinkUrl(context: Context): Flow<String> =
        context.messageDataStore.data.map { it[ADD_LINK_URL_KEY] ?: "" }

    suspend fun setAddLinkUrl(context: Context, url: String) {
        context.messageDataStore.edit { prefs -> prefs[ADD_LINK_URL_KEY] = url }
    }

    fun isAddLinkEnabled(context: Context): Flow<Boolean> =
        context.messageDataStore.data.map { it[ADD_LINK_ENABLED_KEY] ?: false }

    suspend fun setAddLinkEnabled(context: Context, enabled: Boolean) {
        context.messageDataStore.edit { prefs -> prefs[ADD_LINK_ENABLED_KEY] = enabled }
    }

    suspend fun initDefaultMessageIfEmpty(
        context: Context,
        key: suspend (Context) -> Flow<String>,
        setter: suspend (Context, String) -> Unit,
        defaultValue: String
    ) {
        val currentValue = key(context).first()
        if (currentValue.isBlank()) {
            setter(context, defaultValue)
        }
    }
}
