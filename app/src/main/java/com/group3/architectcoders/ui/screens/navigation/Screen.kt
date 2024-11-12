package com.group3.architectcoders.ui.screens.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{bookId}") {
        const val BOOK_ID_ARG = "bookId"
    }
    object Camera : Screen("camera")
}
