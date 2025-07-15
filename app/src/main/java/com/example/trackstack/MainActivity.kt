package com.example.trackstack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Icon
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrackStackApp()
        }
    }
}

@Composable
fun TrackStackApp() {
    val navController = rememberNavController()
    MaterialTheme {
        Scaffold(
            bottomBar = { BottomNavBar(navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Calendar.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Calendar.route) { CalendarScreen() }
                composable(Screen.TemplateLibrary.route) { TemplateLibraryScreen() }
                composable(Screen.Stats.route) { StatsScreen() }
                composable(Screen.Settings.route) { SettingsScreen() }
            }
        }
    }
}

@Composable
fun Greeting() {
    Text("Hello, TrackStack!")
}

@Preview
@Composable
fun GreetingPreview() {
    TrackStackApp()
}

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Calendar : Screen("calendar", Icons.Filled.Home, "Calendar")
    object TemplateLibrary : Screen("templates", Icons.Filled.List, "Templates")
    object Stats : Screen("stats", Icons.Filled.ShowChart, "Stats")
    object Settings : Screen("settings", Icons.Filled.Settings, "Settings")
}

@Composable
fun BottomNavBar(navController: androidx.navigation.NavHostController) {
    val items = listOf(
        Screen.Calendar,
        Screen.TemplateLibrary,
        Screen.Stats,
        Screen.Settings
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) }
            )
        }
    }
}

@Composable
fun CalendarScreen() {
    Surface { Text("Calendar Screen") }
}

@Composable
fun StatsScreen() {
    Surface { Text("Stats Screen") }
}

@Composable
fun SettingsScreen() {
    Surface { Text("Settings Screen") }
}
