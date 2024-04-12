package com.example.sacalacuenta.ui.components

import android.graphics.Picture
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun CaptureComposable(
    picture: Picture,
    padding: PaddingValues,
    content: @Composable () -> Unit
) {

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