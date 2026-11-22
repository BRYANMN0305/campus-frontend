package co.edu.iub.campus_app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.edu.iub.campus_app.domain.model.Service
import co.edu.iub.campus_app.domain.model.enums.Availability
import co.edu.iub.campus_app.domain.model.enums.ServiceCategory
import co.edu.iub.campus_app.ui.components.CampusBottomBar
import co.edu.iub.campus_app.ui.components.CategoryCard
import co.edu.iub.campus_app.ui.components.EmptyState
import co.edu.iub.campus_app.ui.components.InlineError
import co.edu.iub.campus_app.ui.components.LoadingList
import co.edu.iub.campus_app.ui.components.RecentReportRow
import co.edu.iub.campus_app.ui.components.SectionHeader
import co.edu.iub.campus_app.ui.components.StatTile
import co.edu.iub.campus_app.ui.components.TopLevel
import co.edu.iub.campus_app.ui.theme.CampusBorder
import co.edu.iub.campus_app.ui.theme.CampusCanvas
import co.edu.iub.campus_app.ui.theme.CampusInk
import co.edu.iub.campus_app.ui.theme.CampusInkMuted
import co.edu.iub.campus_app.ui.theme.CampusSurface
import co.edu.iub.campus_app.ui.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    onOpenCatalog: () -> Unit,
    onOpenCategory: (ServiceCategory) -> Unit,
    onOpenService: (Long) -> Unit,
    onOpenReports: () -> Unit,
    onOpenHome: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    Scaffold(
        containerColor = CampusCanvas,
        bottomBar = {
            CampusBottomBar(
                current = TopLevel.HOME,
                onHome = onOpenHome,
                onCatalog = onOpenCatalog,
                onReports = onOpenReports
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                HomeHeader()
            }
            item {
                SearchShortcut(onClick = onOpenCatalog)
            }

            when {
                state.loading -> {
                    item {
                        StatsRow(services = emptyList(), recentCount = 0, loading = true)
                    }
                    item {
                        LoadingList(count = 3)
                    }
                }

                state.error != null -> {
                    item {
                        InlineError(
                            message = state.error ?: "",
                            onRetry = viewModel::load
                        )
                    }
                }

                else -> {
                    item {
                        StatsRow(
                            services = state.services,
                            recentCount = state.recent.size,
                            loading = false
                        )
                    }
                    item {
                        SectionHeader(title = "Categorías")
                    }
                    item {
                        CategoriesRow(
                            services = state.services,
                            onOpenCategory = onOpenCategory
                        )
                    }
                    item {
                        SectionHeader(title = "Novedades recientes")
                    }

                    if (state.recent.isEmpty()) {
                        item {
                            EmptyState(
                                icon = Icons.AutoMirrored.Outlined.ReceiptLong,
                                title = "Sin novedades por ahora",
                                message = "Cuando alguien reporte algo, lo verás aquí."
                            )
                        }
                    } else {
                        item {
                            Column {
                                state.recent.forEach { report ->
                                    RecentReportRow(
                                        report = report,
                                        onClick = { onOpenService(report.serviceId) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = CampusSurface,
            border = BorderStroke(1.dp, CampusBorder),
            modifier = Modifier.size(44.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "IUB",
                    color = CampusInk,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = "Campus IUB",
                style = MaterialTheme.typography.labelMedium,
                color = CampusInkMuted
            )
            Text(
                text = "Hola, ¿qué buscas hoy?",
                style = MaterialTheme.typography.headlineSmall,
                color = CampusInk
            )
        }
    }
}

@Composable
private fun SearchShortcut(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = CampusSurface,
        border = BorderStroke(1.dp, CampusBorder)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = CampusInkMuted
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Buscar un servicio",
                style = MaterialTheme.typography.bodyMedium,
                color = CampusInkMuted,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = CampusInkMuted,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun StatsRow(
    services: List<Service>,
    recentCount: Int,
    loading: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatTile(
            value = if (loading) "—" else services.size.toString(),
            label = "Servicios",
            modifier = Modifier.weight(1f)
        )
        VerticalDivider(
            modifier = Modifier.height(36.dp),
            color = CampusBorder
        )
        StatTile(
            value = if (loading) "—" else services.count { it.availability == Availability.DISPONIBLE }.toString(),
            label = "Disponibles",
            modifier = Modifier.weight(1f)
        )
        VerticalDivider(
            modifier = Modifier.height(36.dp),
            color = CampusBorder
        )
        StatTile(
            value = if (loading) "—" else recentCount.toString(),
            label = "Novedades",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun CategoriesRow(
    services: List<Service>,
    onOpenCategory: (ServiceCategory) -> Unit
) {
    val categories = ServiceCategory.entries
        .map { category -> category to services.count { it.category == category } }
        .filter { (_, count) -> count > 0 }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(categories, key = { it.first.name }) { (category, count) ->
            CategoryCard(
                category = category,
                count = count,
                onClick = { onOpenCategory(category) }
            )
        }
    }
}
