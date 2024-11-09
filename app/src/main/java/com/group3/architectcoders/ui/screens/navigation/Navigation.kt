package com.group3.architectcoders.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val navigationAction: NavigationAction = DefaultNavigationAction(navController)

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onBookClick = { book -> navigationAction.onBookClick(book.id) },
                onCamClick = { navigationAction.onCamClick() },
                onBookmarked = { book -> navigationAction.onBookmarked(book.id) },
            )
        }
        composable(
            route = "detail/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = requireNotNull(backStackEntry.arguments?.getString("bookId"))
            DetailScreen(
                viewModel = viewModel {
                    com.group3.architectcoders.ui.screens.detail.DetailViewModel(bookId)
                },
                onBack = { navController.popBackStack() },
                onBookmarked = { book -> navigationAction.onBookmarked(book.id) },
            )
        }
        composable("camera") {
            com.group3.architectcoders.ui.screens.camera.CameraScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navigationAction.onBookClick(book.id) },
            )
        }
    }
}
