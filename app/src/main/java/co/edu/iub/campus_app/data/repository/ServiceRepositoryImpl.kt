package co.edu.iub.campus_app.data.repository

import co.edu.iub.campus_app.data.mapper.response.toDomain
import co.edu.iub.campus_app.data.remote.ServiceApi
import co.edu.iub.campus_app.domain.model.Service
import co.edu.iub.campus_app.domain.model.enums.ServiceCategory
import co.edu.iub.campus_app.domain.repository.ServiceRepository
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val api: ServiceApi
) : ServiceRepository {

    override suspend fun list(query: String?, category: ServiceCategory?): List<Service> {
        return api.list(query, category?.name).map { it.toDomain() }
    }

    override suspend fun get(id: Long): Service {
        return api.get(id).toDomain()
    }
}
