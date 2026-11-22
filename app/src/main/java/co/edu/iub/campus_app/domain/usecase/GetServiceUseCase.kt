package co.edu.iub.campus_app.domain.usecase

import co.edu.iub.campus_app.domain.model.Service
import co.edu.iub.campus_app.domain.repository.ServiceRepository

class GetServiceUseCase(
    private val repository: ServiceRepository
) {
    suspend operator fun invoke(id: Long): Service {
        return repository.get(id)
    }
}
