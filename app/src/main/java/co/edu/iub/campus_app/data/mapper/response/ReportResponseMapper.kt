package co.edu.iub.campus_app.data.mapper.response

import co.edu.iub.campus_app.data.mapper.toLocalDateTimeOrNow
import co.edu.iub.campus_app.data.remote.response.RecentReportResponse
import co.edu.iub.campus_app.data.remote.response.ReportResponse
import co.edu.iub.campus_app.domain.model.RecentReport
import co.edu.iub.campus_app.domain.model.Report
import co.edu.iub.campus_app.domain.model.enums.ReportCategory
import co.edu.iub.campus_app.domain.model.enums.ReportStatus

fun ReportResponse.toDomain(): Report {
    return Report(
        id = id,
        serviceId = serviceId,
        serviceName = serviceName,
        category = ReportCategory.from(category),
        description = description,
        status = ReportStatus.from(status),
        createdAt = createdAt.toLocalDateTimeOrNow(),
        authorCode = authorCode
    )
}

fun RecentReportResponse.toDomain(): RecentReport {
    return RecentReport(
        id = id,
        serviceId = serviceId,
        serviceName = serviceName,
        category = ReportCategory.from(category),
        description = description,
        status = ReportStatus.from(status),
        createdAt = createdAt.toLocalDateTimeOrNow()
    )
}
