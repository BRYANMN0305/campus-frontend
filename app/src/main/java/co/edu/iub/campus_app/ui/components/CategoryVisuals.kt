package co.edu.iub.campus_app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.ui.graphics.vector.ImageVector
import co.edu.iub.campus_app.domain.model.enums.ServiceCategory

fun ServiceCategory.icon(): ImageVector {
    return when (this) {
        ServiceCategory.BIBLIOTECA -> Icons.AutoMirrored.Outlined.MenuBook
        ServiceCategory.LABORATORIO -> Icons.Outlined.Science
        ServiceCategory.SALA_COMPUTO -> Icons.Outlined.Computer
        ServiceCategory.CAFETERIA -> Icons.Outlined.LocalCafe
        ServiceCategory.TUTORIA -> Icons.Outlined.School
        ServiceCategory.OTRO -> Icons.Outlined.Storefront
    }
}
