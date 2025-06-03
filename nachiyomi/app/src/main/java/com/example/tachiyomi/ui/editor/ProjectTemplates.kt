package com.example.tachiyomi.ui.editor

import com.example.tachiyomi.ui.screens.CodeFile
import com.example.tachiyomi.ui.screens.ProgrammingLanguage

/**
 * Defines project templates with multiple files for different types of projects
 */
object ProjectTemplates {

    /**
     * Different types of projects that can be created
     */
    enum class ProjectType {
        WEB_BASIC,
        MARKDOWN_NOTES
    }

    /**
     * Returns a list of CodeFiles for a specific project type
     */
    fun getProjectFiles(projectType: ProjectType, projectName: String): List<CodeFile> {
        return when (projectType) {
            ProjectType.WEB_BASIC -> webBasicProject(projectName)
            ProjectType.MARKDOWN_NOTES -> markdownNotesProject(projectName)
        }
    }

    /**
     * Creates a basic web project with HTML, CSS, and JavaScript files
     */
    private fun webBasicProject(projectName: String): List<CodeFile> {
        return listOf(
            CodeFile("index.html", ProgrammingLanguage.HTML),
            CodeFile("styles.css", ProgrammingLanguage.CSS),
            CodeFile("script.js", ProgrammingLanguage.JAVASCRIPT),
            CodeFile("README.md", ProgrammingLanguage.MARKDOWN)
        )
    }

    /**
     * Creates a set of Markdown notes for documentation
     */
    private fun markdownNotesProject(projectName: String): List<CodeFile> {
        return listOf(
            CodeFile("$projectName.md", ProgrammingLanguage.MARKDOWN),
            CodeFile("notes.md", ProgrammingLanguage.MARKDOWN),
            CodeFile("todo.md", ProgrammingLanguage.MARKDOWN)
        )
    }

    /**
     * Get a description for each project type
     */
    fun getProjectDescription(projectType: ProjectType): String {
        return when (projectType) {
            ProjectType.WEB_BASIC -> "A simple web project with HTML, CSS, and JavaScript files"
            ProjectType.MARKDOWN_NOTES -> "A set of Markdown notes for documentation"
        }
    }
}
