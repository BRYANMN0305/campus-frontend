package co.edu.iub.campus_app.ui.state

import co.edu.iub.campus_app.domain.model.RecentReport
import co.edu.iub.campus_app.domain.model.Service

data class HomeUiState(
    val loading: Boolean = true,
    val services: List<Service> = emptyList(),
    val recent: List<RecentReport> = emptyList(),
    val error: String? = null
)
