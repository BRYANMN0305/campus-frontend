package co.edu.iub.campus_app.navigation

import co.edu.iub.campus_app.domain.model.enums.ServiceCategory

object Routes {

    const val HOME = "home"
    const val CATALOG = "catalog/{category}"
    const val SERVICE_DETAIL = "service/{serviceId}"
    const val REPORT_FORM = "report/{serviceId}"
    const val CONFIRMATION = "confirmation/{reportId}"
    const val MY_REPORTS = "my_reports"

    private const val ALL_CATEGORIES = "ALL"

    fun catalog(category: ServiceCategory? = null): String {
        return "catalog/${category?.name ?: ALL_CATEGORIES}"
    }

    fun serviceDetail(serviceId: Long): String = "service/$serviceId"

    fun reportForm(serviceId: Long): String = "report/$serviceId"

    fun confirmation(reportId: Long): String = "confirmation/$reportId"
}
