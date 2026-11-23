package co.edu.iub.campus_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.edu.iub.campus_app.ui.screens.CatalogScreen
import co.edu.iub.campus_app.ui.screens.ConfirmationScreen
import co.edu.iub.campus_app.ui.screens.HomeScreen
import co.edu.iub.campus_app.ui.screens.MyReportsScreen
import co.edu.iub.campus_app.ui.screens.ReportFormScreen
import co.edu.iub.campus_app.ui.screens.ServiceDetailScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onOpenCatalog = {
                    navController.navigate(Routes.catalog()) {
                        launchSingleTop = true
                    }
                },
                onOpenCategory = { category ->
                    navController.navigate(Routes.catalog(category)) {
                        launchSingleTop = true
                    }
                },
                onOpenService = { serviceId ->
                    navController.navigate(Routes.serviceDetail(serviceId))
                },
                onOpenReports = {
                    navController.navigateTopLevel(Routes.MY_REPORTS)
                },
                onOpenHome = {
                    navController.navigateTopLevel(Routes.HOME)
                }
            )
        }

        composable(
            route = Routes.CATALOG,
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                }
            )
        ) {
            CatalogScreen(
                onOpenService = { serviceId ->
                    navController.navigate(Routes.serviceDetail(serviceId))
                },
                onOpenHome = {
                    navController.navigateTopLevel(Routes.HOME)
                },
                onOpenCatalog = {
                    navController.navigateTopLevel(Routes.catalog())
                },
                onOpenReports = {
                    navController.navigateTopLevel(Routes.MY_REPORTS)
                }
            )
        }

        composable(
            route = Routes.SERVICE_DETAIL,
            arguments = listOf(
                navArgument("serviceId") {
                    type = NavType.LongType
                }
            )
        ) {
            ServiceDetailScreen(
                onBack = { navController.popBackStack() },
                onReport = { serviceId ->
                    navController.navigate(Routes.reportForm(serviceId))
                }
            )
        }

        composable(
            route = Routes.REPORT_FORM,
            arguments = listOf(
                navArgument("serviceId") {
                    type = NavType.LongType
                }
            )
        ) {
            ReportFormScreen(
                onBack = { navController.popBackStack() },
                onReportCreated = { reportId ->
                    navController.navigate(Routes.confirmation(reportId)) {
                        popUpTo(Routes.REPORT_FORM) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Routes.CONFIRMATION,
            arguments = listOf(
                navArgument("reportId") {
                    type = NavType.LongType
                }
            )
        ) {
            ConfirmationScreen(
                onViewReports = {
                    navController.navigate(Routes.MY_REPORTS) {
                        popUpTo(Routes.HOME)
                    }
                },
                onBackHome = {
                    navController.popBackStack(Routes.HOME, false)
                }
            )
        }

        composable(Routes.MY_REPORTS) {
            MyReportsScreen(
                onOpenCatalog = {
                    navController.navigateTopLevel(Routes.catalog())
                },
                onOpenHome = {
                    navController.navigateTopLevel(Routes.HOME)
                },
                onOpenReports = {
                    navController.navigateTopLevel(Routes.MY_REPORTS)
                }
            )
        }
    }
}

private fun NavHostController.navigateTopLevel(route: String) {
    if (route == Routes.HOME) {
        popBackStack(Routes.HOME, false)
        return
    }

    navigate(route) {
        popUpTo(Routes.HOME)
        launchSingleTop = true
    }
}
