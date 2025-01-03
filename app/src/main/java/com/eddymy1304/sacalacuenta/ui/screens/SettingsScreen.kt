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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.eddymy1304.sacalacuenta.R
import com.eddymy1304.sacalacuenta.ui.components.DialogTextField
import com.eddymy1304.sacalacuenta.ui.theme.SacaLaCuentaTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel(),
    configScreen: () -> Unit
) {

    LaunchedEffect(Unit) { configScreen() }

    val userName by viewModel.userName.collectAsState()

    val showDialog by viewModel.showDialog.collectAsState()

    var name by remember { mutableStateOf(userName) }

    SettingsScreen(
        modifier = modifier,
        userName = userName,
        onClickUserName = { viewModel.setShowDialog(true) }
    )

    AnimatedVisibility(visible = showDialog) {
        DialogTextField(
            title = stringResource(R.string.nombre_del_usuario),
            text = name,
            onDismissRequest = {
                viewModel.setShowDialog(false)
                name = ""
            },
            onValueChange = {
                name = it
            },
            onClickAccept = {
                viewModel.saveUserName(name.trim())
                viewModel.setShowDialog(false)
            }
        )
    }
}

@Composable
fun SettingsScreen(
    modifier: Modifier,
    userName: String = "",
    onClickUserName: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Column(modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClickUserName() }
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