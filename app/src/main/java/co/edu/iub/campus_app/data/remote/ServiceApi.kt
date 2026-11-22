package co.edu.iub.campus_app.data.remote

import co.edu.iub.campus_app.data.remote.response.ServiceResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("services")
    suspend fun list(
        @Query("q") query: String?,
        @Query("category") category: String?
    ): List<ServiceResponse>

    @GET("services/{id}")
    suspend fun get(@Path("id") id: Long): ServiceResponse
}
