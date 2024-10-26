package com.group3.architectcoders.ui.screens.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.group3.architectcoders.R
import com.group3.architectcoders.data.Book
import com.group3.architectcoders.ui.common.CustomAsyncImage
import com.group3.architectcoders.ui.common.HtmlText
import com.group3.architectcoders.ui.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBack: () -> Unit,
    onBookmarked: (Book) -> Unit,
) {
    val state = viewModel.state

    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.go_back),
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = if (state.dominantColor != 0) Color(state.dominantColor) else Color.Transparent,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                )
            }
        ) { padding ->
            if (state.isLoading) {
                DetailLoader(padding = padding)
            } else {
                state.book?.let { book ->
                    Box {
                        BookDetail(
                            book = book,
                            dominantColor = state.dominantColor,
                            onDominantColor = viewModel::onDominantColor,
                            modifier = Modifier.padding(padding),
                        )

                        var bookSaved by remember { mutableStateOf(false) }
                        FloatingActionButton(
                            onClick = {
                                bookSaved = !bookSaved
                                onBookmarked(book)
                            },
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(32.dp),
                        ) {
                            Icon(
                                imageVector = if (bookSaved) Icons.Filled.BookmarkAdded else Icons.Outlined.BookmarkAdd,
                                contentDescription = stringResource(id = R.string.bookmark),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookDetail(
    book: Book,
    dominantColor: Int,
    onDominantColor: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Box {
            Box(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        val height = (scrollState.value / 3F).toInt()
                        layout(placeable.width, placeable.height) {
                            placeable.place(0, height)
                        }
                    }
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(if (dominantColor != 0) Color(dominantColor) else Color.Transparent)
                    .align(Alignment.TopCenter)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 80.dp)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                CustomAsyncImage(
                    model = book.coverImage,
                    contentDescription = book.title,
                    modifier = Modifier
                        .height(270.dp)
                        .aspectRatio(1 / 1.5F),
                ) { color ->
                    onDominantColor(color)
                }
                TitleSection(
                    title = book.title,
                    author = book.authors.first()
                )
                InfoSection(
                    rating = book.averageRating,
                    pages = book.pageCount,
                    language = book.language
                )
                if (book.description.isNotEmpty()) {
                    DescriptionSection(book.description)
                }
            }
        }
    }
}

@Composable
private fun TitleSection(
    title: String,
    author: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )
        Text(
            text = author,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun InfoSection(
    rating: Double,
    pages: Int,
    language: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
    ) {
        if (rating > 0) {
            InfoItem(
                title = stringResource(id = R.string.rating),
                description = rating.toString()
            )
        }
        if (pages > 0) {
            InfoItem(
                title = stringResource(id = R.string.pages),
                description = pages.toString()
            )
        }
        if (language.isNotEmpty()) {
            InfoItem(
                title = stringResource(id = R.string.language),
                description = language
            )
        }
    }
}

@Composable
private fun InfoItem(
    title: String,
    description: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.Center,
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun DescriptionSection(description: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
        )

        var isExpanded by remember { mutableStateOf(false) }
        HtmlText(
            modifier = Modifier.animateContentSize(),
            text = description,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
            maxLines = if (isExpanded) Int.MAX_VALUE else 5,
            textAlign = TextAlign.Start,
        )
        Text(
            text = if (isExpanded) stringResource(id = R.string.read_less) else stringResource(id = R.string.read_more),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.End,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { isExpanded = !isExpanded }
        )
    }
}
