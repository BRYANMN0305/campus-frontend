package co.edu.iub.campus_app.data.mapper.request

import co.edu.iub.campus_app.data.remote.request.ReportRequest
import co.edu.iub.campus_app.domain.model.ReportInput

fun ReportInput.toRequest(): ReportRequest {
    return ReportRequest(
        serviceId = serviceId,
        category = category.name,
        description = description,
        authorCode = authorCode
    )
}
