package com.example.tachiyomi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import com.example.tachiyomi.ui.components.NeomorphicCard
import com.example.tachiyomi.ui.components.NeomorphicSurface
import com.example.tachiyomi.ui.components.NeomorphicTopAppBar
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tachiyomi.data.SampleData
import com.example.tachiyomi.model.Manga

@Composable
fun BrowseScreen(
    onMangaClick: (Long) -> Unit
) {
    val mangaList = SampleData.sampleMangaList
    
    Scaffold(
        topBar = {
            NeomorphicTopAppBar(
                title = { Text("Browse") },
                actions = {
                    IconButton(onClick = { /* Search functionality */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(mangaList) { manga ->
                MangaListItem(
                    manga = manga,
                    onClick = { onMangaClick(manga.id) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun MangaListItem(
    manga: Manga,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(manga.isFavorite) }
    
    NeomorphicCard(
        modifier = modifier
            .fillMaxWidth(),
        cornerRadius = 16.dp,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NeomorphicCard(
                modifier = Modifier
                    .width(85.dp)
                    .height(125.dp),
                cornerRadius = 12.dp,
                shadowElevation = 4.dp
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
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = manga.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = manga.author,
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = manga.description,
                    style = MaterialTheme.typography.body2,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row {
                    manga.genres.take(3).forEach { genre ->
                        Chip(
                            text = genre,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
            }
            
            IconButton(
                onClick = { isFavorite = !isFavorite }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorite) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun Chip(
    text: String,
    modifier: Modifier = Modifier
) {
    NeomorphicSurface(
        modifier = modifier,
        cornerRadius = 8.dp,
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.primary
            )
        }
    }
}
