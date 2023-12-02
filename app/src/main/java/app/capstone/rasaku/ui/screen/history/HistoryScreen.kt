package app.capstone.rasaku.ui.screen.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun HomeScreen(
    modifier : Modifier = Modifier
){
    HomeContent(modifier = modifier)
}

@Composable
private fun HomeContent(
    modifier: Modifier,
) {
    Text("It's a homepage")
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview(){
    RasakuTheme { HomeScreen()}
}