package com.example.tachiyomi.ui.editor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import com.example.tachiyomi.ui.screens.ProgrammingLanguage

/**
 * Provides syntax highlighting for code in different programming languages
 */
object SyntaxHighlighter {

    // HTML syntax highlighting colors will be first

    // HTML syntax highlighting colors
    private val htmlTagColor = Color(0xFF0000FF)      // Blue
    private val htmlAttributeColor = Color(0xFF795E26) // Brown
    private val htmlValueColor = Color(0xFFA31515)     // Red
    private val htmlCommentColor = Color(0xFF008000)   // Green

    // CSS syntax highlighting colors
    private val cssPropertyColor = Color(0xFF0000FF)   // Blue
    private val cssSelectorColor = Color(0xFFA31515)   // Red
    private val cssValueColor = Color(0xFF098658)      // Teal
    private val cssUnitColor = Color(0xFF800080)       // Purple
    private val cssCommentColor = Color(0xFF008000)    // Green

    // JavaScript syntax highlighting colors
    private val jsKeywordColor = Color(0xFF0000FF)     // Blue
    private val jsFunctionColor = Color(0xFF795E26)    // Brown
    private val jsStringColor = Color(0xFFA31515)      // Red
    private val jsNumberColor = Color(0xFF098658)      // Teal
    private val jsCommentColor = Color(0xFF008000)     // Green

    // Markdown syntax highlighting colors are next

    // Markdown syntax highlighting colors
    private val markdownHeaderColor = Color(0xFF0000FF) // Blue
    private val markdownEmphasisColor = Color(0xFF800080) // Purple
    private val markdownLinkColor = Color(0xFF098658)  // Teal
    private val markdownCodeColor = Color(0xFFA31515)  // Red
    private val markdownListColor = Color(0xFF795E26)  // Brown

    /**
     * Highlights code syntax based on the programming language
     */
    fun highlightSyntax(code: TextFieldValue, language: ProgrammingLanguage): AnnotatedString {
        return when (language) {
            ProgrammingLanguage.HTML -> highlightHtml(code.text)
            ProgrammingLanguage.CSS -> highlightCss(code.text)
            ProgrammingLanguage.JAVASCRIPT -> highlightJavaScript(code.text)
            ProgrammingLanguage.MARKDOWN -> highlightMarkdown(code.text)
        }
    }

    // HTML highlighting function is next

    private fun highlightHtml(code: String): AnnotatedString {
        return buildAnnotatedString {
            val text = code
            var currentPosition = 0
            
            while (currentPosition < text.length) {
                when {
                    // Comments
                    text.startsWith("<!--", currentPosition) -> {
                        val commentEnd = text.indexOf("-->", currentPosition + 4)
                        val commentEndPos = if (commentEnd == -1) text.length else commentEnd + 3
                        withStyle(SpanStyle(color = htmlCommentColor)) {
                            append(text.substring(currentPosition, commentEndPos))
                        }
                        currentPosition = commentEndPos
                    }
                    // Tags
                    text.startsWith("<", currentPosition) && !text.startsWith("</", currentPosition) -> {
                        val tagEnd = text.indexOf(">", currentPosition)
                        if (tagEnd == -1) {
                            append(text.substring(currentPosition))
                            currentPosition = text.length
                        } else {
                            val tagContent = text.substring(currentPosition, tagEnd + 1)
                            val parts = tagContent.split(Regex("\\s+"))
                            
                            // Tag name
                            withStyle(SpanStyle(color = htmlTagColor)) {
                                append(parts[0] + " ")
                            }
                            
                            // Attributes and values
                            for (i in 1 until parts.size) {
                                val attrParts = parts[i].split("=")
                                if (attrParts.size == 1) {
                                    withStyle(SpanStyle(color = htmlAttributeColor)) {
                                        append(attrParts[0])
                                    }
                                    if (i < parts.size - 1) append(" ")
                                } else {
                                    withStyle(SpanStyle(color = htmlAttributeColor)) {
                                        append(attrParts[0] + "=")
                                    }
                                    withStyle(SpanStyle(color = htmlValueColor)) {
                                        append(attrParts[1])
                                    }
                                    if (i < parts.size - 1) append(" ")
                                }
                            }
                            
                            // Closing bracket
                            withStyle(SpanStyle(color = htmlTagColor)) {
                                append(">")
                            }
                            
                            currentPosition = tagEnd + 1
                        }
                    }
                    // Closing tags
                    text.startsWith("</", currentPosition) -> {
                        val tagEnd = text.indexOf(">", currentPosition)
                        if (tagEnd == -1) {
                            append(text.substring(currentPosition))
                            currentPosition = text.length
                        } else {
                            withStyle(SpanStyle(color = htmlTagColor)) {
                                append(text.substring(currentPosition, tagEnd + 1))
                            }
                            currentPosition = tagEnd + 1
                        }
                    }
                    // Default
                    else -> {
                        val nextTag = text.indexOf("<", currentPosition)
                        if (nextTag == -1) {
                            append(text.substring(currentPosition))
                            currentPosition = text.length
                        } else {
                            append(text.substring(currentPosition, nextTag))
                            currentPosition = nextTag
                        }
                    }
                }
            }
        }
    }

