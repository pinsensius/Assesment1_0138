package com.boido0138.asesment1_0138.navigation

sealed class Screen ( val route : String ){
    data object Home : Screen("homeScreen")
}
