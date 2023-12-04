package app.capstone.rasaku

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import app.capstone.rasaku.ui.theme.RasakuTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
        }

        if(!hasRequiredPermission()){
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSION, 0
            )
        }

        setContent {
            RasakuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RasakuApp()
                }
            }
        }
    }

    private fun hasRequiredPermission() : Boolean  = CAMERAX_PERMISSION.all{
        ContextCompat.checkSelfPermission(
            applicationContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val CAMERAX_PERMISSION = arrayOf(
            Manifest.permission.CAMERA
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RasakuTheme {
        Greeting("Android")
    }
}