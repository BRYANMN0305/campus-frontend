package co.edu.iub.campus_app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.edu.iub.campus_app.domain.model.enums.ServiceCategory
import co.edu.iub.campus_app.ui.theme.CampusInk
import co.edu.iub.campus_app.ui.theme.CampusInkMuted

@Composable
fun CategoryCard(
    category: ServiceCategory,
    count: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(104.dp)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = category.icon(),
            contentDescription = category.label,
            tint = CampusInkMuted,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = category.label,
            style = MaterialTheme.typography.labelMedium,
            color = CampusInk,
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "$count servicios",
            style = MaterialTheme.typography.labelSmall,
            color = CampusInkMuted
        )
    }
}
