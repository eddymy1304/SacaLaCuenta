package com.example.sacalacuenta.ui.screens

import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalGroceryStore
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.sacalacuenta.MainViewModel
import com.example.sacalacuenta.R
import com.example.sacalacuenta.data.models.DetalleCuentaView
import com.example.sacalacuenta.ui.components.CabListDetTicket
import com.example.sacalacuenta.ui.components.ItemTicket
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

@Composable
fun TicketScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val cuentaWithDetalle by viewModel.cuentaWithDetalle.collectAsState()

    Log.d("TicketScreen", "cuentaWithCuenta: $cuentaWithDetalle")

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()

    TicketScreen(
        modifier = modifier
            .drawWithContent {
                graphicsLayer.record {
                    this@drawWithContent.drawContent()
                }
                drawLayer(graphicsLayer)
            },
        textTitle = cuentaWithDetalle.cuenta.title.value.orEmpty(),
        textPaymentMethod = cuentaWithDetalle.cuenta.paymentMethod.value.orEmpty(),
        textDate = cuentaWithDetalle.cuenta.dateTime.orEmpty(),
        textTotal = cuentaWithDetalle.cuenta.total.value ?: 0.0,
        listDet = cuentaWithDetalle.listDetCuenta,
        onClickIconHome = { navController.navigateUp() },
        onClickIconShare = {
            scope.launch {
                val bitmap = graphicsLayer.toImageBitmap()
                val uri = bitmap.asAndroidBitmap().saveToDisk(context)
                shareBitmap(context, uri)
            }
        }
    )
}

@Composable
fun TicketScreen(
    modifier: Modifier = Modifier,
    textTitle: String = "",
    textPaymentMethod: String = "",
    textDate: String = "",
    textTotal: Double = 0.0,
    listDet: List<DetalleCuentaView> = listOf(),
    onClickIconShare: () -> Unit = {},
    onClickIconHome: () -> Unit = {}
) {

    ConstraintLayout(modifier = modifier) {
        val (image, title, method, cab, list, total, date, div1, div2, iconShare, iconHome) = createRefs()

        Icon(
            imageVector = Icons.Outlined.LocalGroceryStore,
            contentDescription = null,
            modifier = Modifier.constrainAs(image) {
                top.linkTo(parent.top, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            },
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(id = R.string.text_nombre, textTitle),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(image.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            })

        Text(
            text = stringResource(id = R.string.text_metodo_pago, textPaymentMethod),
            modifier = Modifier.constrainAs(method) {
                top.linkTo(title.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = stringResource(id = R.string.text_fecha, textDate),
            modifier = Modifier.constrainAs(date) {
                top.linkTo(method.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        CabListDetTicket(modifier = Modifier.constrainAs(cab) {
            top.linkTo(date.bottom, margin = 8.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            width = Dimension.matchParent
        })

        HorizontalDivider(Modifier.constrainAs(div1) {
            top.linkTo(cab.bottom)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            width = Dimension.fillToConstraints
        })

        LazyColumn(modifier = Modifier.constrainAs(list) {
            top.linkTo(div1.bottom)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            bottom.linkTo(div2.top)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }) {
            items(listDet) {
                ItemTicket(det = it)
            }
        }

        HorizontalDivider(Modifier.constrainAs(div2) {
            top.linkTo(list.bottom)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            width = Dimension.fillToConstraints
        })

        Text(
            text = stringResource(id = R.string.text_total_cuenta, textTotal),
            modifier = Modifier.constrainAs(total) {
                top.linkTo(div2.bottom, margin = 8.dp)
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(iconShare.top, margin = 8.dp)
            }
        )

        IconButton(
            modifier = Modifier.constrainAs(iconShare) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(iconHome.start, margin = 8.dp)
            },
            onClick = onClickIconShare
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(
            modifier = Modifier.constrainAs(iconHome) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                end.linkTo(parent.end, margin = 8.dp)
                start.linkTo(iconShare.end, margin = 8.dp)
            },
            onClick = onClickIconHome
        ) {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, locale = "es")
@Composable
fun PreviewTicketScreen() {
    SacaLaCuentaTheme {
        TicketScreen(
            modifier = Modifier.fillMaxSize(),
        )
    }
}

private suspend fun Bitmap.saveToDisk(context: Context): Uri {
    val file = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "screenshot-${System.currentTimeMillis()}.png"
    )

    file.writeBitmap(this, Bitmap.CompressFormat.PNG, 100)

    return scanFilePath(context, file.path) ?: throw Exception("File could not be saved")
}

/**
 * We call [MediaScannerConnection] to index the newly created image inside MediaStore to be visible
 * for other apps, as well as returning its MediaStore Uri
 */
private suspend fun scanFilePath(context: Context, filePath: String): Uri? {
    return suspendCancellableCoroutine { continuation ->
        MediaScannerConnection.scanFile(
            context,
            arrayOf(filePath),
            arrayOf("image/png")
        ) { _, scannedUri ->
            if (scannedUri == null) continuation.cancel(Exception("File $filePath could not be scanned"))
            else continuation.resume(scannedUri)
        }
    }
}

private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}

private fun shareBitmap(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(context, createChooser(intent, "Share your image"), null)
}