package app.capstone.rasaku

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.capstone.rasaku.navigation.NavigationItem
import app.capstone.rasaku.navigation.Screen
import app.capstone.rasaku.ui.screen.camera.CameraScreen
import app.capstone.rasaku.ui.screen.favorite.FavoriteScreen
import app.capstone.rasaku.ui.screen.history.HistoryScreen
import app.capstone.rasaku.ui.screen.home.HomeScreen
import app.capstone.rasaku.ui.screen.search.SearchScreen
import app.capstone.rasaku.ui.theme.RasakuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RasakuApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val routeWithoutBottomBar = arrayOf(Screen.Camera.route)

    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        bottomBar = {
            if (currentRoute !in routeWithoutBottomBar) BottomBar(navController)
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = modifier.padding(innerPadding),
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Search.route) { SearchScreen() }
            composable(Screen.Camera.route) { CameraScreen() }
            composable(Screen.Favorite.route) { FavoriteScreen() }
            composable(Screen.History.route) { HistoryScreen() }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItem = listOf(
            NavigationItem(
                icon = Icons.Rounded.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                icon = Icons.Rounded.Search,
                screen = Screen.Search
            ),
            NavigationItem(
                icon = Icons.Rounded.PhotoCamera,
                screen = Screen.Camera
            ),
            NavigationItem(
                icon = Icons.Rounded.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                icon = Icons.Rounded.History,
                screen = Screen.History
            ),
        )
        navigationItem.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = null) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RasakuAppPreview() {
    RasakuTheme {
        RasakuApp()
    }
}