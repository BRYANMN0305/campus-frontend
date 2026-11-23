package co.edu.iub.campus_app.ui.state

import co.edu.iub.campus_app.domain.model.Report

data class MyReportsUiState(
    val loading: Boolean = true,
    val reports: List<Report> = emptyList(),
    val authorCode: String = "",
    val error: String? = null
)
