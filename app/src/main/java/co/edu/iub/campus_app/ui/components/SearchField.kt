package co.edu.iub.campus_app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.edu.iub.campus_app.ui.theme.CampusBorder
import co.edu.iub.campus_app.ui.theme.CampusInk
import co.edu.iub.campus_app.ui.theme.CampusInkMuted
import co.edu.iub.campus_app.ui.theme.CampusSurface

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Buscar por nombre o ubicación"
) {
    val trailingIcon: @Composable (() -> Unit)? = if (value.isNotEmpty()) {
        {
            IconButton(onClick = { onValueChange("") }) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Limpiar búsqueda",
                    tint = CampusInkMuted
                )
            }
        }
    } else {
        null
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = CampusInkMuted
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = CampusInkMuted
            )
        },
        trailingIcon = trailingIcon,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CampusSurface,
            unfocusedContainerColor = CampusSurface,
            focusedBorderColor = CampusInk,
            unfocusedBorderColor = CampusBorder,
            cursorColor = CampusInk,
            focusedTextColor = CampusInk,
            unfocusedTextColor = CampusInk
        )
    )
}
