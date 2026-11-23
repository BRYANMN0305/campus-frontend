package co.edu.iub.campus_app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.edu.iub.campus_app.domain.model.Report
import co.edu.iub.campus_app.ui.components.ErrorState
import co.edu.iub.campus_app.ui.components.FullScreenLoading
import co.edu.iub.campus_app.ui.components.StatusPill
import co.edu.iub.campus_app.ui.theme.CampusBorder
import co.edu.iub.campus_app.ui.theme.CampusCanvas
import co.edu.iub.campus_app.ui.theme.CampusInk
import co.edu.iub.campus_app.ui.theme.CampusInkMuted
import co.edu.iub.campus_app.ui.theme.CampusSuccess
import co.edu.iub.campus_app.ui.theme.CampusSurface
import co.edu.iub.campus_app.ui.util.formatDateTime
import co.edu.iub.campus_app.ui.viewModel.ConfirmationViewModel

@Composable
fun ConfirmationScreen(
    onViewReports: () -> Unit,
    onBackHome: () -> Unit,
    viewModel: ConfirmationViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(containerColor = CampusCanvas) { innerPadding ->
        when {
            state.loading -> {
                FullScreenLoading(modifier = Modifier.padding(innerPadding))
            }

            state.error != null -> {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ErrorState(
                        message = state.error ?: "",
                        onRetry = viewModel::load
                    )
                    TextButton(onClick = onViewReports) {
                        Text("Ver mis reportes")
                    }
                }
            }

            state.report != null -> {
                ConfirmationContent(
                    report = state.report!!,
                    onViewReports = onViewReports,
                    onBackHome = onBackHome,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun ConfirmationContent(
    report: Report,
    onViewReports: () -> Unit,
    onBackHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Surface(
            shape = CircleShape,
            color = CampusSurface,
            border = BorderStroke(1.dp, CampusBorder),
            modifier = Modifier.size(88.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = CampusSuccess,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Novedad registrada",
            style = MaterialTheme.typography.headlineMedium,
            color = CampusInk,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tu reporte quedó en revisión.",
            style = MaterialTheme.typography.bodyMedium,
            color = CampusInkMuted,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        ReceiptCard(report = report)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onViewReports,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Ver mis reportes")
        }

        Spacer(modifier = Modifier.height(4.dp))

        TextButton(onClick = onBackHome) {
            Text("Volver al inicio")
        }
    }
}

@Composable
private fun ReceiptCard(report: Report) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = CampusSurface,
        border = BorderStroke(1.dp, CampusBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ReceiptRow(
                label = "Folio",
                value = "#${report.id}"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Estado",
                    style = MaterialTheme.typography.labelMedium,
                    color = CampusInkMuted
                )
                StatusPill(status = report.status)
            }
            ReceiptRow(label = "Servicio", value = report.serviceName)
            ReceiptRow(label = "Fecha", value = report.createdAt.formatDateTime())
        }
    }
}

@Composable
private fun ReceiptRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = CampusInkMuted
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = CampusInk
        )
    }
}
