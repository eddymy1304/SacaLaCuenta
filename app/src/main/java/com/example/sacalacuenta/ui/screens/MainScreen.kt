package com.example.sacalacuenta.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.sacalacuenta.MainViewModel
import com.example.sacalacuenta.R
import com.example.sacalacuenta.data.models.Screen.*
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

    LaunchedEffect(Unit) {
        viewModel.getUser()
    }

    val userName by viewModel.userName.collectAsState()

    val title by viewModel.title.collectAsState()

    val showActions by viewModel.showActions.collectAsState()

    val showBottomNav by viewModel.showBottomNav.collectAsState()

    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopBar(
                title = stringResource(id = title),
                subTitle = if (userName.isNotBlank()) stringResource(
                    id = R.string.sub_title,
                    userName
                )
                else stringResource(id = R.string.sub_title_with_out),
                showActions = showActions,
                showIconNav = navController.currentDestination?.route != ScreenCuenta::class.qualifiedName,
                onClickAction = { navController.navigate(ScreenSettings) }
            ) {
                if (navController.currentDestination?.route == ScreenCuenta::class.qualifiedName) finished()
                else navController.navigateUp()
            }
        },
        bottomBar = {
            AnimatedVisibility(visible = showBottomNav) {
                MyBottomBar(navController = navController, items = getListItemsBottomNav())
            }
        }
    ) {
        MyNavHost(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            viewModel = viewModel,
            navController = navController,
            startDestination = ScreenCuenta
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