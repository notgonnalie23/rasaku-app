package app.capstone.rasaku.ui.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.ui.component.CategorySection
import app.capstone.rasaku.ui.component.Header
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun SearchScreen(
    navigateToSearch: () -> Unit,
    modifier : Modifier = Modifier
){
    SearchContent(
        imageUrl = "https://placehold.co/48x48/png",
        navigateToSearch = navigateToSearch,
        modifier = modifier,
        )
}

@Composable
private fun SearchContent(
    imageUrl: String,
    navigateToSearch: () -> Unit,
    modifier: Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        item{
        Header(imageUrl = imageUrl, navigateToSearchInput = navigateToSearch)
        }
        items(4){ index ->
            CategorySection(title = "Section #$index")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview(){
    RasakuTheme { SearchScreen({})}
}