package com.group3.architectcoders.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.group3.architectcoders.data.BooksClient
import com.group3.architectcoders.data.BooksRepository
import com.group3.architectcoders.domain.GetBookByIdUseCase
import com.group3.architectcoders.domain.GetBookByIsbnUseCase
import com.group3.architectcoders.domain.GetBooksBySearchUseCase
import com.group3.architectcoders.ui.navigation.Destination.Argument
import com.group3.architectcoders.ui.screens.camera.CameraController
import com.group3.architectcoders.ui.screens.camera.CameraScreen
import com.group3.architectcoders.ui.screens.camera.CameraViewModel
import com.group3.architectcoders.ui.screens.detail.DetailController
import com.group3.architectcoders.ui.screens.detail.DetailScreen
import com.group3.architectcoders.ui.screens.detail.DetailViewModel
import com.group3.architectcoders.ui.screens.home.HomeController
import com.group3.architectcoders.ui.screens.home.HomeScreen
import com.group3.architectcoders.ui.screens.home.HomeViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navigator by remember { mutableStateOf(Navigator(navController)) }
    val repository = BooksRepository(BooksClient.instance)

    NavHost(navController = navController, startDestination = Destination.Home.route) {
        item(Destination.Home) {
            val homeController = HomeController(
                viewModel = viewModel { HomeViewModel(GetBooksBySearchUseCase(repository)) },
                navigator = navigator,
            )
            HomeScreen(controller = homeController)
        }
        item(Destination.Detail) { backStackEntry ->
            val bookId = requireNotNull(backStackEntry.arguments?.getString(Argument.BookId.name))
            val detailController = DetailController(
                viewModel = viewModel {
                    DetailViewModel(
                        id = bookId,
                        getBookByIdUseCase = GetBookByIdUseCase(repository)
                    )
                },
                navigator = navigator,
            )
            DetailScreen(controller = detailController)
        }
        item(Destination.Camera) {
            val cameraController = CameraController(
                viewModel = viewModel { CameraViewModel(GetBookByIsbnUseCase(repository)) },
                navigator = navigator,
            )
            CameraScreen(controller = cameraController)
        }
    }
}
