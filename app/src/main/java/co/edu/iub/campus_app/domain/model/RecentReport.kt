package co.edu.iub.campus_app.domain.model

import co.edu.iub.campus_app.domain.model.enums.ReportCategory
import co.edu.iub.campus_app.domain.model.enums.ReportStatus
import java.time.LocalDateTime

data class RecentReport(
    val id: Long,
    val serviceId: Long,
    val serviceName: String,
    val category: ReportCategory,
    val description: String,
    val status: ReportStatus,
    val createdAt: LocalDateTime
)
