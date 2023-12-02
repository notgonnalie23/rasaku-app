package app.capstone.rasaku.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.R
import app.capstone.rasaku.ui.component.FoodCard
import app.capstone.rasaku.ui.component.Header
import app.capstone.rasaku.ui.theme.RasakuTheme
import coil.compose.AsyncImage

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToSearch: () -> Unit
) {
    HomeContent(
        imageUrl = "https://placehold.co/48x48/png",
        navigateToSearch = navigateToSearch,
        modifier = modifier,
    )
}

@Composable
private fun HomeContent(
    imageUrl: String,
    navigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        // TODO : Ganti data dummy jadi data dari API
        val dummyImage = "https://placehold.co/375x352/png"
        val imageList = listOf<String>(
            dummyImage,
            dummyImage,
            dummyImage,
            dummyImage,
        )

        Header(
            imageUrl = imageUrl,
            navigateToSearchInput = navigateToSearch,
        )
        Carousel(imageList = imageList)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(96.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(horizontal = 32.dp, vertical = 24.dp),
        ) {
            items(9) { index ->
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    FoodCard(
                        name = "Food #${index + 1}",
                        imageUrl = "https://placehold.co/96x109/png",
                        onClick = { /*TODO : Navigate to Food Detail*/ },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Carousel(
    imageList: List<String>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        pageCount = { imageList.size }
    )

    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(
            state = pagerState,
        ) { index ->
            AsyncImage(
                model = imageList[index],
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.img_placeholder),
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .height(352.dp)
            )
        }
        Row {
            repeat(imageList.size) { index ->
                val color =
                    if (pagerState.currentPage == index) Color.Gray
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
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    RasakuTheme {
        HomeScreen(navigateToSearch = {})
    }
}