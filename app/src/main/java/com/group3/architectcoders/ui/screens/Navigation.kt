package com.group3.architectcoders.ui.screens

import android.os.BaseBundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavController
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
        composable("home") {
            HomeScreen(
                onBookClick = { book -> navController.navigate("detail/${book.id}") },
                onCamClick = { navController.navigate("camera") },
                onBookmarked = { book -> /* TODO: Save book */ },
            )
        }
        composable(
            route = "detail/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = requireNotNull(backStackEntry.arguments?.getString("bookId"))
            DetailScreen(
                viewModel = viewModel {
                    com.group3.architectcoders.ui.screens.detail.DetailViewModel(
                        bookId
                    )
                },
                onBack = { navController.popBackStack() },
                onBookmarked = { book -> /* TODO: Save book */ },
            )
        }
        composable("camera") {
            com.group3.architectcoders.ui.screens.camera.CameraScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navController.navigate("detail/${book.id}") },
            )
        }
    }
}
