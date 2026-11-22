package co.edu.iub.campus_app.data.remote.response

data class ReportResponse(
    val id: Long,
    val serviceId: Long,
    val serviceName: String,
    val category: String,
    val description: String,
    val status: String,
    val createdAt: String,
    val authorCode: String
)
