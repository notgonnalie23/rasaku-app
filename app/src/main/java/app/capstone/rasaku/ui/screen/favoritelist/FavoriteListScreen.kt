package app.capstone.rasaku.ui.screen.favoritelist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.capstone.rasaku.ui.component.ListComponent
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun FavoriteListScreen(
    id: Long,
    modifier: Modifier = Modifier
) {
    FavoriteListContent(modifier = modifier)
}

@Composable
private fun FavoriteListContent(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier.padding(padding)
        ) {
            items(12) { index ->
                ListComponent(
                    title = "Food #$index",
                    id = 0,
                    onClick = {
                        // TODO : Ke halaman detail
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteListScreenPreview() {
    RasakuTheme { FavoriteListScreen(0) }
}