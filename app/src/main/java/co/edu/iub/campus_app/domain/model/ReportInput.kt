package co.edu.iub.campus_app.domain.model

import co.edu.iub.campus_app.domain.model.enums.ReportCategory

data class ReportInput(
    val serviceId: Long,
    val category: ReportCategory,
    val description: String,
    val authorCode: String
)
