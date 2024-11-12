package com.group3.architectcoders.ui.navigation

import androidx.navigation.NamedNavArgument

sealed class Destination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Home : Destination(route = Routes.HOME.createRoute())
    object Camera : Destination(route = Routes.CAMERA.createRoute())
    object Detail :
        Destination(
            route = Routes.DETAIL.createRoute(listOf(Argument.BookId)),
            arguments = listOf(Argument.BookId).toNamedArguments()
        )

    enum class Routes {
        HOME,
        DETAIL,
        CAMERA
    }

    sealed class Argument(val name: String, val type: String) {
        object BookId : Argument(Names.BOOK_ID.name, String::class.toString())

        enum class Names {
            BOOK_ID
        }
    }
}
