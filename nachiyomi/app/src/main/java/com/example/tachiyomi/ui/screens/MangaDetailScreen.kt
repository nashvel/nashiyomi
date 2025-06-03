package com.example.tachiyomi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.ui.layout.Layout
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tachiyomi.data.SampleData
import com.example.tachiyomi.model.Chapter
import com.example.tachiyomi.model.Manga
import com.example.tachiyomi.model.MangaStatus
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MangaDetailScreen(
    mangaId: Long,
    onBackClick: () -> Unit,
    onChapterClick: (Long) -> Unit
) {
    val manga = SampleData.sampleMangaList.find { it.id == mangaId } ?: return
    val chapters = SampleData.sampleChapters.filter { it.mangaId == mangaId }
    
    var isFavorite by remember { mutableStateOf(manga.isFavorite) }
    var isExpanded by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(manga.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { isFavorite = !isFavorite }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (isFavorite) MaterialTheme.colors.secondary else Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            item {
                MangaHeader(manga = manga, isExpanded = isExpanded, onExpandClick = { isExpanded = !isExpanded })
            }
            
            item {
                ChaptersHeader(chapters.size)
            }
            
            items(chapters.sortedByDescending { it.number }) { chapter ->
                ChapterItem(
                    chapter = chapter,
                    onClick = { onChapterClick(chapter.id) }
                )
            }
        }
    }
}

@Composable
fun MangaHeader(
    manga: Manga,
    isExpanded: Boolean,
    onExpandClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp),
                elevation = 4.dp
            ) {
                AsyncImage(
                    model = manga.thumbnailUrl,
                    contentDescription = manga.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = manga.title,
                    style = MaterialTheme.typography.h6
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = manga.author,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                StatusChip(status = manga.status)
                
                Spacer(modifier = Modifier.height(8.dp))
                
                FlowRow {
                    manga.genres.forEach { genre ->
                        Chip(
                            text = genre,
                            modifier = Modifier.padding(end = 4.dp, bottom = 4.dp)
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Column {
            Text(
                text = "Summary",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primary
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = manga.description,
                style = MaterialTheme.typography.body2,
                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            
            if (manga.description.length > 150) {
                TextButton(
                    onClick = onExpandClick,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(if (isExpanded) "Show less" else "Show more")
                }
            }
        }
        
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun StatusChip(status: MangaStatus) {
    val (backgroundColor, textColor) = when (status) {
        MangaStatus.ONGOING -> MaterialTheme.colors.primary.copy(alpha = 0.1f) to MaterialTheme.colors.primary
        MangaStatus.COMPLETED -> Color.Green.copy(alpha = 0.1f) to Color.Green.copy(alpha = 0.8f)
        MangaStatus.HIATUS -> Color.Yellow.copy(alpha = 0.1f) to Color.DarkGray
        MangaStatus.CANCELLED -> Color.Red.copy(alpha = 0.1f) to Color.Red.copy(alpha = 0.8f)
        else -> MaterialTheme.colors.onSurface.copy(alpha = 0.1f) to MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
    }
    
    Surface(
        color = backgroundColor,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = status.name,
            color = textColor,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ChaptersHeader(count: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Chapters ($count)",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primary
        )
        
        Row {
            IconButton(onClick = { /* Filter functionality */ }) {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = "Filter",
                    tint = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            }
            
            IconButton(onClick = { /* Sort functionality */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Sort",
                    tint = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun ChapterItem(
    chapter: Chapter,
    onClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = chapter.name,
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = if (chapter.read) MaterialTheme.colors.onSurface.copy(alpha = 0.5f) else MaterialTheme.colors.onSurface
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ch. ${chapter.number}",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                    
                    Text(
                        text = " • ${dateFormat.format(chapter.dateUpload)}",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                    
                    if (chapter.read) {
                        Text(
                            text = " • Read",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.primary.copy(alpha = 0.8f)
                        )
                    }
                }
            }
            
            if (chapter.read) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Read",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Read",
                    tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Use a simple Row as a replacement for the custom layout
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        content()
    }
}
