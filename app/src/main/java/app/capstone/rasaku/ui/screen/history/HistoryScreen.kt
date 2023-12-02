package app.capstone.rasaku.ui.screen.history

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun HistoryScreen(
    modifier : Modifier = Modifier
){
    HistoryContent(modifier = modifier)
}

@Composable
private fun HistoryContent(
    modifier: Modifier,
) {
    Text("It's a history page")
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview(){
    RasakuTheme { HistoryScreen()}
}