package co.edu.iub.campus_app.data.remote.response

data class ApiErrorResponse(
    val status: Int? = null,
    val error: String? = null,
    val message: String? = null,
    val path: String? = null
)
