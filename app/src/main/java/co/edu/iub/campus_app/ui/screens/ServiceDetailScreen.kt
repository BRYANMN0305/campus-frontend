package co.edu.iub.campus_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.edu.iub.campus_app.domain.model.Service
import co.edu.iub.campus_app.ui.components.AvailabilityPill
import co.edu.iub.campus_app.ui.components.CategoryBadge
import co.edu.iub.campus_app.ui.components.ErrorState
import co.edu.iub.campus_app.ui.components.FullScreenLoading
import co.edu.iub.campus_app.ui.components.InfoRow
import co.edu.iub.campus_app.ui.theme.CampusBorder
import co.edu.iub.campus_app.ui.theme.CampusCanvas
import co.edu.iub.campus_app.ui.theme.CampusInk
import co.edu.iub.campus_app.ui.theme.CampusInkMuted
import co.edu.iub.campus_app.ui.util.formatDate
import co.edu.iub.campus_app.ui.viewModel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceDetailScreen(
    onBack: () -> Unit,
    onReport: (Long) -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = CampusCanvas,
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CampusCanvas,
                    titleContentColor = CampusInk,
                    navigationIconContentColor = CampusInk
                )
            )
        },
        bottomBar = {
            state.service?.let { service ->
                Surface(color = CampusCanvas) {
                    Button(
                        onClick = { onReport(service.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                            .height(52.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Reportar una novedad")
                    }
                }
            }
        }
    ) { innerPadding ->
        when {
            state.loading -> {
                FullScreenLoading(modifier = Modifier.padding(innerPadding))
            }

            state.error != null -> {
                ErrorState(
                    message = state.error ?: "",
                    onRetry = viewModel::load,
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 20.dp)
                )
            }

            state.service != null -> {
                DetailContent(
                    service = state.service!!,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun DetailContent(
    service: Service,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            CategoryBadge(category = service.category, size = 56)
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = service.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = CampusInk
                )
                Text(
                    text = service.category.label,
                    style = MaterialTheme.typography.labelMedium,
                    color = CampusInkMuted
                )
            }
        }

        AvailabilityPill(availability = service.availability)

        HorizontalDivider(color = CampusBorder)

        InfoRow(
            icon = Icons.Outlined.Schedule,
            label = "Horario",
            value = service.schedule
        )
        InfoRow(
            icon = Icons.Outlined.Place,
            label = "Ubicación",
            value = service.location
        )
        InfoRow(
            icon = Icons.Outlined.Lock,
            label = "Condiciones de acceso",
            value = service.accessConditions
        )
        InfoRow(
            icon = Icons.Outlined.Update,
            label = "Última actualización",
            value = service.updatedAt.formatDate()
        )

        HorizontalDivider(color = CampusBorder)

        Text(
            text = "Descripción",
            style = MaterialTheme.typography.titleMedium,
            color = CampusInk
        )
        Text(
            text = service.description,
            style = MaterialTheme.typography.bodyMedium,
            color = CampusInkMuted
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
