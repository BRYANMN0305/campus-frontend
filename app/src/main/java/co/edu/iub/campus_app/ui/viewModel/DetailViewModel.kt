package co.edu.iub.campus_app.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.iub.campus_app.data.remote.error.ApiErrorMapper
import co.edu.iub.campus_app.domain.usecase.GetServiceUseCase
import co.edu.iub.campus_app.ui.state.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getService: GetServiceUseCase,
    private val errorMapper: ApiErrorMapper
) : ViewModel() {

    private val serviceId: Long = savedStateHandle.get<Long>("serviceId") ?: 0L

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }

            try {
                val service = getService(serviceId)
                _uiState.update {
                    it.copy(
                        loading = false,
                        service = service,
                        error = null
                    )
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
