package app.capstone.rasaku.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.capstone.rasaku.R
import app.capstone.rasaku.model.FoodsItem
import app.capstone.rasaku.ui.ViewModelFactory
import app.capstone.rasaku.ui.common.UiState
import app.capstone.rasaku.ui.component.EmptyView
import app.capstone.rasaku.ui.component.FoodCard
import app.capstone.rasaku.ui.component.Loading
import app.capstone.rasaku.ui.theme.RasakuTheme
import app.capstone.rasaku.utils.gridItems
import coil.compose.AsyncImage

@Composable
fun HomeScreen(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context)
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is UiState.Loading -> {
            Loading()
            viewModel.getFoods()
        }

        is UiState.Success -> {
            val (foods, carousel) = (uiState as UiState.Success<HomeState>).data
            HomeContent(
                foods = foods,
                carousel = carousel,
                navigateToDetail = navigateToDetail,
                modifier = modifier,
            )
        }

        is UiState.Error -> EmptyView(message = (uiState as UiState.Error).errorMessage)
    }

}

@Composable
private fun HomeContent(
    foods: List<FoodsItem?>,
    carousel: List<FoodsItem>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        if (foods.isNotEmpty()) {
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                item {
                    Carousel(
                        imageList = carousel,
                        navigateToDetail = navigateToDetail,
                        modifier = modifier.padding(bottom = 16.dp),
                    )
                }
                gridItems(
                    data = foods,
                    columnCount = 3,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                ) { item ->
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        FoodCard(
                            name = item?.foodName.toString(),
                            imageUrl = item?.imagesUrl.toString(),
                            onClick = {
                                navigateToDetail(item?.idFood!!)
                            },
                        )
                    }
                }
                item {
                    Spacer(modifier = modifier.height(24.dp))
                }
            }
        } else EmptyView(message = stringResource(R.string.data_unavailable))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Carousel(
    imageList: List<FoodsItem>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { imageList.size })

    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(
            state = pagerState,
        ) { index ->
            AsyncImage(
                model = imageList[index].imagesUrl,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.img_placeholder),
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .height(352.dp)
                    .clickable {
                        navigateToDetail(imageList[index].idFood!!)
                    },
            )
        }
        Row {
            repeat(imageList.size) { index ->
                val color = if (pagerState.currentPage == index) Color.Gray
                else Color.White
                Box(
                    modifier = Modifier
                        .padding(32.dp)
                        .clip(CircleShape)
                        .size(12.dp)
                        .background(color)
                ) {}
            }
        }
        Spacer(modifier = modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    RasakuTheme {
        HomeScreen({})
    }
}