package com.example.tachiyomi.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Delete
import com.example.tachiyomi.ui.components.NeomorphicCard
import com.example.tachiyomi.ui.components.NeomorphicSurface
import com.example.tachiyomi.ui.components.NeomorphicTopAppBar

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Scaffold(
        topBar = {
            NeomorphicTopAppBar(
                title = { Text("Settings") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            item {
                SettingsCategory(title = "General")
                
                SettingsItem(
                    title = "Theme",
                    subtitle = "Light",
                    icon = Icons.Filled.Settings,
                    onClick = { /* Show theme options */ }
                )
                
                SettingsItem(
                    title = "Language",
                    subtitle = "English",
                    icon = Icons.Filled.Info,
                    onClick = { /* Show language options */ }
                )
                
                SettingsItem(
                    title = "Display",
                    subtitle = "Configure reading mode, screen orientation, etc.",
                    icon = Icons.Filled.Check,
                    onClick = { /* Show display settings */ }
                )
            }
            
            item {
                SettingsCategory(title = "Library")
                
                SettingsItem(
                    title = "Categories",
                    subtitle = "Create and edit library categories",
                    icon = Icons.Filled.List,
                    onClick = { /* Show categories */ }
                )
                
                SettingsItem(
                    title = "Update frequency",
                    subtitle = "Daily",
                    icon = Icons.Filled.Star,
                    onClick = { /* Show update options */ }
                )
                
                SettingsItem(
                    title = "Library filters",
                    subtitle = "Configure default library view",
                    icon = Icons.Filled.List,
                    onClick = { /* Show filter options */ }
                )
            }
            
            item {
                SettingsCategory(title = "Reader")
                
                SettingsItem(
                    title = "Default reading mode",
                    subtitle = "Left to right",
                    icon = Icons.Filled.Home,
                    onClick = { /* Show reading mode options */ }
                )
                
                SettingsItem(
                    title = "Page transitions",
                    subtitle = "Slide",
                    icon = Icons.Filled.ArrowForward,
                    onClick = { /* Show transition options */ }
                )
                
                SwitchSettingsItem(
                    title = "Keep screen on",
                    icon = Icons.Filled.Warning,
                    initialValue = true,
                    onValueChange = { /* Update keep screen on setting */ }
                )
            }
            
            item {
                SettingsCategory(title = "Downloads")
                
                SettingsItem(
                    title = "Download directory",
                    subtitle = "/storage/emulated/0/Nashiyomi",
                    icon = Icons.Filled.Home,
                    onClick = { /* Show directory options */ }
                )
                
                SettingsItem(
                    title = "Download only on Wi-Fi",
                    subtitle = "Save mobile data",
                    icon = Icons.Filled.Settings,
                    onClick = { /* Show Wi-Fi options */ },
                    trailing = {
                        Switch(
                            checked = true,
                            onCheckedChange = { /* Update Wi-Fi setting */ }
                        )
                    }
                )
                
                SettingsItem(
                    title = "Download new chapters",
                    subtitle = "Automatically download new chapters",
                    icon = Icons.Filled.Search,
                    onClick = { /* Show auto-download options */ },
                    trailing = {
                        Switch(
                            checked = false,
                            onCheckedChange = { /* Update auto-download setting */ }
                        )
                    }
                )
            }
            
            item {
                SettingsCategory(title = "Advanced")
                
                SettingsItem(
                    title = "Clear cache",
                    subtitle = "Free up storage space",
                    icon = Icons.Filled.Delete,
                    onClick = { /* Clear cache */ }
                )
                
                SettingsItem(
                    title = "Backup and restore",
                    subtitle = "Back up your library and settings",
                    icon = Icons.Filled.Star,
                    onClick = { /* Show backup options */ }
                )
                
                SettingsItem(
                    title = "About",
                    subtitle = "Version 1.0.0",
                    icon = Icons.Filled.Info,
                    onClick = { /* Show about information */ }
                )
            }
        }
    }
}

@Composable
fun SettingsCategory(title: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String? = null,
    icon: ImageVector,
    onClick: () -> Unit,
    trailing: @Composable (() -> Unit)? = null
) {
    NeomorphicCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        cornerRadius = 12.dp,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
            
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.subtitle1
                )
                
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            
            if (trailing != null) {
                trailing()
            } else {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
                )
            }
        }
    }
    
    Spacer(modifier = Modifier.height(8.dp))
    
    Divider(
        modifier = Modifier.padding(start = 56.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
    )
}

@Composable
fun SwitchSettingsItem(
    title: String,
    icon: ImageVector,
    initialValue: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(initialValue) }
    
    NeomorphicCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        cornerRadius = 12.dp,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isChecked = !isChecked
                    onValueChange(isChecked)
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            )
            
            Switch(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    onValueChange(it)
                }
            )
        }
    }
    
    Spacer(modifier = Modifier.height(8.dp))
}
