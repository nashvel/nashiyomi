package com.example.tachiyomi.model

import java.util.Date

data class Chapter(
    val id: Long,
    val mangaId: Long,
    val name: String,
    val number: Float,
    val dateUpload: Date,
    val scanlator: String? = null,
    val read: Boolean = false,
    val bookmark: Boolean = false,
    val lastPageRead: Int = 0,
    val pageCount: Int = 0,
    val pages: List<Page> = emptyList()
)

data class Page(
    val index: Int,
    val imageUrl: String,
    val isLoaded: Boolean = false
)
