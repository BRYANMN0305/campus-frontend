package co.edu.iub.campus_app.data.repository

import co.edu.iub.campus_app.data.mapper.request.toRequest
import co.edu.iub.campus_app.data.mapper.response.toDomain
import co.edu.iub.campus_app.data.remote.ReportApi
import co.edu.iub.campus_app.domain.model.RecentReport
import co.edu.iub.campus_app.domain.model.Report
import co.edu.iub.campus_app.domain.model.ReportInput
import co.edu.iub.campus_app.domain.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val api: ReportApi
) : ReportRepository {

    override suspend fun create(input: ReportInput): Report {
        return api.create(input.toRequest()).toDomain()
    }

    override suspend fun listByAuthor(authorCode: String): List<Report> {
        return api.listByAuthor(authorCode).map { it.toDomain() }
    }

    override suspend fun recent(limit: Int): List<RecentReport> {
        return api.recent(limit).map { it.toDomain() }
    }
}
