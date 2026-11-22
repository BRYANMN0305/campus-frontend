package co.edu.iub.campus_app.domain.model.enums

enum class Availability(val label: String) {
    DISPONIBLE("Disponible"),
    NO_DISPONIBLE("No disponible"),
    MANTENIMIENTO("Mantenimiento");

    companion object {
        fun from(value: String?): Availability =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: NO_DISPONIBLE
    }
}
