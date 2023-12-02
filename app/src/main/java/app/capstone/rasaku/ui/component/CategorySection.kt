package app.capstone.rasaku.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun CategorySection(
    title : String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.padding(horizontal = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(vertical = 4.dp)
        ){
            items(6){index ->
                FoodCard(
                    name = "Food #$index",
                    imageUrl = "https://placehold.co/96x109/png",
                    onClick = { /*TODO: Navigate to Food Detail*/ }
                )
            }
            item{
                Spacer(modifier = modifier.width(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategorySectionPreview(){
    RasakuTheme {
        CategorySection(title = "Section 1")
    }
}