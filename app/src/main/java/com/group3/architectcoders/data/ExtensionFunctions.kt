package com.group3.architectcoders.data

fun String.toHttps(): String = replace(Regex("^http://"), "https://")
