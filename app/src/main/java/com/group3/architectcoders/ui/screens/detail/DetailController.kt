package com.group3.architectcoders.ui.screens.detail

import com.group3.architectcoders.data.local.Book
import com.group3.architectcoders.ui.navigation.Navigator

class DetailController(
    val viewModel: DetailViewModel,
    val navigator: Navigator,
) : IDetailController {

    override fun onBack() = navigator.navigateBack()
    override fun onBookmarked(book: Book) {
        //
    }
    override fun onDominantColor(color: Int) = viewModel.onDominantColor(color)
}

private interface IDetailController {
    fun onBack()
    fun onBookmarked(book: Book)
    fun onDominantColor(color: Int)
}
