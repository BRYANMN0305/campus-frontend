package co.edu.iub.campus_app.data.remote.request

data class ReportRequest(
    val serviceId: Long,
    val category: String,
    val description: String,
    val authorCode: String
)
