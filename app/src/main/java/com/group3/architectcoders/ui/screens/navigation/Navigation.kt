package com.group3.architectcoders.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.group3.architectcoders.ui.screens.detail.DetailScreen
import com.group3.architectcoders.ui.screens.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable(NavItem.Home) {
            HomeScreen(
                onBookClick = { book -> navController.navigate(NavItem.Detail.createRoute(book.id)) },
                onCamClick = { navController.navigate(NavItem.Camera.route) },
                onBookmarked = { book -> /* TODO: Save book */ },
            )
        }
        composable(NavItem.Detail) { backStackEntry ->
            DetailScreen(
                viewModel = viewModel {
                    com.group3.architectcoders.ui.screens.detail.DetailViewModel(
                        backStackEntry.findArg(
                            NavArg.BookId
                        )
                    )
                },
                onBack = { navController.popBackStack() },
                onBookmarked = { book -> /* TODO: Save book */ },
            )
        }
        composable(NavItem.Camera) {
            com.group3.architectcoders.ui.screens.camera.CameraScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navController.navigate("detail/${book.id}") },
            )
        }
    }
}

private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navItem.route,
        arguments = navItem.args
    ) {
        content(it)
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(arg: NavArg): T {
    requireNotNull(arguments)

    val value: Any? = when (T::class) {
        String::class -> arguments?.getString(arg.key)
        Int::class -> arguments?.getInt(arg.key)
        Boolean::class -> arguments?.getBoolean(arg.key)
        Float::class -> arguments?.getFloat(arg.key)
        Long::class -> arguments?.getLong(arg.key)
        else -> null
    }
    requireNotNull(value)
    return value as T
}
