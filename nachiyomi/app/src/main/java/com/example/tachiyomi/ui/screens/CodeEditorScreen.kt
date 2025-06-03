package com.example.tachiyomi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tachiyomi.ui.components.NeomorphicCard
import com.example.tachiyomi.ui.components.NeomorphicSurface
import com.example.tachiyomi.ui.components.NeomorphicTopAppBar
import com.example.tachiyomi.ui.editor.*
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileWriter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for the code editor screen that handles code content and file operations
 */
class CodeEditorViewModel : ViewModel() {
    private val _codeContent = MutableStateFlow(TextFieldValue(CodeTemplates.getTemplateForLanguage(ProgrammingLanguage.HTML, "Untitled.html")))
    val codeContent: StateFlow<TextFieldValue> = _codeContent.asStateFlow()

    private val _currentFileName = MutableStateFlow("Untitled.html")
    val currentFileName: StateFlow<String> = _currentFileName.asStateFlow()

    private val _currentLanguage = MutableStateFlow(ProgrammingLanguage.HTML)
    val currentLanguage: StateFlow<ProgrammingLanguage> = _currentLanguage.asStateFlow()
    
    // Map to store the actual content of saved files
    private val _fileContents = MutableStateFlow<Map<String, String>>(mutableMapOf())

    // Mock list of saved files
    private val _savedFiles = MutableStateFlow<List<CodeFile>>(
        listOf(
            CodeFile("index.html", ProgrammingLanguage.HTML),
            CodeFile("styles.css", ProgrammingLanguage.CSS),
            CodeFile("script.js", ProgrammingLanguage.JAVASCRIPT),
            CodeFile("notes.md", ProgrammingLanguage.MARKDOWN)
        )
    )
    val savedFiles: StateFlow<List<CodeFile>> = _savedFiles.asStateFlow()
    
    // Track the currently selected project, if any
    private val _currentProject = MutableStateFlow<String?>(null)
    val currentProject: StateFlow<String?> = _currentProject.asStateFlow()
    
    // Track the last saved file for notifications
    private val _lastSavedFile = MutableStateFlow<String?>(null)
    val lastSavedFile: StateFlow<String?> = _lastSavedFile.asStateFlow()

    fun updateCode(newValue: TextFieldValue) {
        _codeContent.value = newValue
    }
    
    fun updateFileName(newName: String) {
        _currentFileName.value = newName
    }
    
    /**
     * Creates a new project from a template
     */
    fun createProject(projectType: ProjectTemplates.ProjectType, projectName: String) {
        // Get the project files from the template
        val projectFiles = ProjectTemplates.getProjectFiles(projectType, projectName)
        
        // Add the new files to the saved files list
        val updatedFilesList = _savedFiles.value.toMutableList()
        updatedFilesList.addAll(projectFiles)
        _savedFiles.value = updatedFilesList
        
        // Set the current project name
        _currentProject.value = projectName
        
        // Load the first file of the project
        if (projectFiles.isNotEmpty()) {
            loadFile(projectFiles.first())
        }
    }
    
    /**
     * Delete the current file from the saved files list
     */
    fun deleteCurrentFile() {
        val updatedFilesList = _savedFiles.value.toMutableList()
        val fileToRemove = updatedFilesList.find { it.name == _currentFileName.value }
        
        if (fileToRemove != null) {
            updatedFilesList.remove(fileToRemove)
            _savedFiles.value = updatedFilesList
            
            // Load another file if available, or create a new empty file
            if (updatedFilesList.isNotEmpty()) {
                loadFile(updatedFilesList.first())
            } else {
                _currentFileName.value = "Untitled.html"
                _currentLanguage.value = ProgrammingLanguage.HTML
                _codeContent.value = TextFieldValue(CodeTemplates.getTemplateForLanguage(ProgrammingLanguage.HTML, "Untitled.html"))
            }
        }
    }

    fun changeLanguage(language: ProgrammingLanguage) {
        val previousLanguage = _currentLanguage.value
        _currentLanguage.value = language
        
        // Update file extension based on new language
        if (previousLanguage != language) {
            val baseName = _currentFileName.value.substringBeforeLast(".", _currentFileName.value)
            val newExtension = when (language) {
                ProgrammingLanguage.HTML -> "html"
                ProgrammingLanguage.CSS -> "css"
                ProgrammingLanguage.JAVASCRIPT -> "js"
                ProgrammingLanguage.MARKDOWN -> "md"
            }
            
            val newFileName = "$baseName.$newExtension"
            _currentFileName.value = newFileName
            
            // If the code is empty or just the default template, update it with a new template
            if (_codeContent.value.text.isBlank() || _codeContent.value.text == "// Start coding here...") {
                _codeContent.value = TextFieldValue(CodeTemplates.getTemplateForLanguage(language, newFileName))
            }
        }
    }

