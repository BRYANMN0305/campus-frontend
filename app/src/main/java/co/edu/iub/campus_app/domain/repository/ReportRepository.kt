package co.edu.iub.campus_app.domain.repository

import co.edu.iub.campus_app.domain.model.RecentReport
import co.edu.iub.campus_app.domain.model.Report
import co.edu.iub.campus_app.domain.model.ReportInput

interface ReportRepository {
    suspend fun create(input: ReportInput): Report
    suspend fun listByAuthor(authorCode: String): List<Report>
    suspend fun recent(limit: Int = 5): List<RecentReport>
}
