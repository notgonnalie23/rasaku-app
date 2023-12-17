package app.capstone.rasaku.ui.screen.favoritelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.capstone.rasaku.R
import app.capstone.rasaku.model.Food
import app.capstone.rasaku.ui.ViewModelFactory
import app.capstone.rasaku.ui.common.UiState
import app.capstone.rasaku.ui.component.EmptyView
import app.capstone.rasaku.ui.component.ListComponent
import app.capstone.rasaku.ui.component.Loading
import app.capstone.rasaku.ui.component.ModalDialog
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun FavoriteListScreen(
    id: Long,
    navigateToBack: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: FavoriteListViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState){
        is UiState.Loading -> {
            Loading()
            viewModel.getFoods(id)
        }
        is UiState.Success -> {
            val foods = (uiState as UiState.Success<FavoriteListState>).data.foods
    FavoriteListContent(
        id = id,
        foods = foods,
        delete = {
            viewModel.deleteFavorite(it)
            navigateToBack()
        },
        deleteFood = {foodId, favoriteId ->
            viewModel.deleteFood(foodId, favoriteId)
        },
        navigateToDetail = navigateToDetail,
        modifier = modifier,
    )
        }
        is UiState.Error -> EmptyView(message = (uiState as UiState.Error).errorMessage)
    }

}

@Composable
private fun FavoriteListContent(
    id: Long,
    foods : List<Food>,
    delete: (Long) -> Unit,
    deleteFood: (Int, Long) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedId by remember { mutableIntStateOf(-1)}
    var selectedFood by remember { mutableStateOf("")}
    var isShowDialog by remember { mutableStateOf(false) }
    var isShowFoodDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        LazyColumn {
            items(foods) { item ->
                ListComponent(
                    title = item.name,
                    imageUrl = item.imageUrl,
                    onClick = {
                        navigateToDetail(item.foodId)
                    },
                    onHold = {
                        selectedId = item.foodId
                        selectedFood = item.name
                        isShowFoodDialog = true
                    }
                )
            }
        }
        FloatingActionButton(
            onClick = {
                isShowDialog = true
            },
            modifier = modifier
                .align(Alignment.BottomEnd)
                .windowInsetsPadding(WindowInsets.safeGestures)
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null
            )
        }
        if (isShowDialog)
            ModalDialog(
                title = stringResource(R.string.delete_collection),
                body = stringResource(R.string.alert_delete_this_collection),
                positive = stringResource(R.string.delete),
                negative = stringResource(R.string.cancel),
                onPositiveClick = {
                    delete(id)
                    isShowDialog = false
                },
                onNegativeClick = {
                    isShowDialog = false
                },
                setShowDialog = {
                    isShowDialog = it
                }
            )
        if (isShowFoodDialog)
            ModalDialog(
                title = stringResource(R.string.delete_food),
                body = stringResource(R.string.alert_delete_this_food, selectedFood),
                positive = stringResource(R.string.delete),
                negative = stringResource(R.string.cancel),
                onPositiveClick = {
                    deleteFood(selectedId, id)
                    isShowFoodDialog = false
                },
                onNegativeClick = {
                    isShowFoodDialog = false
                },
                setShowDialog = {
                    isShowFoodDialog = it
                }
            )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteListScreenPreview() {
    RasakuTheme { FavoriteListScreen(0, {}, {}) }
}