package app.capstone.rasaku.ui.screen.searchresult

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.ui.component.FoodCard
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun SearchResultScreen(
    query : String,
    modifier: Modifier = Modifier
){
//    TODO: Get item by query
    SearchResultContent(
        modifier = modifier
    )
}

@Composable
private fun SearchResultContent(
    modifier: Modifier = Modifier
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(96.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(horizontal = 32.dp, vertical = 24.dp),
    ) {
        items(5) { index ->
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

@Preview(showBackground = true)
@Composable
private fun SearchResultScreenPreview(){
    RasakuTheme {
        SearchResultScreen(query = "")
    }
}