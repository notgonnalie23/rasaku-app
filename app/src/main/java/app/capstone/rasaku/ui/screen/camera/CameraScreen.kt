package app.capstone.rasaku.ui.screen.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import app.capstone.rasaku.R
import app.capstone.rasaku.ui.component.CameraPreview
import app.capstone.rasaku.ui.component.TransparentClipLayout

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier
) {
    CameraContent(modifier = modifier)
}

@Composable
private fun CameraContent(
    modifier: Modifier,
) {
    val context: Context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }

    controller.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CameraPreview(
            controller = controller,
            modifier = modifier.matchParentSize()
        )

        TransparentClipLayout(
            width = 273.dp,
            height = 412.dp,
            modifier = modifier.matchParentSize()
        )

        Text(
            text = stringResource(R.string.info_take_picture),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = modifier
                .align(Alignment.TopCenter)
                .windowInsetsPadding(WindowInsets.safeContent)
                .padding(horizontal = 32.dp)
        )

        FloatingActionButton(
            onClick = {
                controller.takePicture(
                    ContextCompat.getMainExecutor(context),
                    object : OnImageCapturedCallback() {
                        override fun onCaptureSuccess(image: ImageProxy) {
                            super.onCaptureSuccess(image)

                            val matrix = Matrix().apply {
                                postRotate(image.imageInfo.rotationDegrees.toFloat())
                            }
                            val rotatedBitmap = Bitmap.createBitmap(
                                image.toBitmap(),
                                0,
                                0,
                                image.width,
                                image.height,
                                matrix,
                                true
                            )
                            Log.d("Camera", "Camera take a picture")
                        }

                        override fun onError(exception: ImageCaptureException) {
                            super.onError(exception)
                            Log.e("Camera", "Couldn't take photo", exception)
                        }
                    }
                )
            },
            modifier = modifier
                .windowInsetsPadding(WindowInsets.safeGestures)
                .align(Alignment.BottomCenter)
        ) {
            Icon(
                imageVector = Icons.Rounded.CameraAlt,
                contentDescription = null,
            )
        }
    }
}