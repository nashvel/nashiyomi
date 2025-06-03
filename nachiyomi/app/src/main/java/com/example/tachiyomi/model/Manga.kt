package com.example.tachiyomi.model

data class Manga(
    val id: Long,
    val title: String,
    val thumbnailUrl: String,
    val description: String = "",
    val author: String = "",
    val status: MangaStatus = MangaStatus.UNKNOWN,
    val source: String = "",
    val genres: List<String> = emptyList(),
    val isFavorite: Boolean = false,
    val chapters: List<Chapter> = emptyList()
)

enum class MangaStatus {
    ONGOING,
    COMPLETED,
    LICENSED,
    PUBLISHING_FINISHED,
    CANCELLED,
    HIATUS,
    UNKNOWN
}
