package app.capstone.rasaku.ui.screen.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.capstone.rasaku.R
import app.capstone.rasaku.model.Favorite
import app.capstone.rasaku.model.Food
import app.capstone.rasaku.model.FoodsItem
import app.capstone.rasaku.model.History
import app.capstone.rasaku.ui.ViewModelFactory
import app.capstone.rasaku.ui.common.UiState
import app.capstone.rasaku.ui.component.EmptyView
import app.capstone.rasaku.ui.component.FoodCard
import app.capstone.rasaku.ui.component.Loading
import app.capstone.rasaku.ui.component.ModalDialog
import app.capstone.rasaku.utils.gridItems
import app.capstone.rasaku.utils.isValidCollectionName
import coil.compose.AsyncImage
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Composable
fun DetailScreen(
    id: Int,
    navigateToFavoriteList: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context)
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()

    when (uiState) {
        is UiState.Loading -> {
            Loading()
            viewModel.getFoodById(id)
        }

        is UiState.Success -> {
            val food = (uiState as UiState.Success<DetailState>).data.food
            val recommendations = (uiState as UiState.Success<DetailState>).data.recommendations
            food?.let {
                val history = History(
                    id = it.idFood!!,
                    name = it.foodName!!,
                    imageUrl = it.imagesUrl!!,
                )
                viewModel.insertHistory(history)
            }
            DetailContent(
                food = food,
                recommendations = recommendations,
                dialogState = dialogState,
                getFavorite = {
                    viewModel.getFavorites()
                },
                insertFood = {
                    viewModel.insertFood(it)
                },
                insertFavorite = {
                    viewModel.insertFavorites(it)
                },
                navigateToFavoriteList = navigateToFavoriteList,
                navigateBack = navigateBack,
                modifier = modifier,
            )
        }

        is UiState.Error -> EmptyView(message = (uiState as UiState.Error).errorMessage)
    }
}

