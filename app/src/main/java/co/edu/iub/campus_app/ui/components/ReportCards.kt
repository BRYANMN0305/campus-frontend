package co.edu.iub.campus_app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.edu.iub.campus_app.domain.model.RecentReport
import co.edu.iub.campus_app.domain.model.Report
import co.edu.iub.campus_app.ui.theme.CampusBorder
import co.edu.iub.campus_app.ui.theme.CampusInk
import co.edu.iub.campus_app.ui.theme.CampusInkMuted
import co.edu.iub.campus_app.ui.util.formatDateTime
import co.edu.iub.campus_app.ui.util.relativeLabel

@Composable
fun RecentReportRow(
    report: RecentReport,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = report.serviceName,
                    style = MaterialTheme.typography.titleMedium,
                    color = CampusInk,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                StatusPill(status = report.status)
            }
            Text(
                text = report.description,
                style = MaterialTheme.typography.bodySmall,
                color = CampusInkMuted,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${report.category.label} · ${report.createdAt.relativeLabel()}",
                style = MaterialTheme.typography.labelSmall,
                color = CampusInkMuted
            )
        }
        HorizontalDivider(color = CampusBorder)
    }
}

@Composable
fun ReportCard(
    report: Report,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = report.serviceName,
                    style = MaterialTheme.typography.titleMedium,
                    color = CampusInk,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                StatusPill(status = report.status)
            }
            Text(
                text = report.category.label.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = CampusInkMuted
            )
            Text(
                text = report.description,
                style = MaterialTheme.typography.bodySmall,
                color = CampusInk,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = report.createdAt.formatDateTime(),
                style = MaterialTheme.typography.labelSmall,
                color = CampusInkMuted
            )
        }
        HorizontalDivider(color = CampusBorder)
    }
}
