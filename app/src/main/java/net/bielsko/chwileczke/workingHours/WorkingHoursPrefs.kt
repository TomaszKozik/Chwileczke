package net.bielsko.chwileczke.workingHours

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "working_hours_prefs")

object WorkingHoursPrefs {
    private fun openedKey(day: Int) = booleanPreferencesKey("opened_$day")
    private fun wholeDayKey(day: Int) = booleanPreferencesKey("whole_day_$day")
    private fun fromKey(day: Int) = stringPreferencesKey("from_$day")
    private fun toKey(day: Int) = stringPreferencesKey("to_$day")

    fun getWorkingHours(context: Context, day: Int): Flow<WorkingHoursState> =
        context.dataStore.data.map { prefs ->
            WorkingHoursState(
                day = day,
                opened = prefs[openedKey(day)] ?: false,
                wholeDay = prefs[wholeDayKey(day)] ?: false,
                from = prefs[fromKey(day)] ?: "08:00",
                to = prefs[toKey(day)] ?: "16:00"
            )
        }

    suspend fun setWorkingHours(context: Context, state: WorkingHoursState) {
        context.dataStore.edit { prefs ->
            prefs[openedKey(state.day)] = state.opened
            prefs[wholeDayKey(state.day)] = state.wholeDay
            prefs[fromKey(state.day)] = state.from
            prefs[toKey(state.day)] = state.to
        }
    }

    suspend fun getWorkingHoursOnce(context: Context, day: Int): WorkingHoursState {
        val prefs = context.dataStore.data.first()
        return WorkingHoursState(
            day = day,
            opened = prefs[openedKey(day)] ?: true,  // domyślnie true
            wholeDay = prefs[wholeDayKey(day)] ?: false,
            from = prefs[fromKey(day)] ?: DEFAULT_START_HOUR,
            to = prefs[toKey(day)] ?: DEFAULT_END_HOUR
        )
    }
}