package com.group3.architectcoders.ui.screens.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    val baseRoute: String,
    private val navArgs: List<NavArg> = emptyList()
) {
    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map { arg ->
        navArgument(arg.key) { type = arg.navType }
    }
    data object Home : NavItem("home")
    data object Detail : NavItem("detail", listOf(NavArg.BookId)) {
        fun createRoute(bookId: String) = "$baseRoute/$bookId"
    }

    data object Camera : NavItem("camera")

}

enum class NavArg(val key: String, val navType: NavType<*>) {
    BookId("bookId", NavType.StringType)
}