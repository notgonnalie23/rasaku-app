package app.capstone.rasaku.ui.screen.search

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.capstone.rasaku.ui.component.CategorySection
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    SearchContent(
        modifier = modifier,
    )
}

@Composable
private fun SearchContent(
    modifier: Modifier = Modifier,
) {
    LazyColumn {
        items(4) { index ->
            CategorySection(title = "Section #$index")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    RasakuTheme { SearchScreen() }
}