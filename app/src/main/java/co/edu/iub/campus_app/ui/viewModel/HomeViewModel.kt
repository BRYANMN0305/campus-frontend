package co.edu.iub.campus_app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.iub.campus_app.data.remote.error.ApiErrorMapper
import co.edu.iub.campus_app.domain.usecase.ListRecentReportsUseCase
import co.edu.iub.campus_app.domain.usecase.ListServicesUseCase
import co.edu.iub.campus_app.ui.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val listServices: ListServicesUseCase,
    private val listRecentReports: ListRecentReportsUseCase,
    private val errorMapper: ApiErrorMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }

            try {
                val services = listServices(null, null)
                val recent = listRecentReports(5)
                _uiState.update {
                    it.copy(
                        loading = false,
                        services = services,
                        recent = recent,
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
