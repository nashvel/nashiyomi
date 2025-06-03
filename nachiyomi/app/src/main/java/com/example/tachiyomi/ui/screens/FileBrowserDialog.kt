package com.example.tachiyomi.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.tachiyomi.ui.editor.EditorIcons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.tachiyomi.ui.components.NeomorphicSurface

/**
 * Data class to represent a group of files belonging to a project
 */
data class ProjectGroup(
    val name: String,
    val files: List<CodeFile>
)

/**
 * Dialog for browsing and selecting files with project grouping
 */
@Composable
fun FileBrowserDialog(
    onDismiss: () -> Unit,
    onFileSelected: (CodeFile) -> Unit,
    files: List<CodeFile>
) {
    // Group files by project (for now, just use the filename prefix before the first dot)
    val projectGroups = remember(files) {
        val defaultGroup = ProjectGroup("Standalone Files", mutableListOf())
        val groups = mutableMapOf<String, MutableList<CodeFile>>()
        
        files.forEach { file ->
            val projectName = if (file.name.contains(".")) {
                file.name.substringBefore(".").trim()
            } else {
                "Standalone Files"
            }
            
            if (!groups.containsKey(projectName)) {
                groups[projectName] = mutableListOf()
            }
            groups[projectName]?.add(file)
        }
        
        // Convert the map to a list of ProjectGroup
        val result = groups.map { (name, fileList) -> 
            ProjectGroup(name, fileList) 
        }.toMutableList()
        
        // Sort groups alphabetically
        result.sortBy { it.name }
        
        result
    }
    
    Dialog(onDismissRequest = onDismiss) {
        NeomorphicSurface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f),
            cornerRadius = 16.dp,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Dialog header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Files & Projects",
                        style = MaterialTheme.typography.h6
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
                
                // Project groups and files
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(projectGroups) { projectGroup ->
                        ProjectGroupItem(
                            projectGroup = projectGroup,
                            onFileSelected = {
                                onFileSelected(it)
                                onDismiss()
                            }
                        )
                    }
                }
                
                // New file button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            // Create a new file and select it
                            val newFile = CodeFile("New File.kt", ProgrammingLanguage.KOTLIN)
                            onFileSelected(newFile)
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "New File",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("New File")
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    // This is just a placeholder since we have the dedicated New Project dialog
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            Icons.Filled.Create,
                            contentDescription = "New Project",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

/**
 * Expandable project group in the file browser
 */
@Composable
fun ProjectGroupItem(
    projectGroup: ProjectGroup,
    onFileSelected: (CodeFile) -> Unit
) {
    var expanded by remember { mutableStateOf(true) }
    val rotationState by animateFloatAsState(targetValue = if (expanded) 0f else -90f)
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        // Project header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.primary.copy(alpha = 0.1f),
            shape = RoundedCornerShape(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Expand/Collapse",
                    modifier = Modifier.rotate(rotationState)
                )
                
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = "Project",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                
                Text(
                    text = projectGroup.name,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = "${projectGroup.files.size} files",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
            }
        }
        
        // Files list with animation
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            ) {
                projectGroup.files.forEach { file ->
                    FileItem(
                        file = file,
                        onClick = { onFileSelected(file) }
                    )
                }
            }
        }
    }
}

/**
 * Individual file item in the file browser
 */
@Composable
fun FileItem(
    file: CodeFile,
    onClick: () -> Unit
) {
    val icon = when (file.language) {
        ProgrammingLanguage.KOTLIN -> EditorIcons.Android
        ProgrammingLanguage.HTML -> EditorIcons.Web
        ProgrammingLanguage.CSS -> EditorIcons.Brush
        ProgrammingLanguage.JAVASCRIPT -> EditorIcons.Javascript
        ProgrammingLanguage.PYTHON -> EditorIcons.Code
        ProgrammingLanguage.MARKDOWN -> EditorIcons.Description
    }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        color = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "File Icon",
                tint = when (file.language) {
                    ProgrammingLanguage.KOTLIN -> Color(0xFF7F52FF)
                    ProgrammingLanguage.HTML -> Color(0xFFE44D26)
                    ProgrammingLanguage.CSS -> Color(0xFF264DE4)
                    ProgrammingLanguage.JAVASCRIPT -> Color(0xFFF7DF1E)
                    ProgrammingLanguage.PYTHON -> Color(0xFF3776AB)
                    ProgrammingLanguage.MARKDOWN -> Color(0xFF0078D7)
                },
                modifier = Modifier.padding(end = 16.dp)
            )
            
            Column {
                Text(
                    text = file.name,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = file.language.name.lowercase().capitalize(),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}
