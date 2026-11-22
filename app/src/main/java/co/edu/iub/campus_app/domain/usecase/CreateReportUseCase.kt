package co.edu.iub.campus_app.domain.usecase

import co.edu.iub.campus_app.domain.model.Report
import co.edu.iub.campus_app.domain.model.ReportInput
import co.edu.iub.campus_app.domain.repository.ReportRepository

class CreateReportUseCase(
    private val repository: ReportRepository
) {
    suspend operator fun invoke(input: ReportInput): Report {
        return repository.create(input)
    }
}
