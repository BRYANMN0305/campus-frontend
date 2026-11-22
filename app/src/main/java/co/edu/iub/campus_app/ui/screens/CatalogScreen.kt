package co.edu.iub.campus_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.edu.iub.campus_app.domain.model.enums.ServiceCategory
import co.edu.iub.campus_app.ui.components.CampusBottomBar
import co.edu.iub.campus_app.ui.components.EmptyState
import co.edu.iub.campus_app.ui.components.ErrorState
import co.edu.iub.campus_app.ui.components.InlineError
import co.edu.iub.campus_app.ui.components.LoadingList
import co.edu.iub.campus_app.ui.components.SearchField
import co.edu.iub.campus_app.ui.components.ServiceCard
import co.edu.iub.campus_app.ui.components.TopLevel
import co.edu.iub.campus_app.ui.theme.CampusCanvas
import co.edu.iub.campus_app.ui.theme.CampusInk
import co.edu.iub.campus_app.ui.theme.CampusInkMuted
import co.edu.iub.campus_app.ui.viewModel.CatalogViewModel

@Composable
fun CatalogScreen(
    onOpenService: (Long) -> Unit,
    onOpenHome: () -> Unit,
    onOpenCatalog: () -> Unit,
    onOpenReports: () -> Unit,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = CampusCanvas,
        bottomBar = {
            CampusBottomBar(
                current = TopLevel.CATALOG,
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
                .imePadding()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Servicios",
                style = MaterialTheme.typography.headlineLarge,
                color = CampusInk
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (state.loading && state.services.isEmpty()) {
                    "Buscando servicios"
                } else {
                    "${state.services.size} resultados"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = CampusInkMuted
            )
            Spacer(modifier = Modifier.height(16.dp))

            SearchField(
                value = state.query,
                onValueChange = viewModel::onQueryChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            CategoryFilters(
                selected = state.category,
                onSelect = viewModel::onCategoryChange
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (state.error != null && state.services.isNotEmpty()) {
                InlineError(
                    message = state.error ?: "",
                    onRetry = { viewModel.load() }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            when {
                state.loading && state.services.isEmpty() -> {
                    LoadingList(count = 5)
                }

                state.error != null && state.services.isEmpty() -> {
                    ErrorState(
                        message = state.error ?: "",
                        onRetry = { viewModel.load() }
                    )
                }

                state.services.isEmpty() -> {
                    EmptyState(
                        icon = Icons.Outlined.Search,
                        title = "No encontramos servicios",
                        message = "Prueba con otro texto o quita los filtros.",
                        actionLabel = "Limpiar filtros",
                        onAction = viewModel::clearFilters
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        items(state.services, key = { it.id }) { service ->
                            ServiceCard(
                                service = service,
                                onClick = { onOpenService(service.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryFilters(
    selected: ServiceCategory?,
    onSelect: (ServiceCategory?) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        item {
            FilterText(
                label = "Todos",
                selected = selected == null,
                onClick = { onSelect(null) }
            )
        }
        items(ServiceCategory.entries, key = { it.name }) { category ->
            FilterText(
                label = category.label,
                selected = selected == category,
                onClick = { onSelect(category) }
            )
        }
    }
}

@Composable
private fun FilterText(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (selected) CampusInk else CampusInkMuted,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(if (selected) 18.dp else 0.dp)
                .height(2.dp)
                .background(if (selected) CampusInk else Color.Transparent)
        )
    }
}
