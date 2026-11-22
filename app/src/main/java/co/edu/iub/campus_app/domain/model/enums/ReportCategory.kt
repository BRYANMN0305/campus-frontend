package co.edu.iub.campus_app.domain.model.enums

enum class ReportCategory(val label: String) {
    HORARIO_DESACTUALIZADO("Horario desactualizado"),
    EQUIPO_FUERA_DE_SERVICIO("Equipo fuera de servicio"),
    INFORMACION_INCOMPLETA("Información incompleta"),
    ACCESO_INCORRECTO("Acceso incorrecto"),
    OTRO("Otro");

    companion object {
        fun from(value: String?): ReportCategory =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: OTRO
    }
}
