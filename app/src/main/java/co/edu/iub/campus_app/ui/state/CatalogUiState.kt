package co.edu.iub.campus_app.ui.state

import co.edu.iub.campus_app.domain.model.Service
import co.edu.iub.campus_app.domain.model.enums.ServiceCategory

data class CatalogUiState(
    val query: String = "",
    val category: ServiceCategory? = null,
    val services: List<Service> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null
)
