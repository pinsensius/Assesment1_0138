package com.boido0138.asesment1_0138

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.boido0138.asesment1_0138.navigation.SetupNavGraph
import com.boido0138.asesment1_0138.ui.theme.Asesment1_0138Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Asesment1_0138Theme {
                    SetupNavGraph()
            }
        }
    }
}


