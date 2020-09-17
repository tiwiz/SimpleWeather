package com.rob.simpleweather.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val entries: Array<SearchEntry>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchResponse

        if (!entries.contentEquals(other.entries)) return false

        return true
    }

    override fun hashCode(): Int {
        return entries.contentHashCode()
    }
}

@Serializable
data class SearchEntry(
    val id: Int,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val url: String
)
