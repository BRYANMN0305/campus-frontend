package co.edu.iub.campus_app.di

import co.edu.iub.campus_app.domain.repository.ReportRepository
import co.edu.iub.campus_app.domain.repository.ServiceRepository
import co.edu.iub.campus_app.domain.usecase.CreateReportUseCase
import co.edu.iub.campus_app.domain.usecase.GetServiceUseCase
import co.edu.iub.campus_app.domain.usecase.ListRecentReportsUseCase
import co.edu.iub.campus_app.domain.usecase.ListReportsUseCase
import co.edu.iub.campus_app.domain.usecase.ListServicesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideListServices(repository: ServiceRepository) = ListServicesUseCase(repository)

    @Provides
    fun provideGetService(repository: ServiceRepository) = GetServiceUseCase(repository)

    @Provides
    fun provideCreateReport(repository: ReportRepository) = CreateReportUseCase(repository)

    @Provides
    fun provideListReports(repository: ReportRepository) = ListReportsUseCase(repository)

    @Provides
    fun provideListRecentReports(repository: ReportRepository) = ListRecentReportsUseCase(repository)
}
