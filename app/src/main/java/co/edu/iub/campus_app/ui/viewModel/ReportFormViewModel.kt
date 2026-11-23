package co.edu.iub.campus_app.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.iub.campus_app.data.local.AuthorCodeStore
import co.edu.iub.campus_app.data.remote.error.ApiErrorMapper
import co.edu.iub.campus_app.domain.model.ReportInput
import co.edu.iub.campus_app.domain.model.enums.ReportCategory
import co.edu.iub.campus_app.domain.usecase.CreateReportUseCase
import co.edu.iub.campus_app.domain.usecase.GetServiceUseCase
import co.edu.iub.campus_app.ui.state.ReportFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ReportFormEvent {
    data class ReportCreated(val reportId: Long) : ReportFormEvent
}

@HiltViewModel
class ReportFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getService: GetServiceUseCase,
    private val createReport: CreateReportUseCase,
    private val authorCodeStore: AuthorCodeStore,
    private val errorMapper: ApiErrorMapper
) : ViewModel() {

    private val serviceId: Long = savedStateHandle.get<Long>("serviceId") ?: 0L
    private val authorCode: String = authorCodeStore.getOrCreate()

    private val _uiState = MutableStateFlow(ReportFormUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ReportFormEvent>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    init {
        loadService()
    }

    fun loadService() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadingService = true, serviceError = null) }

            try {
                val service = getService(serviceId)
                _uiState.update {
                    it.copy(
                        loadingService = false,
                        service = service,
                        serviceError = null
                    )
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        loadingService = false,
                        serviceError = errorMapper.message(exception)
                    )
                }
            }
        }
    }

    fun onCategoryChange(category: ReportCategory) {
        _uiState.update {
            it.copy(
                category = category,
                categoryError = null,
                submitError = null
            )
        }
    }

    fun onDescriptionChange(value: String) {
        _uiState.update {
            it.copy(
                description = value.take(MAX_DESCRIPTION_LENGTH),
                descriptionError = null,
                submitError = null
            )
        }
    }

    fun submit() {
        val current = _uiState.value
        val category = current.category
        val description = current.description.trim()

        val categoryError = if (category == null) "Selecciona una categoría." else null
        val descriptionError = when {
            description.length < MIN_DESCRIPTION_LENGTH -> "Escribe al menos $MIN_DESCRIPTION_LENGTH caracteres."
            description.length > MAX_DESCRIPTION_LENGTH -> "La descripción no puede superar los $MAX_DESCRIPTION_LENGTH caracteres."
            else -> null
        }

        if (categoryError != null || descriptionError != null) {
            _uiState.update {
                it.copy(
                    categoryError = categoryError,
                    descriptionError = descriptionError
                )
            }
            return
        }

        val selectedCategory = category ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(submitting = true, submitError = null) }

            try {
                val report = createReport(
                    ReportInput(
                        serviceId = serviceId,
                        category = selectedCategory,
                        description = description,
                        authorCode = authorCode
                    )
                )
                _uiState.update { it.copy(submitting = false) }
                _events.emit(ReportFormEvent.ReportCreated(report.id))
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        submitting = false,
                        submitError = errorMapper.message(exception)
                    )
                }
            }
        }
    }

    private companion object {
        const val MIN_DESCRIPTION_LENGTH = 10
        const val MAX_DESCRIPTION_LENGTH = 500
    }
}
