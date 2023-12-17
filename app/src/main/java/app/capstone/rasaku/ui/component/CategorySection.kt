package app.capstone.rasaku.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.model.FoodsItem
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun CategorySection(
    title : String,
    foods : List<FoodsItem?>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier.padding(horizontal = 32.dp, vertical = 8.dp),
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(vertical = 4.dp)
        ){
            item {
                Spacer(modifier = modifier.width(24.dp))
            }
            items(foods){item ->
                item?.let {
                FoodCard(
                    name = it.foodName.toString(),
                    imageUrl = it.imagesUrl.toString(),
                    onClick = {
                        onItemClick(it.idFood!!)
                    }
                )
                }
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
        CategorySection(title = "Section 1", foods = emptyList(), {})
    }
}