package co.edu.iub.campus_app.domain.repository

import co.edu.iub.campus_app.domain.model.Service
import co.edu.iub.campus_app.domain.model.enums.ServiceCategory

interface ServiceRepository {
    suspend fun list(query: String?, category: ServiceCategory?): List<Service>
    suspend fun get(id: Long): Service
}
