package co.edu.iub.campus_app.domain.model.enums

enum class ReportStatus(val label: String) {
    PENDIENTE("Pendiente"),
    EN_REVISION("En revisión"),
    RESUELTO("Resuelto"),
    RECHAZADO("Rechazado");

    companion object {
        fun from(value: String?): ReportStatus =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: PENDIENTE
    }
}
