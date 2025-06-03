package com.example.tachiyomi.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tachiyomi.data.SampleData
import com.example.tachiyomi.model.Chapter
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReaderScreen(
    chapterId: Long,
    onBackClick: () -> Unit
) {
    val chapter = SampleData.sampleChapters.find { it.id == chapterId } ?: return
    val pages = SampleData.samplePages
    
    var showControls by remember { mutableStateOf(true) }
    var showSettings by remember { mutableStateOf(false) }
    
    val pagerState = rememberPagerState(initialPage = 0) { pages.size }
    val coroutineScope = rememberCoroutineScope()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        val width = size.width
                        when {
                            offset.x < width / 3 -> {
                                // Left third of screen - go to previous page
                                if (pagerState.currentPage > 0) {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    }
                                }
                            }
                            offset.x > width * 2 / 3 -> {
                                // Right third of screen - go to next page
                                if (pagerState.currentPage < pages.size - 1) {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                }
                            }
                            else -> {
                                // Middle third of screen - toggle controls
                                showControls = !showControls
                            }
                        }
                    }
                )
            }
    ) {
        // Main content - the manga pages
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            val page = pages[pageIndex]
            
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = page.imageUrl,
                    contentDescription = "Page ${page.index + 1}",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        
        // Reader controls - top and bottom bars
        AnimatedVisibility(
            visible = showControls,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Top bar
                TopAppBar(
                    backgroundColor = Color.Black.copy(alpha = 0.7f),
                    contentColor = Color.White,
                    elevation = 0.dp
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Text(
                        text = "${chapter.name} (${pagerState.currentPage + 1}/${pages.size})"
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    IconButton(onClick = { showSettings = true }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Bottom bar with page slider
                BottomAppBar(
                    backgroundColor = Color.Black.copy(alpha = 0.7f),
                    contentColor = Color.White,
                    elevation = 0.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${pagerState.currentPage + 1}")
                        
                        Slider(
                            value = pagerState.currentPage.toFloat(),
                            onValueChange = { 
                                coroutineScope.launch {
                                    pagerState.scrollToPage(it.toInt())
                                }
                            },
                            valueRange = 0f..(pages.size - 1).toFloat(),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                        )
                        
                        Text("${pages.size}")
                    }
                }
            }
        }
        
        if (showSettings) {
            ReaderSettingsDialog(
                onDismiss = { showSettings = false }
            )
        }
    }
}

@Composable
fun AnimatedVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (visible) {
        Box(modifier = modifier) {
            content()
        }
    }
}

@Composable
fun ReaderSettingsDialog(
    onDismiss: () -> Unit
) {
    var readerMode by remember { mutableStateOf(ReaderMode.LEFT_TO_RIGHT) }
    var keepScreenOn by remember { mutableStateOf(true) }
    var showPageNumber by remember { mutableStateOf(true) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Reader Settings") },
        text = {
            Column {
                Text("Reading Mode", style = MaterialTheme.typography.subtitle1)
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ReaderModeButton(
                        icon = Icons.Filled.KeyboardArrowRight,
                        label = "L→R",
                        selected = readerMode == ReaderMode.LEFT_TO_RIGHT,
                        onClick = { readerMode = ReaderMode.LEFT_TO_RIGHT }
                    )
                    
                    ReaderModeButton(
                        icon = Icons.Filled.KeyboardArrowLeft,
                        label = "R→L",
                        selected = readerMode == ReaderMode.RIGHT_TO_LEFT,
                        onClick = { readerMode = ReaderMode.RIGHT_TO_LEFT }
                    )
                    
                    ReaderModeButton(
                        icon = Icons.Filled.KeyboardArrowDown,
                        label = "Vertical",
                        selected = readerMode == ReaderMode.VERTICAL,
                        onClick = { readerMode = ReaderMode.VERTICAL }
                    )
                }
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Keep screen on")
                    Switch(
                        checked = keepScreenOn,
                        onCheckedChange = { keepScreenOn = it }
                    )
                }
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Show page number")
                    Switch(
                        checked = showPageNumber,
                        onCheckedChange = { showPageNumber = it }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun ReaderModeButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .width(56.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .background(
                    color = if (selected) MaterialTheme.colors.primary else Color.Transparent,
                    shape = MaterialTheme.shapes.small
                )
                .size(48.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selected) Color.White else MaterialTheme.colors.onSurface
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.caption,
            color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
        )
    }
}

enum class ReaderMode {
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
    VERTICAL
}
