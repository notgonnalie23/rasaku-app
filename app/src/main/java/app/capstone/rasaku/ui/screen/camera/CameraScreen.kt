package app.capstone.rasaku.ui.screen.camera

import android.content.Context
import android.graphics.Bitmap
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
import app.capstone.rasaku.ml.Model
import app.capstone.rasaku.ui.component.CameraPreview
import app.capstone.rasaku.ui.component.TransparentClipLayout
import app.capstone.rasaku.utils.FOOD_LIST
import app.capstone.rasaku.utils.FOOD_NAMES
import app.capstone.rasaku.utils.IMAGE_SIZE
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

@Composable
fun CameraScreen(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    CameraContent(
        navigateToDetail = navigateToDetail,
        modifier = modifier,
    )
}

@Composable
private fun CameraContent(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
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
            controller = controller, modifier = modifier.matchParentSize()
        )

        TransparentClipLayout(
            width = 256.dp, height = 256.dp, modifier = modifier.matchParentSize()
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

                            val imageBitmap = image.toBitmap()
                            val scaledBitmap = Bitmap.createScaledBitmap(
                                imageBitmap, IMAGE_SIZE, IMAGE_SIZE, false
                            )

                            classifyImage(
                                image = scaledBitmap, navigateToDetail = navigateToDetail
                            )
                            Log.d("Alert", "Camera take a picture")
                            image.close()
                        }

                        override fun onError(exception: ImageCaptureException) {
                            super.onError(exception)
                            Log.e("Alert", "Couldn't take photo", exception)
                        }

                        private fun classifyImage(
                            image: Bitmap, navigateToDetail: (Int) -> Unit
                        ) {
                            try {
                                val model = Model.newInstance(context)
                                val inputFeature0 = TensorBuffer.createFixedSize(
                                    intArrayOf(1, 128, 128, 3), DataType.FLOAT32
                                )
                                val byteBuffer =
                                    ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3)
                                byteBuffer.order(ByteOrder.nativeOrder())

                                val intValues = IntArray(IMAGE_SIZE * IMAGE_SIZE)

                                image.getPixels(
                                    intValues, 0, image.width, 0, 0, image.width, image.height
                                )
                                var pixel = 0
                                for (i in 0 until IMAGE_SIZE) {
                                    for (j in 0 until IMAGE_SIZE) {
                                        val `value` = intValues[pixel++]
                                        byteBuffer.putFloat((`value` shr 16 and 0xFF) * (1f / 255f))
                                        byteBuffer.putFloat((`value` shr 8 and 0xFF) * (1f / 255f))
                                        byteBuffer.putFloat((`value` and 0xFF) * (1f / 255f))
                                    }
                                }

                                inputFeature0.loadBuffer(byteBuffer)

                                val output = model.process(inputFeature0)
                                val outputFeature0 = output.outputFeature0AsTensorBuffer
                                val confidences = outputFeature0.floatArray

                                var maxPos = 0
                                var maxConfidence = 0F

                                for (i in confidences.indices) {
                                    if (confidences[i] > maxConfidence) {
                                        maxConfidence = confidences[i]
                                        maxPos = i
                                        Log.d("Alert","${FOOD_LIST[maxPos]}, ${FOOD_NAMES[maxPos]}")
                                    }
                                }
                                Log.d("Alert","${FOOD_LIST[maxPos]}, ${FOOD_NAMES[maxPos]}")
                                navigateToDetail(FOOD_LIST[maxPos])
                                model.close()
                            } catch (e: Exception) {
                                Log.e("Alert", e.message.toString())
                            }
                        }
                    })
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
