package co.edu.iub.campus_app.data.remote.response

data class RecentReportResponse(
    val id: Long,
    val serviceId: Long,
    val serviceName: String,
    val category: String,
    val description: String,
    val status: String,
    val createdAt: String
)
