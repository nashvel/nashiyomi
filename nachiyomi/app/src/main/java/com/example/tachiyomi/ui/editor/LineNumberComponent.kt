package com.example.tachiyomi.ui.editor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A composable that displays a code editor with line numbers
 */
@Composable
fun CodeEditorWithLineNumbers(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    // Count the number of lines in the text
    val lines = value.text.lines()
    val lineCount = lines.size
    
    Row(modifier = modifier) {
        // Line numbers column
        Column(
            modifier = Modifier
                .width(40.dp)
                .padding(end = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            for (i in 1..lineCount) {
                Text(
                    text = i.toString(),
                    style = textStyle.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
        
        // Actual text editor
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            textStyle = textStyle,
            visualTransformation = visualTransformation
        )
    }
}

/**
 * A more advanced implementation that keeps line numbers and text editor in sync
 */
@Composable
fun AdvancedCodeEditor(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val lines = value.text.lines()
    val lineCount = lines.size
    
    Row(modifier = modifier) {
        // Line numbers column with sticky line numbers
        Box(
            modifier = Modifier
                .width(40.dp)
                .fillMaxHeight()
                .padding(end = 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                for (i in 1..lineCount) {
                    Text(
                        text = i.toString(),
                        style = textStyle.copy(
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                
                // Add an extra line number at the end if needed
                if (value.text.endsWith("\n") || value.text.isEmpty()) {
                    Text(
                        text = (lineCount + 1).toString(),
                        style = textStyle.copy(
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
        
        // Text editor
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            textStyle = textStyle,
            visualTransformation = visualTransformation
        )
    }
}
