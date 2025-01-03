package com.eddymy1304.sacalacuenta.ui.screens

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.eddymy1304.sacalacuenta.MainViewModel
import com.eddymy1304.sacalacuenta.R
import com.eddymy1304.sacalacuenta.data.models.Screen
import com.eddymy1304.sacalacuenta.data.models.Screen.ScreenReceipt
import com.eddymy1304.sacalacuenta.data.models.Screen.ScreenSettings
import com.eddymy1304.sacalacuenta.data.models.getListItemsBottomNav
import com.eddymy1304.sacalacuenta.ui.components.AppBottomBar
import com.eddymy1304.sacalacuenta.ui.components.AppNavHost
import com.eddymy1304.sacalacuenta.ui.components.AppTopBar
import com.eddymy1304.sacalacuenta.ui.theme.SacaLaCuentaTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {

    val context = LocalContext.current

    val userName by viewModel.userName.collectAsState()

    val title by viewModel.title.collectAsState()

    val showActions by viewModel.showActions.collectAsState()

    val showBottomNav by viewModel.showBottomNav.collectAsState()

    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = stringResource(id = title),
                subTitle = if (userName.isNotBlank())
                    stringResource(id = R.string.sub_title, userName)
                else stringResource(id = R.string.sub_title_with_out),
                showActions = showActions,
                showIconNav = navController.currentDestination?.route !in listOf(
                    ScreenReceipt::class.qualifiedName,
                    Screen.ScreenHistory::class.qualifiedName
                ),
                onClickAction = { navController.navigate(ScreenSettings) }
            ) {
                if (navController.currentDestination?.route == ScreenReceipt::class.qualifiedName) (context as? Activity)?.finish()
                else navController.navigateUp()
            }
        },
        bottomBar = {
            AnimatedVisibility(visible = showBottomNav) {
                AppBottomBar(
                    navController = navController,
                    items = getListItemsBottomNav()
                )
            }
        }
    ) {
        AppNavHost(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            viewModel = viewModel,
            navController = navController,
            startDestination = ScreenReceipt
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainScreen() {
    SacaLaCuentaTheme {
        MainScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = hiltViewModel()
        )
    }
}