package co.edu.iub.campus_app.domain.usecase

import co.edu.iub.campus_app.domain.model.RecentReport
import co.edu.iub.campus_app.domain.repository.ReportRepository

class ListRecentReportsUseCase(
    private val repository: ReportRepository
) {
    suspend operator fun invoke(limit: Int = 5): List<RecentReport> {
        return repository.recent(limit)
    }
}
