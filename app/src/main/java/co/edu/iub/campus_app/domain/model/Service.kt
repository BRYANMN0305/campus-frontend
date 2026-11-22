package co.edu.iub.campus_app.domain.model

import co.edu.iub.campus_app.domain.model.enums.Availability
import co.edu.iub.campus_app.domain.model.enums.ServiceCategory
import java.time.LocalDateTime

data class Service(
    val id: Long,
    val name: String,
    val category: ServiceCategory,
    val description: String,
    val schedule: String,
    val location: String,
    val availability: Availability,
    val accessConditions: String,
    val updatedAt: LocalDateTime
)
