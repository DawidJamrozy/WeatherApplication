package com.synexo.weatherapp.presentation


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.safeNavigate
import com.synexo.weatherapp.data.model.BottomNavItem


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.MyLocations,
        BottomNavItem.Weather,
        BottomNavItem.Settings
    )

    NavigationBar(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        containerColor = LocalCustomColorsPalette.current.navigationBackground
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        items.forEach { item ->
            NavigationBarItem(
                selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == item.route } == true,
                colors = NavigationBarItemDefaults
                    .colors(
                        selectedIconColor = LocalCustomColorsPalette.current.bottomBarSelectedNavigationItem,
                        selectedTextColor = LocalCustomColorsPalette.current.bottomBarSelectedNavigationItem,
                        unselectedIconColor = LocalCustomColorsPalette.current.bottomBarUnselectedNavigationItem,
                        unselectedTextColor = LocalCustomColorsPalette.current.bottomBarUnselectedNavigationItem,
                        indicatorColor = LocalCustomColorsPalette.current.bottomBarSelectedNavigationItem.copy(
                            alpha = 0.1f
                        ),
                    ),
                onClick = {
                    navController.safeNavigate(item.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.label),
                        fontSize = 12.sp
                    )
                }
            )
        }
    }
}
