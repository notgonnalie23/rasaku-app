package app.capstone.rasaku.ui.screen.favorite

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.capstone.rasaku.model.Favorite
import app.capstone.rasaku.ui.ViewModelFactory
import app.capstone.rasaku.ui.common.UiState
import app.capstone.rasaku.ui.component.EmptyView
import app.capstone.rasaku.ui.component.ListComponent
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
                navigateToFavoriteList = navigateToFavoriteList,
                modifier = modifier
            )

        is UiState.Error ->
            EmptyView(message = (uiState as UiState.Error).errorMessage)
    }
}

@Composable
private fun FavoriteContent(
    favorites: List<Favorite>,
    navigateToFavoriteList: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (favorites.isNotEmpty())
        LazyColumn {
            items(favorites) { data ->
                ListComponent(
                    id = data.id,
                    title = data.name,
                    onClick = navigateToFavoriteList,
                    imageUrl = data.imageUrl,
                    modifier = modifier
                )
            }
        }
    else EmptyView(message = "Belum ada makanan favorit.")
}

@Preview(showBackground = true)
@Composable
private fun FavoriteScreenPreview() {
    RasakuTheme { FavoriteScreen({}) }
}