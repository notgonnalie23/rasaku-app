package app.capstone.rasaku.ui.screen.camera

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun CameraScreen(
    modifier : Modifier = Modifier
){
    CameraContent(modifier = modifier)
}

@Composable
private fun CameraContent(
    modifier: Modifier,
) {
    Text("It's a camera page")
}

@Preview(showBackground = true)
@Composable
private fun CameraScreenPreview(){
    RasakuTheme { CameraScreen()}
}