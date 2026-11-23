package co.edu.iub.campus_app.ui.state

import co.edu.iub.campus_app.domain.model.Service

data class DetailUiState(
    val loading: Boolean = true,
    val service: Service? = null,
    val error: String? = null
)
