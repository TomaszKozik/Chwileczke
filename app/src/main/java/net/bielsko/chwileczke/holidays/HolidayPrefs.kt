package net.bielsko.chwileczke.holidays

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property na DataStore w kontekście aplikacji
val Context.dataStore by preferencesDataStore(name = "holiday_prefs")

object HolidayPrefs {

    // Klucze preferencji
    private val HOLIDAY_CHECKED = booleanPreferencesKey("holiday_checked")
    private val HOLIDAY_DATE_FROM = stringPreferencesKey("holiday_date_from")
    private val HOLIDAY_DATE_TO = stringPreferencesKey("holiday_date_to")

    // Flows do odczytu wartości
    fun isHolidayChecked(context: Context): Flow<Boolean> =
        context.dataStore.data.map { prefs -> prefs[HOLIDAY_CHECKED] ?: false }

    fun holidayDateFrom(context: Context): Flow<String> =
        context.dataStore.data.map { prefs -> prefs[HOLIDAY_DATE_FROM] ?: "" }

    fun holidayDateTo(context: Context): Flow<String> =
        context.dataStore.data.map { prefs -> prefs[HOLIDAY_DATE_TO] ?: "" }

    // Funkcje do zapisu wartości
    suspend fun setHolidayChecked(context: Context, checked: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[HOLIDAY_CHECKED] = checked
        }
    }

    suspend fun setHolidayDateFrom(context: Context, value: String) {
        context.dataStore.edit { prefs ->
            prefs[HOLIDAY_DATE_FROM] = value
        }
    }

    suspend fun setHolidayDateTo(context: Context, value: String) {
        context.dataStore.edit { prefs ->
            prefs[HOLIDAY_DATE_TO] = value
        }
    }
}
