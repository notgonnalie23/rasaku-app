package app.capstone.rasaku.ui.screen.favoritelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.R
import app.capstone.rasaku.ui.theme.RasakuTheme
import coil.compose.AsyncImage

@Composable
fun FavoriteListScreen(
    id: Long,
    modifier : Modifier = Modifier
){
    FavoriteListContent(modifier = modifier)
}

@Composable
private fun FavoriteListContent(
    modifier: Modifier,
) {
    LazyColumn {
        items(12) { index ->
            ListItem(
                headlineContent = {
                    Text(
                        text = "Food #$index",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                leadingContent = {
                    AsyncImage(
                        model = "https://placehold.co/56x56/png",
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.img_placeholder),
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .padding(start = 24.dp)
                            .size(56.dp)
                    )
                },
                modifier = Modifier.clickable {
//                        TODO: Navigasi ke halaman detail
                }
            )
            Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteListScreenPreview(){
    RasakuTheme { FavoriteListScreen(0)}
}