    private fun highlightCss(code: String): AnnotatedString {
        return buildAnnotatedString {
            val text = code
            var currentPosition = 0
            
            while (currentPosition < text.length) {
                when {
                    // Comments
                    text.startsWith("/*", currentPosition) -> {
                        val commentEnd = text.indexOf("*/", currentPosition + 2)
                        val commentEndPos = if (commentEnd == -1) text.length else commentEnd + 2
                        withStyle(SpanStyle(color = cssCommentColor)) {
                            append(text.substring(currentPosition, commentEndPos))
                        }
                        currentPosition = commentEndPos
                    }
                    // Selectors
                    text.indexOf("{", currentPosition) != -1 && 
                    (text.indexOf("}", currentPosition) == -1 || text.indexOf("{", currentPosition) < text.indexOf("}", currentPosition)) -> {
                        val selectorEnd = text.indexOf("{", currentPosition)
                        withStyle(SpanStyle(color = cssSelectorColor)) {
                            append(text.substring(currentPosition, selectorEnd))
                        }
                        append("{")
                        currentPosition = selectorEnd + 1
                    }
                    // Properties and values
                    text.indexOf(":", currentPosition) != -1 && 
                    (text.indexOf("}", currentPosition) == -1 || text.indexOf(":", currentPosition) < text.indexOf("}", currentPosition)) -> {
                        val propertyEnd = text.indexOf(":", currentPosition)
                        withStyle(SpanStyle(color = cssPropertyColor)) {
                            append(text.substring(currentPosition, propertyEnd))
                        }
                        append(":")
                        
                        val valueStart = propertyEnd + 1
                        val valueEnd = text.indexOf(";", valueStart)
                        val endPos = if (valueEnd == -1) {
                            val braceEnd = text.indexOf("}", valueStart)
                            if (braceEnd == -1) text.length else braceEnd
                        } else valueEnd
                        
                        val value = text.substring(valueStart, endPos)
                        withStyle(SpanStyle(color = cssValueColor)) {
                            append(value)
                        }
                        
                        if (valueEnd != -1) {
                            append(";")
                            currentPosition = valueEnd + 1
                        } else {
                            currentPosition = endPos
                        }
                    }
                    // Closing brace
                    text.startsWith("}", currentPosition) -> {
                        append("}")
                        currentPosition += 1
                    }
                    // Default
                    else -> {
                        val nextSpecial = minOf(
                            text.indexOf("/*", currentPosition).takeIf { it != -1 } ?: text.length,
                            text.indexOf("{", currentPosition).takeIf { it != -1 } ?: text.length,
                            text.indexOf(":", currentPosition).takeIf { it != -1 } ?: text.length,
                            text.indexOf("}", currentPosition).takeIf { it != -1 } ?: text.length
                        )
                        
                        if (nextSpecial == text.length) {
                            append(text.substring(currentPosition))
                            currentPosition = text.length
                        } else {
                            append(text.substring(currentPosition, nextSpecial))
                            currentPosition = nextSpecial
                        }
                    }
                }
            }
        }
    }

