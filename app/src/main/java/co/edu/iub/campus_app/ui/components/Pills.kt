package co.edu.iub.campus_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.iub.campus_app.domain.model.enums.Availability
import co.edu.iub.campus_app.domain.model.enums.ReportStatus
import co.edu.iub.campus_app.ui.theme.CampusAmber
import co.edu.iub.campus_app.ui.theme.CampusBlue
import co.edu.iub.campus_app.ui.theme.CampusInkMuted
import co.edu.iub.campus_app.ui.theme.CampusRed
import co.edu.iub.campus_app.ui.theme.CampusSuccess

@Composable
fun AvailabilityPill(availability: Availability, modifier: Modifier = Modifier) {
    val dotColor = when (availability) {
        Availability.DISPONIBLE -> CampusSuccess
        Availability.NO_DISPONIBLE -> CampusInkMuted
        Availability.MANTENIMIENTO -> CampusAmber
    }

    StatusLine(
        text = availability.label,
        dotColor = dotColor,
        modifier = modifier
    )
}

@Composable
fun StatusPill(status: ReportStatus, modifier: Modifier = Modifier) {
    val dotColor = when (status) {
        ReportStatus.PENDIENTE -> CampusAmber
        ReportStatus.EN_REVISION -> CampusBlue
        ReportStatus.RESUELTO -> CampusSuccess
        ReportStatus.RECHAZADO -> CampusRed
    }

    StatusLine(
        text = status.label,
        dotColor = dotColor,
        modifier = modifier
    )
}

@Composable
private fun StatusLine(
    text: String,
    dotColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(color = dotColor, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.8.sp),
            color = CampusInkMuted
        )
    }
}
