package co.edu.iub.campus_app.domain.usecase

import co.edu.iub.campus_app.domain.model.Service
import co.edu.iub.campus_app.domain.model.enums.ServiceCategory
import co.edu.iub.campus_app.domain.repository.ServiceRepository

class ListServicesUseCase(
    private val repository: ServiceRepository
) {
    suspend operator fun invoke(query: String?, category: ServiceCategory?): List<Service> {
        return repository.list(query, category)
    }
}
