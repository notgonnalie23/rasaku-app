package app.capstone.rasaku.ui.screen.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.ui.theme.seed

@Composable
fun WelcomeScreen(
    navigateToLogin: () -> Unit,
    navigateToRegister: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .windowInsetsPadding(WindowInsets.safeGestures)
            .padding(horizontal = 32.dp)
            .fillMaxSize()
    ){
        Column {
            Button(
                onClick = navigateToLogin,
                colors = ButtonDefaults.buttonColors(
                    containerColor = seed
                ),
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Masuk"
                )
            }
            Button(
                onClick = navigateToRegister,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Daftar"
                )
            }
        }
    }
}