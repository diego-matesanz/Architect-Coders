package com.group3.architectcoders.ui.screens.navigation

import androidx.navigation.NavController

class DefaultNavigationAction(
    private val navController: NavController
) : BookNavigationAction, CameraNavigationAction, BookmarkAction {

    override fun onBookClick(bookId: String) {
        navController.navigate("detail/$bookId")
    }

    override fun onCamClick() {
        navController.navigate("camera")
    }

    override fun onBookmarked(bookId: String) {
        // TODO: Save book logic
    }
}
