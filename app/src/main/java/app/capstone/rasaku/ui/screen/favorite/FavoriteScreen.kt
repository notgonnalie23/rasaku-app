package app.capstone.rasaku.ui.screen.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.capstone.rasaku.R
import app.capstone.rasaku.model.Favorite
import app.capstone.rasaku.ui.ViewModelFactory
import app.capstone.rasaku.ui.common.UiState
import app.capstone.rasaku.ui.component.EmptyView
import app.capstone.rasaku.ui.component.ListComponent
import app.capstone.rasaku.ui.component.ModalDialog
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun FavoriteScreen(
    navigateToFavoriteList: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is UiState.Loading -> viewModel.getFavorites()

        is UiState.Success ->
            FavoriteContent(
                favorites = (uiState as UiState.Success<FavoriteState>).data.favorites,
                onClick = navigateToFavoriteList,
                delete = {
                    viewModel.deleteFavorite(it)
                },
                modifier = modifier
            )

        is UiState.Error ->
            EmptyView(message = (uiState as UiState.Error).errorMessage)
    }
}

@Composable
private fun FavoriteContent(
    favorites: List<Favorite>,
    onClick: (Long) -> Unit,
    delete: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isShowDialog by remember { mutableStateOf(false) }
    var selectedId by remember { mutableLongStateOf(0) }
    var selectedName by remember { mutableStateOf("")}

    if (favorites.isNotEmpty())
        Box {
            LazyColumn {
                items(favorites) { data ->
                    ListComponent(
                        title = data.name,
                        body = stringResource(R.string.d_menu, data.foodCount),
                        onClick = { onClick(data.id) },
                        onHold = {
                            selectedId = data.id
                            selectedName = data.name
                            isShowDialog = true
                        },
                        imageUrl = data.imageUrl,
                        modifier = modifier
                    )
                }
            }
            if (isShowDialog)
                ModalDialog(
                    title = stringResource(R.string.delete_collection),
                    body = stringResource(R.string.alert_delete_collection, selectedName),
                    positive = stringResource(R.string.delete),
                    negative = stringResource(R.string.cancel),
                    onPositiveClick = {
                        delete(selectedId)
                    },
                    onNegativeClick = {
                        isShowDialog = false
                    },
                    setShowDialog = {
                        isShowDialog = it
                    }
                )
        }
    else EmptyView(message = stringResource(R.string.collections_unavailable))
}

@Preview(showBackground = true)
@Composable
private fun FavoriteScreenPreview() {
    RasakuTheme { FavoriteScreen({}) }
}