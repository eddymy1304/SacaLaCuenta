package com.example.sacalacuenta.ui.components

import android.graphics.Bitmap
import android.graphics.Picture
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CaptureComposable(
    padding: Dp,
    content: @Composable () -> Unit
) {

    val picture = remember { Picture() }

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .drawWithCache {

                val width = this.size.width.toInt()
                val height = this.size.height.toInt()

                onDrawWithContent {

                    val canvas = androidx.compose.ui.graphics.Canvas(
                        picture.beginRecording(
                            width,
                            height
                        )
                    )

                    draw(this, this.layoutDirection, canvas, this.size) {
                        this@onDrawWithContent.drawContent()
                    }

                    picture.endRecording()

                    drawIntoCanvas { cv ->
                        cv.nativeCanvas.drawPicture(picture)
                    }

                }

            }
    ) {
        content()
    }
}

private fun createBitmapFromPicture(picture: Picture): Bitmap {
    val bitmap = Bitmap.createBitmap(
        picture.width,
        picture.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.WHITE)
    canvas.drawPicture(picture)
    return bitmap
}