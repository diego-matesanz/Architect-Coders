package com.group3.architectcoders.ui.screens.navigation

interface NavigationAction {
    fun onBookClick(bookId : String)
    fun onCamClick()
    fun onBookmarked(bookId : String)
}