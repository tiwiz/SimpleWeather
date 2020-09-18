package com.rob.simpleweather.main

fun String.extractIconUrl(): String =
    "https:${replace("64x64", "128x128")}"