    private fun highlightJavaScript(code: String): AnnotatedString {
        return buildAnnotatedString {
            val keywords = listOf("function", "var", "let", "const", "if", "else", "for", "while", "switch", "case", "default", "break", "continue", "return", "true", "false", "null", "undefined", "this", "new", "class", "extends", "import", "export", "from", "as", "async", "await")
            
            val text = code
            var currentPosition = 0
            
            // Simple tokenization
            val tokens = text.split(Regex("(\\s+|[{}()\\[\\],.;:]|\\b)"))
            
            for (token in tokens) {
                val tokenStart = text.indexOf(token, currentPosition)
                if (tokenStart == -1) continue
                
                currentPosition = tokenStart + token.length

                when {
                    // Comments (line comments)
                    token.startsWith("//") -> {
                        val lineEnd = text.indexOf('\n', tokenStart)
                        val commentEnd = if (lineEnd == -1) text.length else lineEnd
                        val comment = text.substring(tokenStart, commentEnd)
                        withStyle(SpanStyle(color = jsCommentColor)) {
                            append(comment)
                        }
                        currentPosition = commentEnd
                    }
                    // Comments (block comments)
                    token.startsWith("/*") -> {
                        val blockEnd = text.indexOf("*/", tokenStart)
                        val commentEnd = if (blockEnd == -1) text.length else blockEnd + 2
                        val comment = text.substring(tokenStart, commentEnd)
                        withStyle(SpanStyle(color = jsCommentColor)) {
                            append(comment)
                        }
                        currentPosition = commentEnd
                    }
                    // Strings
                    (token.startsWith("\"") && token.endsWith("\"") && token.length > 1) ||
                    (token.startsWith("'") && token.endsWith("'") && token.length > 1) -> {
                        withStyle(SpanStyle(color = jsStringColor)) {
                            append(token)
                        }
                    }
                    // Numbers
                    token.matches(Regex("-?\\d+(\\.\\d+)?")) -> {
                        withStyle(SpanStyle(color = jsNumberColor)) {
                            append(token)
                        }
                    }
                    // Keywords
                    token in keywords -> {
                        withStyle(SpanStyle(color = jsKeywordColor, fontWeight = FontWeight.Bold)) {
                            append(token)
                        }
                    }
                    // Function calls
                    token.matches(Regex("[a-zA-Z_][a-zA-Z0-9_]*")) && 
                    text.substring(currentPosition).trimStart().startsWith("(") -> {
                        withStyle(SpanStyle(color = jsFunctionColor)) {
                            append(token)
                        }
                    }
                    // Default
                    else -> {
                        append(token)
                    }
                }
            }
        }
    }

    private fun highlightPython(code: String): AnnotatedString {
        return buildAnnotatedString {
            val keywords = listOf("def", "class", "if", "elif", "else", "for", "while", "try", "except", "finally", "import", "from", "as", "return", "yield", "break", "continue", "pass", "True", "False", "None", "and", "or", "not", "is", "in", "lambda", "with", "global", "nonlocal")
            
            val text = code
            var currentPosition = 0
            
            // Simple tokenization
            val tokens = text.split(Regex("(\\s+|[{}()\\[\\],.;:]|\\b)"))
            
            for (token in tokens) {
                val tokenStart = text.indexOf(token, currentPosition)
                if (tokenStart == -1) continue
                
                currentPosition = tokenStart + token.length

                when {
                    // Comments
                    token.startsWith("#") -> {
                        val lineEnd = text.indexOf('\n', tokenStart)
                        val commentEnd = if (lineEnd == -1) text.length else lineEnd
                        val comment = text.substring(tokenStart, commentEnd)
                        withStyle(SpanStyle(color = pythonCommentColor)) {
                            append(comment)
                        }
                        currentPosition = commentEnd
                    }
                    // Strings (single quotes)
                    token.startsWith("'") && token.endsWith("'") && token.length > 1 -> {
                        withStyle(SpanStyle(color = pythonStringColor)) {
                            append(token)
                        }
                    }
                    // Strings (double quotes)
                    token.startsWith("\"") && token.endsWith("\"") && token.length > 1 -> {
                        withStyle(SpanStyle(color = pythonStringColor)) {
                            append(token)
                        }
                    }
                    // Numbers
                    token.matches(Regex("-?\\d+(\\.\\d+)?")) -> {
                        withStyle(SpanStyle(color = pythonNumberColor)) {
                            append(token)
                        }
                    }
                    // Keywords
                    token in keywords -> {
                        withStyle(SpanStyle(color = pythonKeywordColor, fontWeight = FontWeight.Bold)) {
                            append(token)
                        }
                    }
                    // Function calls
                    token.matches(Regex("[a-zA-Z_][a-zA-Z0-9_]*")) && 
                    text.substring(currentPosition).trimStart().startsWith("(") -> {
                        withStyle(SpanStyle(color = pythonFunctionColor)) {
                            append(token)
                        }
                    }
                    // Default
                    else -> {
                        append(token)
                    }
                }
            }
        }
    }

