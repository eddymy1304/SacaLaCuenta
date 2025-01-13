package com.eddymy1304.sacalacuenta.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eddymy1304.sacalacuenta.core.designsystem.icon.AppIcons
import com.eddymy1304.sacalacuenta.core.designsystem.theme.SacaLaCuentaTheme

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    content: @Composable RowScope.(currentDestination: NavDestination?) -> Unit
) {
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        content(currentDestination)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppBottomBar() {
    val controller = rememberNavController()
    SacaLaCuentaTheme {
        AppBottomBar(
            navController = controller
        ) { _ ->
            ItemBottomBar(
                title = "Item 1",
                icon = AppIcons.Home,
                isSelected = false,
                onItemClick = {

                }
            )

            ItemBottomBar(
                title = "Item 2",
                icon = AppIcons.History,
                isSelected = false,
                onItemClick = {

                }
            )
        }
    }
}