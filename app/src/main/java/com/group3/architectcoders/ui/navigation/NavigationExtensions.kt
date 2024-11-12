package com.group3.architectcoders.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.group3.architectcoders.ui.navigation.Destination.Argument
import com.group3.architectcoders.ui.navigation.Destination.Routes

fun NavController.navigateTo(destination: Destination, arguments: Map<String, Any> = emptyMap()) {
    var route = destination.route
    if (arguments.isNotEmpty()) {
        destination.arguments.forEach { argument ->
            route = route.replace("{${argument.name}}", arguments[argument.name].toString())
        }
    }
    navigate(route)
}

fun NavGraphBuilder.item(
    destination: Destination,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
    ) {
        content(it)
    }
}

fun Routes.createRoute(arguments: List<Argument> = emptyList()): String {
    var route = name
    arguments.forEach { route += "/{${it.name}}" }
    return route
}

fun List<Argument>.toNamedArguments(): List<NamedNavArgument> =
    map { argument ->
        when (argument.type) {
            String::class.toString() -> navArgument(argument.name) { type = NavType.StringType }
            else -> throw IllegalArgumentException("Unsupported argument type: ${argument.type}")
        }
    }