    private fun highlightMarkdown(code: String): AnnotatedString {
        return buildAnnotatedString {
            val text = code
            var currentPosition = 0
            
            val lines = text.split("\n")
            
            for (line in lines) {
                val lineStart = text.indexOf(line, currentPosition)
                if (lineStart == -1) continue
                
                when {
                    // Headers
                    line.startsWith("# ") || line.startsWith("## ") || line.startsWith("### ") || 
                    line.startsWith("#### ") || line.startsWith("##### ") || line.startsWith("###### ") -> {
                        withStyle(SpanStyle(color = markdownHeaderColor, fontWeight = FontWeight.Bold)) {
                            append(line)
                        }
                    }
                    // Lists
                    line.trim().startsWith("- ") || line.trim().startsWith("* ") || line.trim().matches(Regex("\\d+\\.\\s.*")) -> {
                        val listPrefix = when {
                            line.trim().startsWith("- ") -> line.indexOf("- ")
                            line.trim().startsWith("* ") -> line.indexOf("* ")
                            else -> line.indexOfFirst { it.isDigit() }
                        }
                        
                        val prefixEnd = line.indexOf(" ", listPrefix) + 1
                        
                        append(line.substring(0, listPrefix))
                        withStyle(SpanStyle(color = markdownListColor, fontWeight = FontWeight.Bold)) {
                            append(line.substring(listPrefix, prefixEnd))
                        }
                        append(line.substring(prefixEnd))
                    }
                    // Emphasis (bold)
                    line.contains("**") || line.contains("__") -> {
                        var pos = 0
                        var inEmphasis = false
                        var i = 0
                        
                        while (i < line.length) {
                            if ((line.startsWith("**", i) || line.startsWith("__", i)) && !inEmphasis) {
                                append(line.substring(pos, i))
                                pos = i
                                inEmphasis = true
                                i += 2
                            } else if ((line.startsWith("**", i) || line.startsWith("__", i)) && inEmphasis) {
                                withStyle(SpanStyle(color = markdownEmphasisColor, fontWeight = FontWeight.Bold)) {
                                    append(line.substring(pos, i + 2))
                                }
                                pos = i + 2
                                inEmphasis = false
                                i += 2
                            } else {
                                i++
                            }
                        }
                        
                        if (pos < line.length) {
                            if (inEmphasis) {
                                withStyle(SpanStyle(color = markdownEmphasisColor, fontWeight = FontWeight.Bold)) {
                                    append(line.substring(pos))
                                }
                            } else {
                                append(line.substring(pos))
                            }
                        }
                    }
                    // Links
                    line.contains("[") && line.contains("](") -> {
                        var pos = 0
                        var i = 0
                        
                        while (i < line.length) {
                            if (line.startsWith("[", i)) {
                                append(line.substring(pos, i))
                                
                                val linkTextStart = i
                                val linkTextEnd = line.indexOf("]", i)
                                
                                if (linkTextEnd != -1 && line.startsWith("](", linkTextEnd)) {
                                    val linkUrlStart = linkTextEnd + 2
                                    val linkUrlEnd = line.indexOf(")", linkUrlStart)
                                    
                                    if (linkUrlEnd != -1) {
                                        withStyle(SpanStyle(color = markdownLinkColor, fontWeight = FontWeight.Bold)) {
                                            append(line.substring(linkTextStart, linkUrlEnd + 1))
                                        }
                                        
                                        pos = linkUrlEnd + 1
                                        i = pos
                                        continue
                                    }
                                }
                                
                                i++
                            } else {
                                i++
                            }
                        }
                        
                        if (pos < line.length) {
                            append(line.substring(pos))
                        }
                    }
                    // Code blocks (inline)
                    line.contains("`") -> {
                        var pos = 0
                        var inCode = false
                        var i = 0
                        
                        while (i < line.length) {
                            if (line.startsWith("`", i) && !inCode) {
                                append(line.substring(pos, i))
                                pos = i
                                inCode = true
                                i++
                            } else if (line.startsWith("`", i) && inCode) {
                                withStyle(SpanStyle(color = markdownCodeColor, fontFamily = FontFamily.Monospace)) {
                                    append(line.substring(pos, i + 1))
                                }
                                pos = i + 1
                                inCode = false
                                i++
                            } else {
                                i++
                            }
                        }
                        
                        if (pos < line.length) {
                            if (inCode) {
                                withStyle(SpanStyle(color = markdownCodeColor, fontFamily = FontFamily.Monospace)) {
                                    append(line.substring(pos))
                                }
                            } else {
                                append(line.substring(pos))
                            }
                        }
                    }
                    // Default
                    else -> {
                        append(line)
                    }
                }
                
                if (lines.indexOf(line) < lines.size - 1) {
                    append("\n")
                }
                
                currentPosition = lineStart + line.length + 1 // +1 for the newline
            }
        }
    }
}
