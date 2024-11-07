package com.group3.architectcoders.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        composable("home") {
            HomeScreen(
                onBookClick = { book -> navController.navigate("detail/${book.id}") },
                onCamClick = { navController.navigate("camera") },
                onBookmarked = { book -> /* TODO: Save book */ },
                viewModel = viewModel {
                    HomeViewModel(repository)
                }
            )
        }
        composable(
            route = "detail/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = requireNotNull(backStackEntry.arguments?.getString("bookId"))
            DetailScreen(
                viewModel = viewModel {
                    DetailViewModel(
                        repository = repository,
                        id = bookId
                    )
                },
                onBack = { navController.popBackStack() },
                onBookmarked = { book -> /* TODO: Save book */ },
            )
        }
        composable("camera") {
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
