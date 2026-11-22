package co.edu.iub.campus_app.data.remote

import co.edu.iub.campus_app.data.remote.request.ReportRequest
import co.edu.iub.campus_app.data.remote.response.RecentReportResponse
import co.edu.iub.campus_app.data.remote.response.ReportResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ReportApi {

    @POST("reports")
    suspend fun create(@Body request: ReportRequest): ReportResponse

    @GET("reports")
    suspend fun listByAuthor(@Query("authorCode") authorCode: String): List<ReportResponse>

    @GET("reports/recent")
    suspend fun recent(@Query("limit") limit: Int = 5): List<RecentReportResponse>
}
