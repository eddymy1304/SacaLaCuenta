package com.eddymy1304.sacalacuenta.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.eddymy1304.sacalacuenta.MainViewModel
import com.eddymy1304.sacalacuenta.R
import com.eddymy1304.sacalacuenta.data.models.Screen.*
import com.eddymy1304.sacalacuenta.ui.components.DialogTextfield
import com.eddymy1304.sacalacuenta.ui.theme.SacaLaCuentaTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel
) {

    val userName by viewModel.userName.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf(userName) }

    LaunchedEffect(Unit) {
        viewModel.configScreen(
            title = ScreenSettings.title,
            showActions = false,
            showBottomNav = false
        )
    }

    SettingsScreen(
        modifier = modifier,
        userName = userName,
        onClickNombreUsuario = { showDialog = true }
    )

    AnimatedVisibility(visible = showDialog) {
        DialogTextfield(
            title = stringResource(R.string.nombre_del_usuario),
            text = name,
            onDismissRequest = {
                showDialog = false
                name = ""
            },
            onValueChange = {
                name = it
            },
            onClickAccept = {
                viewModel.updateUserName(name.trim())
                showDialog = false
            }
        )
    }
}

@Composable
fun SettingsScreen(
    modifier: Modifier,
    userName: String = "",
    onClickNombreUsuario: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Column(modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClickNombreUsuario() }
        ) {
            Text(
                text = stringResource(R.string.nombre_del_usuario),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = userName,
                fontSize = 14.sp
            )
        }

        HorizontalDivider()
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_4", locale = "es")
@Composable
fun ConfigScreenPreview() {
    SacaLaCuentaTheme {
        SettingsScreen(modifier = Modifier.fillMaxSize()) {

        }
    }
}