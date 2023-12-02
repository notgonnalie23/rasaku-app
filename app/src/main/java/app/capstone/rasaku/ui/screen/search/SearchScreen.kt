package app.capstone.rasaku.ui.screen.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun SearchScreen(
    modifier : Modifier = Modifier
){
    SearchContent(modifier = modifier)
}

@Composable
private fun SearchContent(
    modifier: Modifier,
) {
    Text("It's a search page")
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview(){
    RasakuTheme { SearchScreen()}
}