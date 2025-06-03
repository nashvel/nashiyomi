package com.example.tachiyomi.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import com.example.tachiyomi.ui.components.NeomorphicBottomBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tachiyomi.ui.screens.*

@Composable
fun NashiyomiApp() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    
    // Track navigation state to determine if bottom bar should be shown
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Only show bottom bar for main screens
    val showBottomBar = remember(currentRoute) {
        currentRoute in listOf(
            Screen.Library.route, 
            Screen.Browse.route, 
            Screen.History.route, 
            Screen.Settings.route
        )
    }
    
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { 
            if (showBottomBar) {
                BottomNavigationBar(navController) 
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Library.route) {
        composable(Screen.Library.route) {
            LibraryScreen(
                onMangaClick = { mangaId ->
                    navController.navigate("manga_detail/$mangaId")
                }
            )
        }
        composable(Screen.Browse.route) {
            BrowseScreen(
                onMangaClick = { mangaId ->
                    navController.navigate("manga_detail/$mangaId")
                }
            )
        }
        composable(Screen.History.route) {
            HistoryScreen(
                onMangaClick = { mangaId ->
                    navController.navigate("manga_detail/$mangaId")
                },
                onChapterClick = { chapterId ->
                    navController.navigate("reader/$chapterId")
                }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        
        // Manga detail screen with manga ID parameter
        composable(
            route = "manga_detail/{mangaId}",
            arguments = listOf(navArgument("mangaId") { type = NavType.LongType })
        ) { backStackEntry ->
            val mangaId = backStackEntry.arguments?.getLong("mangaId") ?: return@composable
            MangaDetailScreen(
                mangaId = mangaId,
                onBackClick = { navController.popBackStack() },
                onChapterClick = { chapterId ->
                    navController.navigate("reader/$chapterId")
                }
            )
        }
        
        // Reader screen with chapter ID parameter
        composable(
            route = "reader/{chapterId}",
            arguments = listOf(navArgument("chapterId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chapterId = backStackEntry.arguments?.getLong("chapterId") ?: return@composable
            ReaderScreen(
                chapterId = chapterId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Library,
        Screen.Browse,
        Screen.History,
        Screen.Settings
    )
    
    NeomorphicBottomBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

/**
 * Screen destinations for navigation
 */
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Library : Screen("library", "Library", Icons.Filled.Home)
    object Browse : Screen("browse", "Browse", Icons.Filled.Search)
    object History : Screen("history", "History", Icons.Filled.List)
    object Settings : Screen("settings", "Settings", Icons.Filled.Settings)
}
