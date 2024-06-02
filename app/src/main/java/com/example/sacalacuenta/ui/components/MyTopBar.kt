package com.example.sacalacuenta.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    subTitle: String = "",
    showIconNav: Boolean = false,
    onClickAction: () -> Unit,
    onClickNav: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Column {
                Text(text = title, fontSize = 24.sp)
                Text(text = subTitle, fontSize = 14.sp)
            }
        },
        navigationIcon = {
            if (showIconNav) {
                IconButton(onClick = onClickNav) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewMyTopBar() {
    SacaLaCuentaTheme {
        MyTopBar(
            title = "SacaLaCuenta",
            subTitle = "Bienvenido, Eddy.",
            onClickAction = {}
        ) {

        }
    }
}