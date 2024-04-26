package com.example.sacalacuenta.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    finished: () -> Unit
) {

    val nameUser by viewModel.nameUser.collectAsState()

    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopBar(
                title = stringResource(id = R.string.app_name),
                subTitle = if (nameUser.isNotBlank()) stringResource(id = R.string.sub_title, nameUser)
                else stringResource(id = R.string.sub_title_with_out),
                showIconNav = navController.currentDestination?.route != CuentaScreen.route
            ) {
                if (navController.currentDestination?.route == CuentaScreen.route) finished()
                else navController.navigateUp()
            }
        },
        bottomBar = { MyBottomBar(navController = navController, items = getListItemsBottomNav()) }
    ) {
        MyNavHost(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            viewModel = viewModel,
            navController = navController,
            startDestination = CuentaScreen.route
        )
    }
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