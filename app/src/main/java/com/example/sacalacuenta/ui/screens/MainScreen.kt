package com.example.sacalacuenta.ui.screens

import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.graphics.Bitmap
import android.graphics.Picture
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.sacalacuenta.MainViewModel
import com.example.sacalacuenta.R
import com.example.sacalacuenta.data.models.Screen.CuentaScreen
import com.example.sacalacuenta.data.models.getListItemsBottomNav
import com.example.sacalacuenta.ui.components.MyBottomBar
import com.example.sacalacuenta.ui.components.MyNavHost
import com.example.sacalacuenta.ui.components.MyTopBar
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    finished: () -> Unit
) {

    val context = LocalContext.current
    val picture = remember { Picture() }
    val snackbarHostState = remember { SnackbarHostState() }

    val nameUser by viewModel.nameUser.collectAsState()
    val onClickIconShare by viewModel.onClickIconShare.collectAsState()

    val navController = rememberNavController()

    val coroutineScope = rememberCoroutineScope()

    val writeStorageAccessState = rememberMultiplePermissionsState(emptyList())

    // This logic should live in your ViewModel - trigger a side effect to invoke URI sharing.
    // checks permissions granted, and then saves the bitmap from a Picture that is already capturing content
    // and shares it with the default share sheet.
    fun shareBitmapFromComposable() {
        if (writeStorageAccessState.allPermissionsGranted) {
            coroutineScope.launch(Dispatchers.IO) {
                val bitmap = createBitmapFromPicture(picture)
                val uri = bitmap.saveToDisk(context)
                shareBitmap(context, uri)
            }
        } else if (writeStorageAccessState.shouldShowRationale) {
            coroutineScope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = "The storage permission is needed to save the image",
                    actionLabel = "Grant Access"
                )

                if (result == SnackbarResult.ActionPerformed) {
                    writeStorageAccessState.launchMultiplePermissionRequest()
                }
            }
        } else {
            writeStorageAccessState.launchMultiplePermissionRequest()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier,
        topBar = {
            MyTopBar(
                title = stringResource(id = R.string.app_name),
                subTitle = if (nameUser.isNotBlank()) stringResource(
                    id = R.string.sub_title,
                    nameUser
                )
                else stringResource(id = R.string.sub_title_with_out),
                showIconNav = navController.currentDestination?.route != CuentaScreen.route
            ) {
                if (navController.currentDestination?.route == CuentaScreen.route) {
                    finished()
                } else {
                    navController.navigateUp()
                }
            }
        },
        bottomBar = {
            MyBottomBar(
                navController = navController,
                items = getListItemsBottomNav()
            )
        }
    ) {
        MyNavHost(
            picture = picture,
            paddingValues = it,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            viewModel = viewModel,
            navController = navController,
            startDestination = CuentaScreen.route
        )
    }

    DisposableEffect(onClickIconShare) {
        if (onClickIconShare) {
            shareBitmapFromComposable()
            viewModel.updateOnClickIconShare(false)
        }
        onDispose { }
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

private suspend fun Bitmap.saveToDisk(context: Context): Uri {
    val file = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "screenshot-${System.currentTimeMillis()}.png"
    )

    file.writeBitmap(this, Bitmap.CompressFormat.PNG, 100)

    return scanFilePath(context, file.path) ?: throw Exception("File could not be saved")
}

private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}

private suspend fun scanFilePath(context: Context, filePath: String): Uri? {
    return suspendCancellableCoroutine { continuation ->
        MediaScannerConnection.scanFile(
            context,
            arrayOf(filePath),
            arrayOf("image/png")
        ) { _, scannedUri ->
            if (scannedUri == null) {
                continuation.cancel(Exception("File $filePath could not be scanned"))
            } else {
                continuation.resume(scannedUri)
            }
        }
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainScreen() {
    SacaLaCuentaTheme {
        MainScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = hiltViewModel(),
            finished = {}
        )
    }
}