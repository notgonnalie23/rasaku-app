package app.capstone.rasaku

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun RasakuApp(
    modifier: Modifier = Modifier,
){
    Text("Hello Android!")
}

@Preview(showBackground = true)
@Composable
fun RasakuAppPreview(){
    RasakuTheme {
        RasakuApp()
    }
}