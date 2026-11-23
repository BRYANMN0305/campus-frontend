package co.edu.iub.campus_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.edu.iub.campus_app.ui.components.CampusBottomBar
import co.edu.iub.campus_app.ui.components.EmptyState
import co.edu.iub.campus_app.ui.components.ErrorState
import co.edu.iub.campus_app.ui.components.InlineError
import co.edu.iub.campus_app.ui.components.LoadingList
import co.edu.iub.campus_app.ui.components.ReportCard
import co.edu.iub.campus_app.ui.components.TopLevel
import co.edu.iub.campus_app.ui.theme.CampusCanvas
import co.edu.iub.campus_app.ui.theme.CampusInk
import co.edu.iub.campus_app.ui.theme.CampusInkMuted
import co.edu.iub.campus_app.ui.viewModel.MyReportsViewModel

@Composable
fun MyReportsScreen(
    onOpenCatalog: () -> Unit,
    onOpenHome: () -> Unit,
    onOpenReports: () -> Unit,
    viewModel: MyReportsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    Scaffold(
        containerColor = CampusCanvas,
        bottomBar = {
            CampusBottomBar(
                current = TopLevel.REPORTS,
                onHome = onOpenHome,
                onCatalog = onOpenCatalog,
                onReports = onOpenReports
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Column {
                Text(
                    text = "Mis reportes",
                    style = MaterialTheme.typography.headlineLarge,
                    color = CampusInk
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Sigue el estado de tus envíos.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CampusInkMuted
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Código anónimo  ${state.authorCode.ifBlank { "—" }}",
                style = MaterialTheme.typography.labelMedium,
                color = CampusInkMuted
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                state.loading && state.reports.isEmpty() -> {
                    LoadingList(count = 4)
                }

                state.error != null && state.reports.isEmpty() -> {
                    ErrorState(
                        message = state.error ?: "",
                        onRetry = viewModel::load
                    )
                }

                state.reports.isEmpty() -> {
                    EmptyState(
                        icon = Icons.AutoMirrored.Outlined.ReceiptLong,
                        title = "Todavía no has reportado novedades",
                        message = "Cuando envíes una, aparecerá aquí con su estado y fecha.",
                        actionLabel = "Explorar servicios",
                        onAction = onOpenCatalog
                    )
                }

                else -> {
                    if (state.error != null) {
                        InlineError(
                            message = state.error ?: "",
                            onRetry = viewModel::load
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        items(state.reports, key = { it.id }) { report ->
                            ReportCard(report = report)
                        }
                    }
                }
            }
        }
    }
}
