package com.eddymy1304.sacalacuenta.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
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
import com.eddymy1304.sacalacuenta.core.designsystem.icon.AppIcons
import com.eddymy1304.sacalacuenta.core.designsystem.theme.SacaLaCuentaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    subTitle: String = "",
    showIconNav: Boolean = false,
    showActions: Boolean = true,
    onClickAction: () -> Unit,
    onClickNav: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Column {
                Text(text = title, fontSize = 24.sp, maxLines = 1)
                Text(text = subTitle, fontSize = 14.sp, maxLines = 1)
            }
        },
        navigationIcon = {
            AnimatedVisibility(visible = showIconNav) {
                IconButton(onClick = onClickNav) {
                    Icon(
                        imageVector = AppIcons.Back,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            AnimatedVisibility(visible = showActions) {
                IconButton(onClick = onClickAction) {
                    Icon(
                        tint = MaterialTheme.colorScheme.primary,
                        imageVector = AppIcons.Settings,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewAppTopBar() {
    SacaLaCuentaTheme {
        AppTopBar(
            title = "App",
            subTitle = "Welcome, Eddy.",
            onClickAction = {}
        ) {

        }
    }
}