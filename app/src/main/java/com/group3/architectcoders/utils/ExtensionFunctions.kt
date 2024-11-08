package com.group3.architectcoders.utils

fun String.toHttps(): String = replace(Regex("^http://"), "https://")
