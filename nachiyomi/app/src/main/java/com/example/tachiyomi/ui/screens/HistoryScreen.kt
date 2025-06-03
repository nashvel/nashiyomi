package com.example.tachiyomi.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
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
import java.text.SimpleDateFormat
import java.util.*

data class HistoryEntry(
    val manga: Manga,
    val chapter: Chapter,
    val lastReadAt: Date
)

@Composable
fun HistoryScreen(
    onMangaClick: (Long) -> Unit,
    onChapterClick: (Long) -> Unit
) {
    // Generate some history entries from sample data
    val historyEntries = remember {
        val calendar = Calendar.getInstance()
        val entries = mutableListOf<HistoryEntry>()
        
        // Create some history entries with different timestamps
        SampleData.sampleMangaList.take(3).forEach { manga ->
            val chapters = SampleData.sampleChapters.filter { it.mangaId == manga.id }
            chapters.take(2).forEach { chapter ->
                // Set last read time between now and 7 days ago
                calendar.time = Date()
                calendar.add(Calendar.DAY_OF_YEAR, -(1..7).random())
                
                entries.add(
                    HistoryEntry(
                        manga = manga,
                        chapter = chapter.copy(read = true),
                        lastReadAt = calendar.time
                    )
                )
            }
        }
        
        // Sort by most recently read
        entries.sortedByDescending { it.lastReadAt }
    }
    
    var showEmptyState by remember { mutableStateOf(historyEntries.isEmpty()) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("History") },
                actions = {
                    IconButton(onClick = { /* Search functionality */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /* Clear history functionality */ }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Clear history")
                    }
                }
            )
        }
    ) { padding ->
        if (showEmptyState) {
            EmptyHistoryState(modifier = Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(historyEntries) { historyEntry ->
                    HistoryItem(
                        historyEntry = historyEntry,
                        onMangaClick = { onMangaClick(historyEntry.manga.id) },
                        onChapterClick = { onChapterClick(historyEntry.chapter.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryItem(
    historyEntry: HistoryEntry,
    onMangaClick: () -> Unit,
    onChapterClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy, HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(historyEntry.lastReadAt)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onMangaClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .width(60.dp)
                    .height(90.dp),
                elevation = 4.dp
            ) {
                AsyncImage(
                    model = historyEntry.manga.thumbnailUrl,
                    contentDescription = historyEntry.manga.title,
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
                    text = historyEntry.manga.title,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = historyEntry.chapter.name,
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable(onClick = onChapterClick)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "Last read: $formattedDate",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                val progress = historyEntry.chapter.lastPageRead.toFloat() / historyEntry.chapter.pageCount.toFloat()
                
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.primary,
                    backgroundColor = Color.Gray.copy(alpha = 0.2f)
                )
                
                Spacer(modifier = Modifier.height(2.dp))
                
                Text(
                    text = "Page ${historyEntry.chapter.lastPageRead}/${historyEntry.chapter.pageCount}",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun EmptyHistoryState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "No reading history found",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Items you've read will appear here",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
