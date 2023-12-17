package app.capstone.rasaku.ui.screen.search

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.capstone.rasaku.R
import app.capstone.rasaku.model.FoodsItem
import app.capstone.rasaku.ui.ViewModelFactory
import app.capstone.rasaku.ui.common.UiState
import app.capstone.rasaku.ui.component.CategorySection
import app.capstone.rasaku.ui.component.EmptyView
import app.capstone.rasaku.ui.component.Loading
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun SearchScreen(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val viewModel: SearchViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context)
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is UiState.Loading -> {
            Loading()
            viewModel.getFoods()
        }
        is UiState.Success ->
            SearchContent(
                foods = (uiState as UiState.Success<SearchState>).data.foods,
                navigateToDetail = navigateToDetail,
                modifier = modifier,
            )

        is UiState.Error ->
            EmptyView(message = (uiState as UiState.Error).errorMessage)
    }
}

@Composable
private fun SearchContent(
    foods : List<FoodsItem?>?,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (foods != null) {
        LazyColumn {
            item { Spacer(modifier.height(16.dp)) }

            val foodsGroupedByCity = foods.groupBy { it?.city }

            for ((city, foodsList) in foodsGroupedByCity){
                item {
                    CategorySection(
                        title = "Makanan Tradisional $city",
                        foods = foodsList,
                        onItemClick = navigateToDetail
                    )
                }
            }
        }
    } else EmptyView(message = stringResource(R.string.data_unavailable))
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    RasakuTheme { SearchScreen({}) }
}