package com.group3.architectcoders.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.group3.architectcoders.data.BooksRepository
import com.group3.architectcoders.data.IBooksRepository
import com.group3.architectcoders.ui.screens.camera.CameraScreen
import com.group3.architectcoders.ui.screens.camera.CameraViewModel
import com.group3.architectcoders.ui.screens.detail.DetailScreen
import com.group3.architectcoders.ui.screens.detail.DetailViewModel
import com.group3.architectcoders.ui.screens.home.HomeScreen
import com.group3.architectcoders.ui.screens.home.HomeViewModel
import com.group3.architectcoders.ui.screens.navigation.Screen.Detail.BOOK_ID_ARG

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navigationAction = DefaultNavigationAction(navController)
    val booksRepository: IBooksRepository = BooksRepository()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onBookClick = { book -> navigationAction.onBookClick(book.id) },
                onCamClick = { navigationAction.onCamClick() },
                onBookmarked = { book -> navigationAction.onBookmarked(book.id) },
                viewModel = HomeViewModel(booksRepository)
            )
        }
        composable(
            route = Screen.Detail.route + BOOK_ID_ARG,
            arguments = listOf(navArgument(BOOK_ID_ARG) { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = requireNotNull(backStackEntry.arguments?.getString(BOOK_ID_ARG))
            DetailScreen(
                viewModel = DetailViewModel(bookId, booksRepository),
                onBack = { navController.popBackStack() },
                onBookmarked = { book -> navigationAction.onBookmarked(book.id) },
            )
        }
        composable(Screen.Camera.route) {
            CameraScreen(
                viewModel = CameraViewModel(booksRepository),
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navigationAction.onBookClick(book.id) },
            )
        }
    }
}
