package com.group3.architectcoders.ui.screens.home

import com.group3.architectcoders.data.Book
import com.group3.architectcoders.ui.navigation.Navigator

class HomeController(
    val viewModel: HomeViewModel,
    val navigator: Navigator,
) : IHomeController {

    override fun onBookClick(book: Book) = navigator.navigateToDetail(book.id)
    override fun onCamClick() = navigator.navigateToCamera()
    override fun onSearch(search: String) = viewModel.fetchBooksBySearch(search)
    override fun onBookMarked(book: Book) {}
}

private interface IHomeController {
    fun onBookClick(book: Book)
    fun onCamClick()
    fun onSearch(search: String)
    fun onBookMarked(book: Book)
}
