package com.example.tachiyomi.ui.editor

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Types of notifications that can be shown
 */
enum class NotificationType {
    SUCCESS,
    ERROR,
    INFO,
    WARNING
}

/**
 * Data class for notification
 */
data class EditorNotification(
    val message: String,
    val type: NotificationType,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null,
    val duration: Long = 3000 // Duration in milliseconds
)

/**
 * A component that shows notifications at the bottom of the screen
 */
@Composable
fun NotificationHost(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val currentNotification = LocalNotificationState.current
    
    Box(modifier = modifier) {
        content()
        
        AnimatedVisibility(
            visible = currentNotification.value != null,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            currentNotification.value?.let { notification ->
                NotificationItem(notification = notification) {
                    currentNotification.value = null
                }
            }
        }
    }
}

/**
 * Individual notification item
 */
@Composable
private fun NotificationItem(
    notification: EditorNotification,
    onDismiss: () -> Unit
) {
    val icon: ImageVector
    val backgroundColor: Color
    val contentColor: Color = Color.White
    
    when (notification.type) {
        NotificationType.SUCCESS -> {
            icon = Icons.Default.Check
            backgroundColor = Color(0xFF4CAF50)
        }
        NotificationType.ERROR -> {
            icon = Icons.Default.Close
            backgroundColor = Color(0xFFF44336)
        }
        NotificationType.INFO -> {
            icon = Icons.Default.Info
            backgroundColor = Color(0xFF2196F3)
        }
        NotificationType.WARNING -> {
            icon = Icons.Default.Warning
            backgroundColor = Color(0xFFFF9800)
        }
    }
    
    val coroutineScope = rememberCoroutineScope()
    
    DisposableEffect(notification) {
        val job = coroutineScope.launch {
            delay(notification.duration)
            onDismiss()
        }
        
        onDispose {
            job.cancel()
        }
    }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = notification.type.name,
                tint = contentColor
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = notification.message,
                color = contentColor,
                modifier = Modifier.weight(1f)
            )
            
            if (notification.actionLabel != null && notification.onAction != null) {
                Spacer(modifier = Modifier.width(16.dp))
                
                TextButton(
                    onClick = {
                        notification.onAction.invoke()
                        onDismiss()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = contentColor
                    )
                ) {
                    Text(notification.actionLabel)
                }
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Dismiss",
                    tint = contentColor
                )
            }
        }
    }
}

/**
 * Notification state for composition
 */
val LocalNotificationState = compositionLocalOf<MutableState<EditorNotification?>> {
    error("No NotificationState provided")
}

/**
 * Composition provider for notifications
 */
@Composable
fun ProvideNotificationState(content: @Composable () -> Unit) {
    val notificationState = remember { mutableStateOf<EditorNotification?>(null) }
    
    CompositionLocalProvider(LocalNotificationState provides notificationState) {
        content()
    }
}

/**
 * Hook to show notifications
 */
@Composable
fun rememberNotificationState(): (EditorNotification) -> Unit {
    val state = LocalNotificationState.current
    
    return { notification ->
        state.value = notification
    }
}
