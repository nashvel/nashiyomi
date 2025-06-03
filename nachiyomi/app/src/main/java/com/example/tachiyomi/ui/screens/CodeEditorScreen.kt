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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for the code editor screen that handles code content and file operations
 */
class CodeEditorViewModel : ViewModel() {
    private val _codeContent = MutableStateFlow(TextFieldValue(CodeTemplates.getTemplateForLanguage(ProgrammingLanguage.KOTLIN, "Untitled.kt")))
    val codeContent: StateFlow<TextFieldValue> = _codeContent.asStateFlow()

    private val _currentFileName = MutableStateFlow("Untitled.kt")
    val currentFileName: StateFlow<String> = _currentFileName.asStateFlow()

    private val _currentLanguage = MutableStateFlow(ProgrammingLanguage.KOTLIN)
    val currentLanguage: StateFlow<ProgrammingLanguage> = _currentLanguage.asStateFlow()

    // Mock list of saved files
    private val _savedFiles = MutableStateFlow<List<CodeFile>>(
        listOf(
            CodeFile("Example.kt", ProgrammingLanguage.KOTLIN),
            CodeFile("styles.css", ProgrammingLanguage.CSS),
            CodeFile("index.html", ProgrammingLanguage.HTML),
            CodeFile("script.js", ProgrammingLanguage.JAVASCRIPT),
            CodeFile("main.py", ProgrammingLanguage.PYTHON),
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
                ProgrammingLanguage.KOTLIN -> "kt"
                ProgrammingLanguage.HTML -> "html"
                ProgrammingLanguage.CSS -> "css"
                ProgrammingLanguage.JAVASCRIPT -> "js"
                ProgrammingLanguage.PYTHON -> "py"
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
        // In a real implementation, this would save to local storage
        // For now, we just update the saved files list if it's a new file
        val existingFile = _savedFiles.value.find { it.name == _currentFileName.value }
        
        if (existingFile == null) {
            val updatedFilesList = _savedFiles.value.toMutableList()
            updatedFilesList.add(CodeFile(_currentFileName.value, _currentLanguage.value))
            _savedFiles.value = updatedFilesList
        }
        
        // Update last saved file for notification
        _lastSavedFile.value = _currentFileName.value
    }

    fun loadFile(file: CodeFile) {
        // In a real implementation, this would load from local storage
        _currentFileName.value = file.name
        _currentLanguage.value = file.language
        
        // Set content based on file type using templates
        _codeContent.value = TextFieldValue(CodeTemplates.getTemplateForLanguage(file.language, file.name))
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
