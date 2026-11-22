package co.edu.iub.campus_app.domain.model.enums

enum class ServiceCategory(val label: String) {
    BIBLIOTECA("Biblioteca"),
    LABORATORIO("Laboratorio"),
    SALA_COMPUTO("Sala de cómputo"),
    CAFETERIA("Cafetería"),
    TUTORIA("Tutoría"),
    OTRO("Otro");

    companion object {
        fun from(value: String?): ServiceCategory =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: OTRO
    }
}
