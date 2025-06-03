package com.example.tachiyomi.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.tachiyomi.ui.components.NeomorphicSurface
import com.example.tachiyomi.ui.editor.ProjectTemplates

/**
 * Dialog for creating a new project from templates
 */
@Composable
fun NewProjectDialog(
    onDismiss: () -> Unit,
    onProjectSelected: (ProjectTemplates.ProjectType, String) -> Unit
) {
    var projectName by remember { mutableStateOf("MyProject") }
    var selectedProjectType by remember { mutableStateOf<ProjectTemplates.ProjectType?>(null) }
    
    Dialog(onDismissRequest = onDismiss) {
        NeomorphicSurface(
            modifier = Modifier
                .fillMaxWidth()
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
                        text = "Create New Project",
                        style = MaterialTheme.typography.h6
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
                
                // Project name input
                OutlinedTextField(
                    value = projectName,
                    onValueChange = { projectName = it },
                    label = { Text("Project Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                
                Text(
                    text = "Select Project Type:",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                // Project type selection
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(ProjectTemplates.ProjectType.values()) { projectType ->
                        ProjectTypeItem(
                            projectType = projectType,
                            isSelected = projectType == selectedProjectType,
                            onClick = { selectedProjectType = projectType }
                        )
                    }
                }
                
                // Description of selected project
                selectedProjectType?.let { projectType ->
                    Text(
                        text = ProjectTemplates.getProjectDescription(projectType),
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                // Create button
                Button(
                    onClick = {
                        selectedProjectType?.let { projectType ->
                            if (projectName.isNotBlank()) {
                                onProjectSelected(projectType, projectName)
                                onDismiss()
                            }
                        }
                    },
                    enabled = selectedProjectType != null && projectName.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Create Project")
                }
            }
        }
    }
}

/**
 * Individual project type item in the selection list
 */
@Composable
private fun ProjectTypeItem(
    projectType: ProjectTemplates.ProjectType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colors.primary.copy(alpha = 0.2f)
    } else {
        MaterialTheme.colors.surface
    }
    
    Surface(
        color = backgroundColor,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = projectType.name.replace("_", " "),
                style = MaterialTheme.typography.body1
            )
        }
    }
}
