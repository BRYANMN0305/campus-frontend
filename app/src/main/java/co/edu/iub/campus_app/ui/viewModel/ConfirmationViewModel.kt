package co.edu.iub.campus_app.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.iub.campus_app.data.local.AuthorCodeStore
import co.edu.iub.campus_app.data.remote.error.ApiErrorMapper
import co.edu.iub.campus_app.domain.usecase.ListReportsUseCase
import co.edu.iub.campus_app.ui.state.ConfirmationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val listReports: ListReportsUseCase,
    private val authorCodeStore: AuthorCodeStore,
    private val errorMapper: ApiErrorMapper
) : ViewModel() {

    private val reportId: Long = savedStateHandle.get<Long>("reportId") ?: 0L

    private val _uiState = MutableStateFlow(ConfirmationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }

            try {
                val authorCode = authorCodeStore.getOrCreate()
                val report = listReports(authorCode).firstOrNull { it.id == reportId }

                if (report == null) {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            error = "No encontramos el reporte. Inténtalo de nuevo."
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            report = report,
                            error = null
                        )
                    }
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = errorMapper.message(exception)
                    )
                }
            }
        }
    }
}
