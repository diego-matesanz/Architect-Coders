package com.group3.architectcoders.ui.navigation

import androidx.navigation.NavHostController
import com.group3.architectcoders.ui.navigation.Destination.Argument

class Navigator(val navController: NavHostController) : INavigator {

    override fun navigateToHome() = navController.navigateTo(Destination.Home)
    override fun navigateToCamera() = navController.navigateTo(Destination.Camera)

    override fun navigateToDetail(bookId: String) {
        navController.navigateTo(
            destination = Destination.Detail,
            arguments = mapOf(Argument.BookId.name to bookId),
        )
    }

    override fun navigateBack() {
        navController.popBackStack()
    }
}

private interface INavigator {
    fun navigateToHome()
    fun navigateToCamera()
    fun navigateToDetail(bookId: String)
    fun navigateBack()
}
