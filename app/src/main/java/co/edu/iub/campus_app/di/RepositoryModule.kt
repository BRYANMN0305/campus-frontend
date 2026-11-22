package co.edu.iub.campus_app.di

import co.edu.iub.campus_app.data.repository.ReportRepositoryImpl
import co.edu.iub.campus_app.data.repository.ServiceRepositoryImpl
import co.edu.iub.campus_app.domain.repository.ReportRepository
import co.edu.iub.campus_app.domain.repository.ServiceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindServiceRepository(implementation: ServiceRepositoryImpl): ServiceRepository

    @Binds
    @Singleton
    abstract fun bindReportRepository(implementation: ReportRepositoryImpl): ReportRepository
}
