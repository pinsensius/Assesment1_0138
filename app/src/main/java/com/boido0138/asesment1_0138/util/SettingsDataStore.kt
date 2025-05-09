package com.boido0138.asesment1_0138.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.boido0138.asesment1_0138.ui.theme.ThemeOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore : DataStore<Preferences> by preferencesDataStore(
    name = "settings_preference"
)

class SettingsDataStore(private val context: Context) {

    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")
        private val THEME_KEY = stringPreferencesKey("theme_key")
    }

    val layoutFlow: Flow<Boolean> = context.datastore.data.map { preferences ->
        preferences[IS_LIST] ?: true
    }

    val themeFlow: Flow<String> = context.datastore.data.map { preferences ->
        preferences[THEME_KEY] ?: ThemeOption.LightTheme.name
    }

    suspend fun saveLayout(isList: Boolean){
        context.datastore.edit { preferences ->
            preferences[IS_LIST] = isList
        }
    }

    suspend fun saveTheme(theme : String){
        context.datastore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }
}