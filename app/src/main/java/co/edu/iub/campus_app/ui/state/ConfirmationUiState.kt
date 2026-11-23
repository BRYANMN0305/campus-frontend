package co.edu.iub.campus_app.ui.state

import co.edu.iub.campus_app.domain.model.Report

data class ConfirmationUiState(
    val loading: Boolean = true,
    val report: Report? = null,
    val error: String? = null
)
