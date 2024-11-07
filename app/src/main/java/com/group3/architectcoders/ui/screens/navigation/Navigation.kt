package com.group3.architectcoders.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.group3.architectcoders.data.BooksClient
import com.group3.architectcoders.data.BooksRepository
import com.group3.architectcoders.data.BooksRepositoryImpl
import com.group3.architectcoders.ui.screens.camera.CameraViewModel
import com.group3.architectcoders.ui.screens.detail.DetailScreen
import com.group3.architectcoders.ui.screens.detail.DetailViewModel
import com.group3.architectcoders.ui.screens.home.HomeScreen
import com.group3.architectcoders.ui.screens.home.HomeViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val repository: BooksRepository =
        remember { BooksRepositoryImpl(BooksClient.getBooksService()) }

    NavHost(navController = navController, startDestination = "home") {
        composable(NavItem.Home) {
            HomeScreen(
                onBookClick = { book -> navController.navigate(NavItem.Detail.createRoute(book.id)) },
                onCamClick = { navController.navigate(NavItem.Camera.route) },
                onBookmarked = { book -> /* TODO: Save book */ },
                viewModel = viewModel {
                    HomeViewModel(repository)
                }
            )
        }
        composable(NavItem.Detail) { backStackEntry ->
            DetailScreen(
                viewModel = viewModel {
                    DetailViewModel(
                        repository,
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
                viewModel = viewModel {
                    CameraViewModel(repository)
                },
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
