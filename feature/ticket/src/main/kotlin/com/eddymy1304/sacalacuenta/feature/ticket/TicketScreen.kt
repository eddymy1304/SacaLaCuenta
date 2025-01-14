package com.eddymy1304.sacalacuenta.feature.ticket

import android.Manifest
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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.eddymy1304.sacalacuenta.core.common.UiText
import com.eddymy1304.sacalacuenta.core.designsystem.icon.AppIcons
import com.eddymy1304.sacalacuenta.core.designsystem.theme.SacaLaCuentaTheme
import com.eddymy1304.sacalacuenta.core.model.DetailReceipt
import com.eddymy1304.sacalacuenta.core.ui.CabListDetTicket
import com.eddymy1304.sacalacuenta.core.ui.ItemTicket
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import com.eddymy1304.sacalacuenta.core.ui.R as uiR

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TicketScreen(
    modifier: Modifier = Modifier,
    id: Int,
    viewModel: TicketViewModel = hiltViewModel(),
    configScreen: () -> Unit,
    navToReceiptScreen: () -> Unit
) {

    LaunchedEffect(Unit) {
        configScreen()
        Log.d("TicketScreen", "id: $id")
        viewModel.getReceiptWithDetById(id)
    }

    val receiptWithDet by viewModel.receiptWithDet.collectAsState()

    Log.d("TicketScreen", "receiptWithDet: $receiptWithDet")

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()

    var background by remember { mutableStateOf(Color.Transparent) }
    var textColor by remember { mutableStateOf(Color.Unspecified) }

    val showDialog by viewModel.showDialogPermissionRationale.collectAsState()

    val listPermissions = listOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val permissionsState = rememberMultiplePermissionsState(listPermissions)

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { granters ->
        if (granters.all { it.value }) {
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
        } else {
            viewModel.setMessage(UiText.StringResource(R.string.permission_denied))
        }
    }

    if (showDialog) {
        AlertDialog(
            icon = {
                Icon(imageVector = Icons.Outlined.Warning, contentDescription = null)
            },
            text = {
                Text(stringResource(R.string.message_dialog_permission_rationale))
            },
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.setShowDialogPermissionRationale(false)
                        requestPermissionLauncher.launch(listPermissions.toTypedArray())
                    }
                ) {
                    Text(text = stringResource(com.eddymy1304.sacalacuenta.core.designsystem.R.string.accept))
                }
            }
        )
    }

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
        textTitle = receiptWithDet.receipt.title,
        textPaymentMethod = receiptWithDet.receipt.paymentMethod,
        textDate = receiptWithDet.receipt.dateTime,
        textTotal = receiptWithDet.receipt.total,
        listDet = receiptWithDet.listDetailReceipt,
        onClickIconHome = navToReceiptScreen,
        onClickIconShare = {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {

                if (!permissionsState.allPermissionsGranted && permissionsState.shouldShowRationale)
                    viewModel.setShowDialogPermissionRationale(true)
                else requestPermissionLauncher.launch(listPermissions.toTypedArray())

            } else {

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
    listDet: List<DetailReceipt> = listOf(),
    onClickIconShare: () -> Unit = {},
    onClickIconHome: () -> Unit = {}
) {

    ConstraintLayout(modifier = modifier.background(background)) {
        val (image, title, method, cab, list, total, date, div1, div2, iconShare, iconHome) = createRefs()

        Icon(
            imageVector = AppIcons.Cart,
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
            text = stringResource(
                id = uiR.string.text_name,
                textTitle
            ),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(image.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            })

        Text(
            color = textColor,
            text = stringResource(
                id = uiR.string.text_payment_method,
                textPaymentMethod
            ),
            modifier = Modifier.constrainAs(method) {
                top.linkTo(title.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            color = textColor,
            text = stringResource(
                id = uiR.string.text_date,
                textDate
            ),
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
            text = stringResource(
                id = uiR.string.text_total,
                textTotal
            ),
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

@Preview(showBackground = true, locale = "es")
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
        //put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Usamos RELATIVE_PATH solo en dispositivos con API 29 o superior
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        } else {
            // Para dispositivos con API menor a 29
            val picturesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path
            val file = File(picturesDir, filename)
            put(MediaStore.Images.Media.DATA, file.absolutePath)
        }
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
    context.startActivity(createChooser(intent, "Share your image"))
    //startActivity(context, createChooser(intent, "Share your image"), null)
}