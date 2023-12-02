package app.capstone.rasaku.ui.screen.favorite

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun FavoriteScreen(
    modifier : Modifier = Modifier
){
    FavoriteContent(modifier = modifier)
}

@Composable
private fun FavoriteContent(
    modifier: Modifier,
) {
    Text("It's a favorite page")
}

@Preview(showBackground = true)
@Composable
private fun FavoriteScreenPreview(){
    RasakuTheme { FavoriteScreen()}
}