@Composable
private fun DetailContent(
    food: FoodsItem?,
    recommendations: List<FoodsItem>,
    dialogState: UiState<DetailState>,
    getFavorite: () -> Unit,
    insertFood: (Food) -> Unit,
    insertFavorite: (Favorite) -> Unit,
    navigateToFavoriteList: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isShowFavoriteDialog by remember { mutableStateOf(false) }
    var isShowFavoriteForm by remember { mutableStateOf(false) }
    var isShowAlertDialog by remember { mutableStateOf(false) }

    if (food != null) Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn {
            item {
                Box {
                    AsyncImage(
                        model = food.imagesUrl,
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.img_placeholder),
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    IconButton(
                        onClick = navigateBack,
                        modifier = modifier
                            .windowInsetsPadding(WindowInsets.safeGestures)
                            .padding(horizontal = 24.dp)
                            .background(Color(0x24000000), RoundedCornerShape(8.dp))
                            .blur(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Box(
                        modifier = modifier
                            .offset(0.dp, 276.dp)
                            .align(Alignment.TopCenter)
                            .height(48.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {
                        Text(
                            text = food.foodName.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 24.dp)
                        )
                    }
                }
                Spacer(modifier = modifier.height(48.dp))
            }
            item {
                Text(
                    text = food.descriptionFood.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify,
                    modifier = modifier.padding(horizontal = 32.dp)
                )
            }
            item {
                Divider(modifier = modifier.padding(horizontal = 24.dp, vertical = 16.dp))
                Text(
                    text = "Resep Makanan",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.padding(horizontal = 32.dp)
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = food.foodRecipe.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify,
                    modifier = modifier.padding(horizontal = 32.dp)
                )
            }
            if (recommendations.isNotEmpty()) {
                item {
                    Divider(modifier = modifier.padding(horizontal = 24.dp, vertical = 16.dp))
                    Text(
                        text = "Rekomendasi Makanan Serupa",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(modifier = modifier.height(8.dp))
                }
                gridItems(
                    data = recommendations,
                    columnCount = 3,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = modifier.padding(horizontal = 24.dp)
                ) { item ->
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        FoodCard(
                            name = item.foodName.toString(),
                            imageUrl = item.imagesUrl.toString(),
                            onClick = {
                                navigateToFavoriteList(item.idFood!!)
                            },
                        )
                    }
                }
            }
            item { Spacer(modifier = modifier.height(96.dp)) }
        }

        FloatingActionButton(
            onClick = {
                isShowFavoriteDialog = true
            },
            modifier = modifier
                .align(Alignment.BottomEnd)
                .windowInsetsPadding(WindowInsets.safeGestures)
                .padding(horizontal = 24.dp)
        ) {
            Icon(imageVector = Icons.Rounded.Favorite, contentDescription = null)
        }

        if (isShowFavoriteDialog) {
            FavoriteDialog(state = dialogState, getFavorite = getFavorite, handleItemClick = {
                val foodsItem = Food(food.idFood!!, food.foodName!!, food.imagesUrl!!, it)
                insertFood(foodsItem)
                isShowFavoriteDialog = false
                isShowAlertDialog = true
                Executors.newSingleThreadScheduledExecutor().schedule(
                    { isShowAlertDialog = false }, 800, TimeUnit.MILLISECONDS
                )
            }, handleButtonClick = {
                isShowFavoriteDialog = false
                isShowFavoriteForm = true
            }, setShowDialog = {
                isShowFavoriteDialog = it
            })
        }

        if (isShowFavoriteForm) {
            AddFavoriteForm(setShowDialog = {
                isShowFavoriteForm = it
            }, handleButtonClick = {
                insertFavorite(it)
                isShowFavoriteForm = false
                isShowFavoriteDialog = true
            })
        }

        if (isShowAlertDialog) {
            ModalDialog(body = "${food.foodName} ditambahkan ke dalam koleksi.", setShowDialog = {
                isShowFavoriteDialog = it
            })
        }

    } else EmptyView(message = stringResource(R.string.food_unavailable))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FavoriteDialog(
    state: UiState<DetailState>,
    getFavorite: () -> Unit,
    handleItemClick: (Long) -> Unit,
    handleButtonClick: () -> Unit,
    setShowDialog: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalDialog(body = {
        val favorites = mutableListOf<Favorite>()

        LazyColumn(
            modifier = modifier.padding(32.dp)
        ) {
            when (state) {
                is UiState.Loading -> {
                    item { Loading() }
                    getFavorite()
                }

                is UiState.Success -> {
                    favorites.addAll(state.data.favorites)
                    items(favorites) { item ->
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            },
                            modifier = modifier
                                .clickable {
                                    handleItemClick(item.id)
                                }
                                .background(Color.Transparent)
                                .animateItemPlacement(),
                        )
                    }
                    if (favorites.isNotEmpty()) item { Spacer(modifier = modifier.height(24.dp)) }
                    item {
                        Button(
                            onClick = {
                                handleButtonClick()
                            }, modifier = modifier.fillMaxWidth()
                        ) {
                            Text(text = "Buat Koleksi Baru")
                        }
                    }
                }

                is UiState.Error -> item {
                    EmptyView(message = state.errorMessage)
                }
            }
        }
    }, setShowDialog = {
        setShowDialog(it)
    })
}

@Composable
private fun AddFavoriteForm(
    setShowDialog: (Boolean) -> Unit,
    handleButtonClick: (Favorite) -> Unit,
    modifier: Modifier = Modifier,
) {
    var text by remember { mutableStateOf("") }
    var isTextFieldError by remember { mutableStateOf(false) }
    var isButtonEnabled by remember { mutableStateOf(true) }

    ModalDialog(
        body = {
            Column {
                Text(
                    text = stringResource(R.string.create_favorite_collection),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.padding(
                        top = 24.dp, start = 32.dp, end = 32.dp, bottom = 12.dp
                    ),
                )
                TextField(value = text,
                    onValueChange = {
                        if (it.length < 25) text = it
                        isTextFieldError = !it.isValidCollectionName()
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.enter_collection_name)
                        )
                    },
                    supportingText = {
                        if (isTextFieldError) Text(
                            text = when {
                                text.isEmpty() -> stringResource(R.string.collection_name_cant_be_empty)
                                text.length > 24 -> stringResource(R.string.max_char, 24)
                                else -> ""
                            }, color = MaterialTheme.colorScheme.error
                        )

                    },
                    isError = isTextFieldError,
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        isTextFieldError = !text.isValidCollectionName()
                        if (!isTextFieldError) {
                            isButtonEnabled = false
                            val favorite = Favorite(name = text)
                            handleButtonClick(favorite)
                            isButtonEnabled = true
                        }
                    }),
                    modifier = modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth()
                )
                Button(
                    onClick = {
                        isTextFieldError = !text.isValidCollectionName()
                        if (!isTextFieldError) {
                            isButtonEnabled = false
                            val favorite = Favorite(name = text)
                            handleButtonClick(favorite)
                            isButtonEnabled = true
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
        },
        setShowDialog = {
            setShowDialog(it)
            text = ""
        },
    )
}
