package app.capstone.rasaku

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CreateNewFolder
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.capstone.rasaku.model.Favorite
import app.capstone.rasaku.ui.ViewModelFactory
import app.capstone.rasaku.ui.navigation.NavigationItem
import app.capstone.rasaku.ui.navigation.Screen
import app.capstone.rasaku.ui.screen.camera.CameraScreen
import app.capstone.rasaku.ui.screen.detail.DetailScreen
import app.capstone.rasaku.ui.screen.favorite.FavoriteScreen
import app.capstone.rasaku.ui.screen.favoritelist.FavoriteListScreen
import app.capstone.rasaku.ui.screen.history.HistoryScreen
import app.capstone.rasaku.ui.screen.home.HomeScreen
import app.capstone.rasaku.ui.screen.login.LoginScreen
import app.capstone.rasaku.ui.screen.register.RegisterScreen
import app.capstone.rasaku.ui.screen.search.SearchScreen
import app.capstone.rasaku.ui.screen.searchinput.SearchInputScreen
import app.capstone.rasaku.ui.screen.searchresult.SearchResultScreen
import app.capstone.rasaku.ui.screen.welcome.WelcomeScreen
import app.capstone.rasaku.ui.theme.RasakuTheme
import app.capstone.rasaku.utils.isValidCollectionName
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RasakuApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val viewModel: MainViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val screenWithoutBottomBar = arrayOf(
        Screen.Camera.route,
        Screen.SearchInput.route,
        Screen.FavoriteList.route,
        Screen.Login.route,
        Screen.Register.route,
        Screen.Welcome.route,
        Screen.FoodDetail.route,
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var collectionName by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            when (currentRoute) {
                Screen.Home.route, Screen.Search.route, Screen.SearchResult.route -> NavigationDrawer(
                    toAccountSettings = {},
                    toThemeSettings = {},
                    toAbout = {},
                    logout = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate(Screen.Welcome.route)
                        }
                    },
                )

                Screen.Favorite.route -> if (isSheetOpen) {
                    BottomSheet(
                        onDismissRequest = { isSheetOpen = false },
                        collectionName = collectionName,
                        setCollectionName = {
                            if (it.length <= 24) collectionName = it
                        },
                        onButtonClick = {
                            viewModel.insertFavorite(Favorite(name = collectionName))
                            collectionName = ""
                        },
                        sheetState = sheetState
                    )
                }
            }
        }, drawerState = drawerState
    ) {
        Scaffold(topBar = {
            when (currentRoute) {
                Screen.Home.route, Screen.Search.route, Screen.SearchResult.route -> Header(
                    imageUrl = null,
                    navigateToSearchInput = { navController.navigate(Screen.SearchInput.route) },
                    profileOnClick = {
                        // TODO: Check user already login or not
                        scope.launch {
                            drawerState.open()
                        }
                    },
                )

                Screen.Camera.route -> TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.what_is_this_food),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = modifier.padding(horizontal = 8.dp)
                        )
                    },
                    navigationIcon = {
                        Icon(imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clickable {
                                    navController.navigateUp()
                                })
                    },
                )

                Screen.Favorite.route -> TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.favorite),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = modifier.padding(horizontal = 16.dp)
                        )
                    },
                    actions = {
                        Icon(imageVector = Icons.Rounded.CreateNewFolder,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 24.dp)
                                .clickable {
                                    scope.launch {
                                        isSheetOpen = true
                                    }
                                })
                    },
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

                Screen.SearchInput.route -> SearchInputScreen(navigateBack = { navController.navigateUp() },
                    navigateToSearchResult = { query ->
                        navController.navigate(Screen.SearchResult.createRoute(query))
                    },
                    navigateToDetail = { id ->
                        navController.navigate(Screen.FoodDetail.createRoute(id))
                    })

                Screen.FavoriteList.route -> TopAppBar(title = {
                    Text(
                        text = stringResource(R.string.food_list),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier.padding(horizontal = 8.dp)
                    )
                }, navigationIcon = {
                    Icon(imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .clickable {
                                navController.navigateUp()
                            })
                })
            }
        }, bottomBar = {
            if (currentRoute !in screenWithoutBottomBar) BottomBar(navController)
        }, modifier = modifier
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = modifier.padding(innerPadding),
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(navigateToDetail = { id ->
                        navController.navigate(Screen.FoodDetail.createRoute(id))
                    })
                }
                composable(Screen.Search.route) {
                    SearchScreen(navigateToDetail = { id ->
                        navController.navigate(Screen.FoodDetail.createRoute(id))
                    })
                }
                composable(Screen.Camera.route) {
                    CameraScreen(navigateToDetail = { id ->
                        navController.navigate(Screen.FoodDetail.createRoute(id))
                    })
                }
                composable(Screen.Favorite.route) {
                    FavoriteScreen(navigateToFavoriteList = { id ->
                        navController.navigate(Screen.FavoriteList.createRoute(id))
                    })
                }
                composable(Screen.History.route) {
                    HistoryScreen(navigateToDetail = { id ->
                        navController.navigate(Screen.FoodDetail.createRoute(id))
                    })
                }
                composable(Screen.Welcome.route) {
                    WelcomeScreen(navigateToLogin = { navController.navigate(Screen.Login.route) },
                        navigateToRegister = { navController.navigate(Screen.Register.route) })
                }
                composable(Screen.Login.route) {
                    LoginScreen(navigateToHome = { navController.navigate(Screen.Home.route) })
                }
                composable(Screen.Register.route) {
                    RegisterScreen(navigateToLogin = { navController.navigate(Screen.Login.route) })
                }
                composable(
                    route = Screen.FoodDetail.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) {
                    val id = it.arguments?.getInt("id") ?: 0
                    DetailScreen(
                        id = id,
                        navigateToFavoriteList = { foodId ->
                            navController.navigate(Screen.FoodDetail.createRoute(foodId))
                        },
                        navigateBack = { navController.navigateUp() },
                    )
                }
                composable(Screen.SearchInput.route) {}
                composable(
                    route = Screen.SearchResult.route,
                    arguments = listOf(navArgument("query") { type = NavType.StringType })
                ) {
                    val query = it.arguments?.getString("query") ?: ""
                    SearchResultScreen(
                        query = query,
                        navigateToDetail = { id ->
                            navController.navigate(Screen.FoodDetail.createRoute(id))
                        },
                    )
                }
                composable(
                    route = Screen.FavoriteList.route,
                    arguments = listOf(navArgument("id") { type = NavType.LongType })
                ) {
                    val id = it.arguments?.getLong("id") ?: -1L
                    FavoriteListScreen(
                        id = id,
                        navigateToBack = { navController.navigateUp() },
                        navigateToDetail = { foodId ->
                            navController.navigate(Screen.FoodDetail.createRoute(foodId))
                        },
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
        modifier = modifier, windowInsets = NavigationBarDefaults.windowInsets
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItem = listOf(
            NavigationItem(
                icon = Icons.Rounded.Home, screen = Screen.Home
            ),
            NavigationItem(
                icon = Icons.Rounded.Search, screen = Screen.Search
            ),
            NavigationItem(
                icon = Icons.Rounded.PhotoCamera, screen = Screen.Camera
            ),
            NavigationItem(
                icon = Icons.Rounded.Favorite, screen = Screen.Favorite
            ),
            NavigationItem(
                icon = Icons.Rounded.History, screen = Screen.History
            ),
        )
        navigationItem.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
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
    imageUrl: String?,
    navigateToSearchInput: () -> Unit,
    profileOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(title = {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.padding(horizontal = 16.dp)
        ) {
            AsyncImage(
                model = imageUrl ?: "https://i.ibb.co/0ZW5YV6/img-profile.png",
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.img_placeholder),
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable {
                        profileOnClick()
                    },
            )
            Box(contentAlignment = Alignment.CenterStart,
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        navigateToSearchInput()
                    }) {
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

@Composable
private fun NavigationDrawer(
    toAccountSettings: () -> Unit,
    toThemeSettings: () -> Unit,
    toAbout: () -> Unit,
    logout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet {
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(208.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = modifier.padding(24.dp)
            ) {
                AsyncImage(
                    model = "https://i.ibb.co/0ZW5YV6/img-profile.png",
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.img_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(84.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = stringResource(R.string.username),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
        ListItem(leadingContent = {
            Icon(
                imageVector = Icons.Rounded.Person,
                contentDescription = null,
            )
        }, headlineContent = {
            Text(
                text = stringResource(R.string.account), style = MaterialTheme.typography.bodyLarge
            )
        }, modifier = modifier.clickable {
            toAccountSettings()
        })
        ListItem(leadingContent = {
            Icon(
                imageVector = Icons.Rounded.Palette,
                contentDescription = null,
            )
        }, headlineContent = {
            Text(
                text = stringResource(R.string.theme), style = MaterialTheme.typography.bodyLarge
            )
        }, modifier = modifier.clickable {
            toThemeSettings()
        })
        ListItem(leadingContent = {
            Icon(
                imageVector = Icons.Rounded.Info,
                contentDescription = null,
            )
        }, headlineContent = {
            Text(
                text = stringResource(R.string.about), style = MaterialTheme.typography.bodyLarge
            )
        }, modifier = modifier.clickable {
            toAbout()
        })
        Spacer(modifier = modifier.fillMaxHeight(.8f))
        ListItem(leadingContent = {
            Icon(imageVector = Icons.Rounded.Logout, contentDescription = null)
        }, headlineContent = {
            Text(
                text = stringResource(R.string.logout), style = MaterialTheme.typography.bodyLarge
            )
        }, modifier = Modifier.clickable {
            logout()
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun BottomSheet(
    collectionName: String,
    onDismissRequest: () -> Unit,
    setCollectionName: (String) -> Unit,
    onButtonClick: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
) {
    var supportingText by remember { mutableStateOf("") }
    var isCollectionNameError by remember { mutableStateOf(false) }
    var isButtonEnabled by remember { mutableStateOf(true) }

    ModalBottomSheet(
        sheetState = sheetState, onDismissRequest = {
            supportingText = ""
            isButtonEnabled = true
            onDismissRequest()
        }, modifier = if (WindowInsets.isImeVisible) modifier.fillMaxSize() else modifier
    ) {
        Text(
            text = stringResource(R.string.create_favorite_collection),
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.padding(
                top = 24.dp, start = 32.dp, end = 32.dp, bottom = 12.dp
            ),
        )
        TextField(value = collectionName,
            onValueChange = {
                setCollectionName(it)
                isCollectionNameError = !it.isValidCollectionName()
            },
            label = {
                Text(
                    text = stringResource(R.string.enter_collection_name)
                )
            },
            supportingText = {
                if (isCollectionNameError) Text(
                    text = when {
                        collectionName.isEmpty() -> stringResource(R.string.collection_name_cant_be_empty)
                        collectionName.length > 24 -> stringResource(R.string.max_char, 24)
                        else -> ""
                    }, color = MaterialTheme.colorScheme.error
                )

            },
            isError = isCollectionNameError,
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                if (isButtonEnabled) {
                    isCollectionNameError = !collectionName.isValidCollectionName()
                    if (!isCollectionNameError) {
                        isButtonEnabled = false
                        onButtonClick()
                        isButtonEnabled = true
                        onDismissRequest()
                    }
                }
            }),
            modifier = modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
                isCollectionNameError = !collectionName.isValidCollectionName()
                if (!isCollectionNameError) {
                    isButtonEnabled = false
                    onButtonClick()
                    isButtonEnabled = true
                    onDismissRequest()
                }
            },
            enabled = isButtonEnabled,
            modifier = modifier
                .padding(32.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.confirm)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HeaderPreview() {
    RasakuTheme {
        Header(
            imageUrl = "",
            navigateToSearchInput = {},
            profileOnClick = {},
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