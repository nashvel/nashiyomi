package com.example.tachiyomi.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tachiyomi.data.SampleData
import com.example.tachiyomi.model.Manga
import com.example.tachiyomi.ui.components.NeomorphicCard
import com.example.tachiyomi.ui.components.NeomorphicSurface
import com.example.tachiyomi.ui.components.NeomorphicTopAppBar

@Composable
fun LibraryScreen(
    onMangaClick: (Long) -> Unit
) {
    val mangaList = SampleData.sampleMangaList.filter { it.isFavorite }
    
    Scaffold(
        topBar = {
            NeomorphicTopAppBar(
                title = { Text("Library") },
                actions = {
                    IconButton(onClick = { /* Search functionality */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { padding ->
        if (mangaList.isEmpty()) {
            EmptyLibrary(modifier = Modifier.padding(padding))
        } else {
            LibraryGrid(
                mangaList = mangaList,
                onMangaClick = onMangaClick,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
fun LibraryGrid(
    mangaList: List<Manga>,
    onMangaClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {
        items(mangaList) { manga ->
            MangaGridItem(
                manga = manga,
                onClick = { onMangaClick(manga.id) },
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun MangaGridItem(
    manga: Manga,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .width(128.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(170.dp)
                .fillMaxWidth()
        ) {
            NeomorphicCard(
                modifier = Modifier.fillMaxSize(),
                cornerRadius = 12.dp,
                shadowElevation = 6.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    AsyncImage(
                        model = manga.thumbnailUrl,
                        contentDescription = manga.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = manga.title,
            style = MaterialTheme.typography.subtitle2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EmptyLibrary(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        NeomorphicCard(
            modifier = Modifier
                .width(280.dp)
                .padding(16.dp),
            cornerRadius = 16.dp,
            shadowElevation = 8.dp
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Your library is empty",
                    style = MaterialTheme.typography.h6
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Add manga from the Browse tab",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
