package com.boido0138.asesment1_0138

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.boido0138.asesment1_0138.navigation.SetupNavGraph
import com.boido0138.asesment1_0138.ui.theme.Asesment1_0138Theme
import com.boido0138.asesment1_0138.ui.theme.ThemeOption
import com.boido0138.asesment1_0138.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val settingsDataStore = remember { SettingsDataStore(this) }
            val theme = settingsDataStore.themeFlow.collectAsState(initial = ThemeOption.LightTheme.name).value

            Asesment1_0138Theme (
                theme = theme,
                content = {
                    SetupNavGraph()
                }
            )
        }
    }
}


