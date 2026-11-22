package co.edu.iub.campus_app.data.remote.response

data class ServiceResponse(
    val id: Long,
    val name: String,
    val category: String,
    val description: String,
    val schedule: String,
    val location: String,
    val availability: String,
    val accessConditions: String,
    val updatedAt: String
)
