package com.eddymy1304.sacalacuenta.ui.screens

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
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
import com.eddymy1304.sacalacuenta.MainViewModel
import com.eddymy1304.sacalacuenta.R
import com.eddymy1304.sacalacuenta.data.models.DetalleCuentaView
import com.eddymy1304.sacalacuenta.data.models.Screen.*
import com.eddymy1304.sacalacuenta.ui.components.CabListDetTicket
import com.eddymy1304.sacalacuenta.ui.components.ItemTicket
import com.eddymy1304.sacalacuenta.ui.theme.SacaLaCuentaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TicketScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel
) {

    LaunchedEffect(Unit) { viewModel.configScreen(ScreenTicket.title) }

    val cuentaWithDetalle by viewModel.cuentaWithDetalle.collectAsState()

    Log.d("TicketScreen", "cuentaWithCuenta: $cuentaWithDetalle")

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()

    var background by remember { mutableStateOf(Color.Transparent) }
    var textColor by remember { mutableStateOf(Color.Unspecified) }

    TicketScreen(
        background = background,
        textColor = textColor,
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
        onClickIconHome = {
            navController.popBackStack()
            navController.navigate(ScreenCuenta)
        },
        onClickIconShare = {
            background = Color.White
            textColor = Color.Black
            scope.launch {
                delay(1000)
                background = Color.Transparent
                textColor = Color.Unspecified
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
    background: Color,
    textColor: Color = Color.Unspecified,
    textTitle: String = "",
    textPaymentMethod: String = "",
    textDate: String = "",
    textTotal: Double = 0.0,
    listDet: List<DetalleCuentaView> = listOf(),
    onClickIconShare: () -> Unit = {},
    onClickIconHome: () -> Unit = {}
) {

    ConstraintLayout(modifier = modifier.background(background)) {
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
            color = textColor,
            text = stringResource(id = R.string.text_nombre, textTitle),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(image.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            })

        Text(
            color = textColor,
            text = stringResource(id = R.string.text_metodo_pago, textPaymentMethod),
            modifier = Modifier.constrainAs(method) {
                top.linkTo(title.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            color = textColor,
            text = stringResource(id = R.string.text_fecha, textDate),
            modifier = Modifier.constrainAs(date) {
                top.linkTo(method.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        CabListDetTicket(
            textColor = textColor,
            modifier = Modifier.constrainAs(cab) {
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
                ItemTicket(
                    textColor = textColor,
                    det = it
                )
            }
        }

        HorizontalDivider(Modifier.constrainAs(div2) {
            top.linkTo(list.bottom)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            width = Dimension.fillToConstraints
        })

        Text(
            color = textColor,
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
            background = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

private fun Bitmap.saveToDisk(context: Context): Uri {

    val filename = "screenshot-${System.currentTimeMillis()}.png"

    val uri = createImageUri(context, filename)
        ?: throw Exception("Failed to create new MediaStore record.")

    saveBitmapToUri(context, this, uri)

    Log.d("Eddycito", "saveToDisk: $uri")

    return uri
}

// Función para crear un Uri en MediaStore
private fun createImageUri(context: Context, filename: String): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }
    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )
}

// Función para guardar el bitmap en el archivo usando el Uri
private fun saveBitmapToUri(context: Context, bitmap: Bitmap, uri: Uri) {
    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
        if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
            throw Exception("Failed to save bitmap.")
        }
    } ?: throw Exception("Failed to open output stream.")
}

private fun shareBitmap(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(context, createChooser(intent, "Share your image"), null)
}