package com.eddymy1304.sacalacuenta.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun RowScope.ItemBottomBar(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {

    NavigationBarItem(
        selected = isSelected,
        onClick = onItemClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        label = {
            Text(text = title)
        }
    )
}
