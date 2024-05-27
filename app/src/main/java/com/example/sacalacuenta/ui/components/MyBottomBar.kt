//package com.example.sacalacuenta.ui.components
//
//import androidx.compose.material3.Icon
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.navigation.NavDestination.Companion.hierarchy
//import androidx.navigation.NavGraph.Companion.findStartDestination
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.example.sacalacuenta.data.models.Screen
//import com.example.sacalacuenta.data.models.getListItemsBottomNav
//import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme
//
//@Composable
//fun MyBottomBar(
//    modifier: Modifier = Modifier,
//    navController: NavHostController,
//    items: List<Screen>
//) {
//    NavigationBar(modifier = modifier) {
//
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentDestination = navBackStackEntry?.destination
//
//        items.forEach { screen ->
//            NavigationBarItem(
//                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
//                onClick = {
//                    navController.navigate(screen.route) {
//                        // Pop up to the start destination of the graph to
//                        // avoid building up a large stack of destinations
//                        // on the back stack as users select items
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        // Avoid multiple copies of the same destination when
//                        // reselecting the same item
//                        launchSingleTop = true
//                        // Restore state when reselecting a previously selected item
//                        restoreState = true
//                    }
//                },
//                icon = {
//                    if (screen.icon != null) Icon(
//                        imageVector = screen.icon,
//                        contentDescription = null
//                    )
//                },
//                label = {
//                    Text(text = stringResource(id = screen.title))
//                }
//            )
//
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewMyBottomBar() {
//    SacaLaCuentaTheme {
//        MyBottomBar(items = getListItemsBottomNav(), navController = rememberNavController())
//    }
//}