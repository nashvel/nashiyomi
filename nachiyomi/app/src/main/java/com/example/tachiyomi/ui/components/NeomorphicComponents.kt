package com.example.tachiyomi.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A simplified neomorphic surface with a soft UI look.
 * For stability, this implementation uses standard Material composables with subtle shadow effects.
 */
@Composable
fun NeomorphicSurface(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    shadowElevation: Dp = 4.dp,
    content: @Composable () -> Unit
) {
    // Using standard Surface with elevation for stability
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius)),
        shape = RoundedCornerShape(cornerRadius),
        elevation = shadowElevation,
        color = MaterialTheme.colors.surface
    ) {
        content()
    }
}

/**
 * A neomorphic card component with a soft UI look.
 * Uses standard Card with elevation for stability.
 */
@Composable
fun NeomorphicCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    shadowElevation: Dp = 4.dp,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius),
        elevation = shadowElevation
    ) {
        content()
    }
}

/**
 * A neomorphic top app bar with a soft UI look.
 * Uses standard TopAppBar with custom shape for stability.
 */
@Composable
fun NeomorphicTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Surface(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
        color = MaterialTheme.colors.surface
    ) {
        TopAppBar(
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.onSurface,
            elevation = 0.dp
        )
    }
}

/**
 * A neomorphic bottom navigation bar with a soft UI look.
 * Uses a Surface with elevation for stability.
 */
@Composable
fun NeomorphicBottomBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        color = MaterialTheme.colors.surface
    ) {
        BottomNavigation(
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.primary,
            elevation = 0.dp,
            modifier = Modifier.height(56.dp).fillMaxWidth(),
            content = content
        )
    }
}

/**
 * A neomorphic button with a soft UI look.
 * Uses standard Button with custom elevation parameters for stability.
 */
@Composable
fun NeomorphicButton(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    shadowElevation: Dp = 4.dp,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius),
        elevation = ButtonDefaults.elevation(
            defaultElevation = shadowElevation,
            pressedElevation = shadowElevation / 2,
            disabledElevation = 1.dp
        )
    ) {
        content()
    }
}

/**
 * A neomorphic floating action button with a soft UI look.
 */
@Composable
fun NeomorphicFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.size(56.dp),
        contentAlignment = Alignment.Center
    ) {
        NeomorphicSurface(
            modifier = Modifier.fillMaxSize(),
            cornerRadius = 28.dp,
            shadowElevation = 8.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onClick,
                    modifier = Modifier.fillMaxSize()
                ) {
                    content()
                }
            }
        }
    }
}

/**
 * Extension to determine if the theme is light or dark
 */
val Color.isLight: Boolean
    get() = red * 0.299 + green * 0.587 + blue * 0.114 > 0.5
