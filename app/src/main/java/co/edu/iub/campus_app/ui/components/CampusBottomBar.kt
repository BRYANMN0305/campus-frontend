package co.edu.iub.campus_app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.edu.iub.campus_app.ui.theme.CampusBorder
import co.edu.iub.campus_app.ui.theme.CampusInk
import co.edu.iub.campus_app.ui.theme.CampusInkMuted
import co.edu.iub.campus_app.ui.theme.CampusSurface

enum class TopLevel {
    HOME,
    CATALOG,
    REPORTS
}

@Composable
fun CampusBottomBar(
    current: TopLevel,
    onHome: () -> Unit,
    onCatalog: () -> Unit,
    onReports: () -> Unit
) {
    Column {
        HorizontalDivider(color = CampusBorder)
        NavigationBar(
            containerColor = CampusSurface,
            tonalElevation = 0.dp
        ) {
            val colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CampusInk,
                selectedTextColor = CampusInk,
                indicatorColor = Color.Transparent,
                unselectedIconColor = CampusInkMuted,
                unselectedTextColor = CampusInkMuted
            )

            NavigationBarItem(
                selected = current == TopLevel.HOME,
                onClick = onHome,
                icon = {
                    Icon(
                        imageVector = if (current == TopLevel.HOME) {
                            Icons.Filled.Home
                        } else {
                            Icons.Outlined.Home
                        },
                        contentDescription = "Inicio"
                    )
                },
                label = { Text("Inicio") },
                colors = colors
            )

            NavigationBarItem(
                selected = current == TopLevel.CATALOG,
                onClick = onCatalog,
                icon = {
                    Icon(
                        imageVector = if (current == TopLevel.CATALOG) {
                            Icons.Filled.GridView
                        } else {
                            Icons.Outlined.GridView
                        },
                        contentDescription = "Catálogo"
                    )
                },
                label = { Text("Catálogo") },
                colors = colors
            )

            NavigationBarItem(
                selected = current == TopLevel.REPORTS,
                onClick = onReports,
                icon = {
                    Icon(
                        imageVector = if (current == TopLevel.REPORTS) {
                            Icons.AutoMirrored.Filled.ReceiptLong
                        } else {
                            Icons.AutoMirrored.Outlined.ReceiptLong
                        },
                        contentDescription = "Mis reportes"
                    )
                },
                label = { Text("Mis reportes") },
                colors = colors
            )
        }
    }
}
