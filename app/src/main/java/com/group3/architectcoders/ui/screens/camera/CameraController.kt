package com.group3.architectcoders.ui.screens.camera

import com.group3.architectcoders.data.local.Book
import com.group3.architectcoders.ui.navigation.Navigator

class CameraController(
    val viewModel: CameraViewModel,
    val navigator: Navigator,
) : ICameraController {

    override fun onBack() = navigator.navigateBack()
    override fun onBookClick(book: Book) = navigator.navigateToDetail(book.id)
    override fun searchBook(isbn: String) = viewModel.fetchBookByIsbn(isbn)
}

private interface ICameraController {
    fun onBack()
    fun onBookClick(book: Book)
    fun searchBook(isbn: String)
}
