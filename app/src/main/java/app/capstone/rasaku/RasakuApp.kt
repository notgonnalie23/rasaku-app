package app.capstone.rasaku

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CreateNewFolder
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.capstone.rasaku.navigation.NavigationItem
import app.capstone.rasaku.navigation.Screen
import app.capstone.rasaku.ui.screen.camera.CameraScreen
import app.capstone.rasaku.ui.screen.favorite.FavoriteScreen
import app.capstone.rasaku.ui.screen.favoritelist.FavoriteListScreen
import app.capstone.rasaku.ui.screen.history.HistoryScreen
import app.capstone.rasaku.ui.screen.home.HomeScreen
import app.capstone.rasaku.ui.screen.search.SearchScreen
import app.capstone.rasaku.ui.screen.searchinput.SearchInputScreen
import app.capstone.rasaku.ui.screen.searchresult.SearchResultScreen
import app.capstone.rasaku.ui.theme.RasakuTheme
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RasakuApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val routeWithoutBottomBar = arrayOf(Screen.Camera.route)
    val navDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {},
        drawerState = navDrawerState
    ) {
        Scaffold(
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
            topBar = {
                when (currentRoute) {
                    Screen.Home.route,
                    Screen.Search.route,
                    Screen.SearchResult.route -> Header(
                        imageUrl = "https://placehold.co/48x48/png",
                        navigateToSearchInput = { navController.navigate(Screen.SearchInput.route) },
                        openNavigationDrawer = {/* TODO: Open navigation drawer */ },
                    )

                    Screen.Camera.route -> TopAppBar(
                        title = {
                            Text(
                                text = "Makanan Apa Ini?",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = modifier.padding(horizontal = 8.dp)
                            )
                        },
                        navigationIcon = {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .clickable {
                                        navController.navigateUp()
                                    }
                            )
                        }
                    )

                    Screen.Favorite.route -> TopAppBar(
                        title = {
                            Text(
                                text = "Favorit",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = modifier.padding(horizontal = 16.dp)
                            )
                        },
                        actions = {
                            Icon(
                                imageVector = Icons.Rounded.CreateNewFolder,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 24.dp)
                                    .clickable {
                                        // TODO: Open bottom sheet
                                    }
                            )
                        }

                    )

                    Screen.History.route -> TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.history),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = modifier.padding(horizontal = 16.dp)
                            )
                        },
                    )

                    Screen.SearchInput.route -> SearchInputScreen(
                        navigateBack = { navController.navigateUp() },
                        navigateToSearchResult = { query ->
                            navController.navigate(Screen.SearchResult.createRoute(query))
                        }
                    )

                    Screen.FavoriteList.route -> TopAppBar(
                        title = {
                            Text(
                                text = "Daftar Makanan",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = modifier.padding(horizontal = 8.dp)
                            )
                        },
                        navigationIcon = {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .clickable {
                                        navController.navigateUp()
                                    }
                            )
                    })
                }
            },
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
                composable(Screen.Home.route) {
                    HomeScreen()
                }
                composable(Screen.Search.route) {
                    SearchScreen()
                }
                composable(Screen.Camera.route) { CameraScreen() }
                composable(Screen.Favorite.route) { FavoriteScreen(
                    navigateToFavoriteList = {id ->
                        navController.navigate(Screen.FavoriteList.createRoute(id))
                    }
                ) }
                composable(Screen.History.route) { HistoryScreen() }
                composable(Screen.SearchInput.route) {}
                composable(
                    route = Screen.SearchResult.route,
                    arguments = listOf(navArgument("query") { type = NavType.StringType })
                ) {
                    val query = it.arguments?.getString("query") ?: ""
                    SearchResultScreen(
                        query = query,
                    )
                }
                composable(
                    route = Screen.FavoriteList.route,
                    arguments = listOf(navArgument("id") { type = NavType.LongType })
                ) {
                    val id = it.arguments?.getLong("id") ?: -1L
                    FavoriteListScreen(
                        id = id,
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
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
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Header(
    imageUrl: String,
    navigateToSearchInput: () -> Unit,
    openNavigationDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.padding(horizontal = 16.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.img_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable {
                            openNavigationDrawer
                        }
                )
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            navigateToSearchInput()
                        }
                ) {
                    Text(
                        text = stringResource(id = R.string.search_food),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = modifier.padding(horizontal = 24.dp)
                    )
                }
            }
        })
}

@Preview(showBackground = true)
@Composable
private fun HeaderPreview() {
    RasakuTheme {
        Header(
            imageUrl = "",
            navigateToSearchInput = {},
            openNavigationDrawer = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RasakuAppPreview() {
    RasakuTheme {
        RasakuApp()
    }
}