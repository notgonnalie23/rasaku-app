package app.capstone.rasaku.ui.screen.searchresult

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.capstone.rasaku.model.FoodsItem
import app.capstone.rasaku.ui.ViewModelFactory
import app.capstone.rasaku.ui.common.UiState
import app.capstone.rasaku.ui.component.EmptyView
import app.capstone.rasaku.ui.component.FoodCard
import app.capstone.rasaku.ui.component.Loading
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun SearchResultScreen(
    query: String, navigateToDetail: (Int) -> Unit, modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val viewModel: SearchResultViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context)
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is UiState.Loading -> {
            Loading()
            viewModel.findFoods(query)
        }

        is UiState.Success -> {
            val foods = (uiState as UiState.Success<SearchResultState>).data.foods
            SearchResultContent(
                foods = foods, navigateToDetail = navigateToDetail, modifier = modifier,
            )

        }

        is UiState.Error -> EmptyView(message = (uiState as UiState.Error).errorMessage)
    }
}

@Composable
private fun SearchResultContent(
    foods: List<FoodsItem>, navigateToDetail: (Int) -> Unit, modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(96.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(horizontal = 32.dp, vertical = 24.dp),
    ) {
        items(foods) { item ->
            Box(
                contentAlignment = Alignment.Center,
            ) {
                FoodCard(
                    name = item.foodName!!,
                    imageUrl = item.imagesUrl!!,
                    onClick = {
                        navigateToDetail(item.idFood!!)
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultScreenPreview() {
    RasakuTheme {
        SearchResultScreen(query = "", {})
    }
}