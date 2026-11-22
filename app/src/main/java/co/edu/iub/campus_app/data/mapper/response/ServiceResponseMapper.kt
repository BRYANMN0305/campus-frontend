package co.edu.iub.campus_app.data.mapper.response

import co.edu.iub.campus_app.data.mapper.toLocalDateTimeOrNow
import co.edu.iub.campus_app.data.remote.response.ServiceResponse
import co.edu.iub.campus_app.domain.model.Service
import co.edu.iub.campus_app.domain.model.enums.Availability
import co.edu.iub.campus_app.domain.model.enums.ServiceCategory

fun ServiceResponse.toDomain(): Service {
    return Service(
        id = id,
        name = name,
        category = ServiceCategory.from(category),
        description = description,
        schedule = schedule,
        location = location,
        availability = Availability.from(availability),
        accessConditions = accessConditions,
        updatedAt = updatedAt.toLocalDateTimeOrNow()
    )
}
