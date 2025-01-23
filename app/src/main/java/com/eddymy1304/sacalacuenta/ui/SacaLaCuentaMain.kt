package com.eddymy1304.sacalacuenta.ui

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import com.eddymy1304.sacalacuenta.MainViewModel
import com.eddymy1304.sacalacuenta.R
import com.eddymy1304.sacalacuenta.core.designsystem.component.AppBottomBar
import com.eddymy1304.sacalacuenta.core.designsystem.component.AppTopBar
import com.eddymy1304.sacalacuenta.core.designsystem.component.ItemBottomBar
import com.eddymy1304.sacalacuenta.core.designsystem.theme.SacaLaCuentaTheme
import com.eddymy1304.sacalacuenta.feature.history.navigation.navigateToHistoryScreen
import com.eddymy1304.sacalacuenta.feature.receipt.navigation.navigateToReceiptScreen
import com.eddymy1304.sacalacuenta.feature.settings.navigation.navigateToSettingsScreen
import com.eddymy1304.sacalacuenta.navigation.AppNavHost
import com.eddymy1304.sacalacuenta.navigation.TopLevelDestination.HISTORY
import com.eddymy1304.sacalacuenta.navigation.TopLevelDestination.RECEIPT

@Composable
fun SacaLaCuentaMain(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val username by viewModel.userName.collectAsStateWithLifecycle()

    val title by viewModel.title.collectAsStateWithLifecycle()

    val showActions by viewModel.showActions.collectAsStateWithLifecycle()

    val showBottomNav by viewModel.showBottomNav.collectAsStateWithLifecycle()

    val navController = rememberNavController()


    SacaLaCuentaTheme {

        Scaffold(
            modifier = modifier,
            topBar = {
                val hideIconNav =
                    navController.currentDestination?.hasRoute(route = RECEIPT.route) == true ||
                            navController.currentDestination?.hasRoute(route = HISTORY.route) == true
                AppTopBar(
                    title = stringResource(id = title),
                    subTitle = if (username.isNotBlank())
                        stringResource(id = R.string.sub_title, username)
                    else stringResource(id = R.string.sub_title_with_out),
                    showActions = showActions,
                    showIconNav = !hideIconNav,
                    onClickAction = navController::navigateToSettingsScreen,
                    onClickNav = {
                        val validate = navController.currentDestination?.hasRoute(RECEIPT.route)
                        if (validate == true) (context as? Activity)?.finish()
                        else navController.navigateUp()
                    }
                )
            },
            bottomBar = {

                val items = listOf(RECEIPT, HISTORY)

                AnimatedVisibility(visible = showBottomNav) {
                    AppBottomBar(
                        navController = navController
                    ) { currentDestination ->
                        items.forEach { item ->
                            ItemBottomBar(
                                title = stringResource(item.title),
                                icon = item.icon,
                                isSelected = currentDestination?.hierarchy?.any {
                                    it.hasRoute(item.route)
                                } == true,
                                onItemClick = {
                                    val navOptions: NavOptionsBuilder.() -> Unit = {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    when (item) {
                                        RECEIPT -> navController.navigateToReceiptScreen(navOptions = navOptions)
                                        HISTORY -> navController.navigateToHistoryScreen(navOptions = navOptions)
                                        else -> throw Exception("Error Navigation")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        ) { padding ->
            AppNavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}