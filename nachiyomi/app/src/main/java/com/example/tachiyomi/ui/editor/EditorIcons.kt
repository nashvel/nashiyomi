package com.example.tachiyomi.ui.editor

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Custom icon provider for the code editor
 * Provides icon mappings for different file types and operations
 */
object EditorIcons {
    // Code Editor Icons for file types - using available Material icons
    val Code: ImageVector = Icons.Filled.Create
    val Web: ImageVector = Icons.Filled.Create // HTML files
    val Brush: ImageVector = Icons.Filled.Create // CSS files
    val Javascript: ImageVector = Icons.Filled.Create // JavaScript files 
    // Only supporting HTML, CSS, JavaScript, and Markdown
    val Description: ImageVector = Icons.Filled.Create // For text files
    
    /**
     * Get the appropriate icon for a file based on its extension
     */
    fun getIconForFile(fileName: String): ImageVector {
        return when {
            fileName.endsWith(".html") -> Web
            fileName.endsWith(".css") -> Brush
            fileName.endsWith(".js") -> Javascript
            fileName.endsWith(".md") -> Description
            else -> Description
        }
    }
}
