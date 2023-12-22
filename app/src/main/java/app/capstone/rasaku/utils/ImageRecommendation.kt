package app.capstone.rasaku.utils

import android.content.Context
import android.graphics.Bitmap
import app.capstone.rasaku.ml.ModelRec
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun ModelUtils(context: Context, image: Bitmap){
    val modelRec = ModelRec.newInstance(context)

    val inputFeature0 = TensorBuffer.createFixedSize(
        intArrayOf(1, 224, 224, 3), DataType.FLOAT32
    )
    val byteBuffer =
        ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
    byteBuffer.order(ByteOrder.nativeOrder())

    val intValues = IntArray(224 * 224)

    image.getPixels(
        intValues, 0, 224, 0, 0, 224, 224
    )
    var pixel = 0
    for (i in 0 until 224) {
        for (j in 0 until 224) {
            val `value` = intValues[pixel++]
            byteBuffer.putFloat((`value` shr 16 and 0xFF) * (1f / 255f))
            byteBuffer.putFloat((`value` shr 8 and 0xFF) * (1f / 255f))
            byteBuffer.putFloat((`value` and 0xFF) * (1f / 255f))
        }
    }

    inputFeature0.loadBuffer(byteBuffer)

    val output = modelRec.process(inputFeature0)
    val outputFeature0 = output.outputFeature0AsTensorBuffer
    val similaties = outputFeature0.floatArray

    var maxPos = 0

    for (i in similaties.indices) {
        for (j in similaties.indices) {
        val similarity = cosineSimilarity(similaties[i],similaties[j])
            similarities
        }

    }

}
    private fun cosineSimilarity(a: FloatArray, b: FloatArray): Float {
        var dotProduct = 0.0
        var normA = 0.0
        var normB = 0.0
        for (i in a.indices) {
            dotProduct += a[i] * b[i]
            normA += a[i] * a[i]
            normB += b[i] * b[i]
        }
        val normProduct = Math.sqrt(normA) * Math.sqrt(normB)
        return if (normProduct == 0.0) 0.0f else (dotProduct / normProduct).toFloat()
    }
