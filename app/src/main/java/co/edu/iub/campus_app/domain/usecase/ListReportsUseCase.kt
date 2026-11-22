package co.edu.iub.campus_app.domain.usecase

import co.edu.iub.campus_app.domain.model.Report
import co.edu.iub.campus_app.domain.repository.ReportRepository

class ListReportsUseCase(
    private val repository: ReportRepository
) {
    suspend operator fun invoke(authorCode: String): List<Report> {
        return repository.listByAuthor(authorCode)
    }
}
