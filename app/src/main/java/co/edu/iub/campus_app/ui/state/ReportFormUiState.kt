package co.edu.iub.campus_app.ui.state

import co.edu.iub.campus_app.domain.model.Service
import co.edu.iub.campus_app.domain.model.enums.ReportCategory

data class ReportFormUiState(
    val loadingService: Boolean = true,
    val service: Service? = null,
    val serviceError: String? = null,
    val category: ReportCategory? = null,
    val description: String = "",
    val categoryError: String? = null,
    val descriptionError: String? = null,
    val submitting: Boolean = false,
    val submitError: String? = null
)
