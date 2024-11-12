package com.group3.architectcoders.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.group3.architectcoders.R

@Composable
fun BookmarIcon(isBookmarked: Boolean){
    Icon(
        imageVector = if (isBookmarked) Icons.Filled.BookmarkAdded else Icons.Outlined.BookmarkAdd,
        contentDescription = stringResource(id = R.string.bookmark),
    )
}
