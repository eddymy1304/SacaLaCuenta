package com.eddymy1304.sacalacuenta.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eddymy1304.sacalacuenta.data.models.Screen
import com.eddymy1304.sacalacuenta.data.models.getListItemsBottomNav
import com.eddymy1304.sacalacuenta.ui.theme.SacaLaCuentaTheme

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    items: List<Screen>
) {
    NavigationBar(modifier = modifier) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen::class.qualifiedName } == true,
                onClick = {
                    navController.navigate(screen) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // re selecting the same item
                        launchSingleTop = true
                        // Restore state when re selecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(id = screen.title))
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppBottomBar() {
    SacaLaCuentaTheme {
        AppBottomBar(items = getListItemsBottomNav(), navController = rememberNavController())
    }
}