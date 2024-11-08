package com.group3.architectcoders.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.group3.architectcoders.ui.navigation.Destination.Argument
import com.group3.architectcoders.ui.screens.camera.CameraScreen
import com.group3.architectcoders.ui.screens.detail.DetailScreen
import com.group3.architectcoders.ui.screens.detail.DetailViewModel
import com.group3.architectcoders.ui.screens.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.Home.route) {
        item(Destination.Home) {
            HomeScreen(
                onBookClick = { book ->
                    navController.navigateTo(
                        destination = Destination.Detail,
                        arguments = mapOf(Argument.BookId.name to book.id),
                    )
                },
                onCamClick = { navController.navigateTo(Destination.Camera) },
                onBookmarked = { book -> /* TODO: Save book */ },
            )
        }
        item(Destination.Detail) { backStackEntry ->
            val bookId = requireNotNull(backStackEntry.arguments?.getString(Argument.BookId.name))
            DetailScreen(
                viewModel = viewModel {
                    DetailViewModel(bookId)
                },
                onBack = { navController.popBackStack() },
                onBookmarked = { book -> /* TODO: Save book */ },
            )
        }
        item(Destination.Camera) {
            CameraScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book ->
                    navController.navigateTo(
                        destination = Destination.Detail,
                        arguments = mapOf(Argument.BookId.name to book.id),
                    )
                },
            )
        }
    }
}