    fun saveCurrentFile() {
        // Save the file content to our map
        val updatedContents = _fileContents.value.toMutableMap()
        updatedContents[_currentFileName.value] = _codeContent.value.text
        _fileContents.value = updatedContents
        
        // Set the last saved file for notification
        _lastSavedFile.value = _currentFileName.value
        
        // After notification is shown, clear it
        _lastSavedFile.value = null
    }
    
    /**
     * Exports current HTML file and associated CSS/JS files to the external storage
     * and returns the URI of the main HTML file
     */
    fun exportAndGetHtmlFileUri(context: Context): Uri? {
        // Save current file first
        saveCurrentFile()
        
        try {
            // Create directory for exported files
            val exportDir = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "nashiyomi_web")
            if (!exportDir.exists()) {
                exportDir.mkdirs()
            }
            
            // Create the HTML file
            val htmlFile = File(exportDir, _currentFileName.value)
            FileWriter(htmlFile).use { it.write(_codeContent.value.text) }
            
            // Also export any CSS and JS files that might be referenced
            _fileContents.value.forEach { (filename, content) ->
                if (filename.endsWith(".css") || filename.endsWith(".js")) {
                    val file = File(exportDir, filename)
                    FileWriter(file).use { it.write(content) }
                }
            }
            
            // Return the URI for the HTML file
            return FileProvider.getUriForFile(
                context,
                "com.example.tachiyomi.fileprovider",
                htmlFile
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun loadFile(file: CodeFile) {
        _currentFileName.value = file.name
        _currentLanguage.value = file.language
        
        // Get the content if it exists in our map, otherwise use the template
        val content = _fileContents.value[file.name] ?: CodeTemplates.getTemplateForLanguage(file.language, file.name)
        _codeContent.value = TextFieldValue(content)
    }
}

/**
 * Supported programming languages for syntax highlighting
 */
enum class ProgrammingLanguage {
    HTML,
    CSS,
    JAVASCRIPT,
    MARKDOWN
}

/**
 * Represents a code file with a name and language
 */
data class CodeFile(val name: String, val language: ProgrammingLanguage)

/**
 * Main composable for the code editor screen
 */
@Composable
fun CodeEditorScreen(
    navController: NavController,
    viewModel: CodeEditorViewModel = viewModel()
) {
    // Provide notification state for the editor
    ProvideNotificationState {
        CodeEditorScreenContent(navController, viewModel)
    }
}

@Composable
fun CodeEditorScreenContent(
    navController: NavController,
    viewModel: CodeEditorViewModel = viewModel()
) {
    val codeContent by viewModel.codeContent.collectAsState()
    val currentFileName by viewModel.currentFileName.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val savedFiles by viewModel.savedFiles.collectAsState()
    val currentProject by viewModel.currentProject.collectAsState()
    
    // Get notification hook
    val showNotification = rememberNotificationState()
    
    // Dialog state
    var showFileBrowserDialog by remember { mutableStateOf(false) }
    var showRenameDialog by remember { mutableStateOf(false) }
    var showNewProjectDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var newFileName by remember { mutableStateOf(currentFileName) }
    
    // Launch effect to show notification when file is saved
    LaunchedEffect(viewModel.lastSavedFile.collectAsState().value) {
        viewModel.lastSavedFile.value?.let { fileName ->
            showNotification(
                EditorNotification(
                    message = "File saved: $fileName",
                    type = NotificationType.SUCCESS
                )
            )
        }
    }
    
    NotificationHost(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            // Top app bar with file controls
            NeomorphicTopAppBar(
                title = { Text(currentFileName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Get the context once at the Composable level
                    val context = androidx.compose.ui.platform.LocalContext.current
                    
                    // Run button (only visible for HTML files)
                    if (currentFileName.endsWith(".html")) {
                        IconButton(
                            onClick = {
                                // Export the HTML file and get its URI
                                val htmlFileUri = viewModel.exportAndGetHtmlFileUri(context)
                                
                                // Open the file in a browser
                                htmlFileUri?.let { uri ->
                                    val intent = Intent(Intent.ACTION_VIEW).apply {
                                        setDataAndType(uri, "text/html")
                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    }
                                    context.startActivity(intent)
                                }
                                
                                showNotification(
                                    EditorNotification(
                                        message = "Opening $currentFileName in browser",
                                        type = NotificationType.SUCCESS
                                    )
                                )
                            }
                        ) {
                            Icon(Icons.Filled.PlayArrow, contentDescription = "Run in Browser")
                        }
                    }
                    
                    IconButton(
                        onClick = { 
                            viewModel.saveCurrentFile()
                            showNotification(
                                EditorNotification(
                                    message = "File saved: $currentFileName",
                                    type = NotificationType.SUCCESS
                                )
                            )
                        }
                    ) {
                        Icon(Icons.Filled.Create, contentDescription = "Save")
                    }
                    IconButton(onClick = { showFileBrowserDialog = true }) {
                        Icon(Icons.Filled.Create, contentDescription = "Open")
                    }
                    IconButton(onClick = { showRenameDialog = true }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Rename")
                    }
                    IconButton(onClick = { showNewProjectDialog = true }) {
                        Icon(Icons.Filled.Add, contentDescription = "New Project")
                    }
                    IconButton(onClick = { showDeleteConfirmation = true }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete File")
                    }
                }
            )
            
            // Language selection tabs
            LanguageSelectionBar(
                currentLanguage = currentLanguage,
                onLanguageSelected = { viewModel.changeLanguage(it) }
            )
            
            // Editor area
            NeomorphicCard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .weight(1f),
                shadowElevation = 4.dp
            ) {
                val scrollState = rememberScrollState()
            
                // We're using a simpler implementation to avoid transformation issues
                BasicTextField(
                    value = codeContent,
                    onValueChange = { viewModel.updateCode(it) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .verticalScroll(scrollState),
                    textStyle = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                )
            }
        }
    }
    
    // File browser dialog
    if (showFileBrowserDialog) {
        FileBrowserDialog(
            onDismiss = { showFileBrowserDialog = false },
            onFileSelected = { file -> viewModel.loadFile(file) },
            files = savedFiles
        )
    }
    
    // Rename dialog
    if (showRenameDialog) {
        AlertDialog(
            onDismissRequest = { showRenameDialog = false },
            title = { Text("Rename File") },
            text = {
                TextField(
                    value = newFileName,
                    onValueChange = { newFileName = it },
                    label = { Text("File Name") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newFileName.isNotBlank()) {
                            viewModel.updateFileName(newFileName)
                            showRenameDialog = false
                        }
                    }
                ) {
                    Text("Rename")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRenameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // New Project dialog
    if (showNewProjectDialog) {
        NewProjectDialog(
            onDismiss = { showNewProjectDialog = false },
            onProjectSelected = { projectType, projectName ->
                viewModel.createProject(projectType, projectName)
                showNotification(
                    EditorNotification(
                        message = "Project created: $projectName",
                        type = NotificationType.SUCCESS
                    )
                )
            }
        )
    }
    
    // Delete confirmation dialog
    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Delete File") },
            text = { Text("Are you sure you want to delete '$currentFileName'? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteCurrentFile()
                        showDeleteConfirmation = false
                        showNotification(
                            EditorNotification(
                                message = "File deleted: $currentFileName",
                                type = NotificationType.INFO
                            )
                        )
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.error)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

/**
 * Bar for selecting the programming language
 */
@Composable
fun LanguageSelectionBar(
    currentLanguage: ProgrammingLanguage,
    onLanguageSelected: (ProgrammingLanguage) -> Unit
) {
    NeomorphicSurface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgrammingLanguage.values().forEach { language ->
                LanguageTab(
                    language = language,
                    isSelected = language == currentLanguage,
                    onClick = { onLanguageSelected(language) }
                )
            }
        }
    }
}

/**
 * Tab for language selection
 */
@Composable
fun LanguageTab(
    language: ProgrammingLanguage,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colors.primary.copy(alpha = 0.2f)
    } else {
        Color.Transparent
    }
    
    val textColor = if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
    }
    
    Surface(
        modifier = Modifier
            .padding(4.dp),
        color = backgroundColor,
        shape = MaterialTheme.shapes.small,
        contentColor = textColor
    ) {
        Text(
            text = language.name.lowercase().replaceFirstChar { it.uppercase() },
            modifier = Modifier
                .clickable { onClick() }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            style = MaterialTheme.typography.body2
        )
    }
}
