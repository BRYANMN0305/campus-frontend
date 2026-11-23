package co.edu.iub.campus_app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.iub.campus_app.data.local.AuthorCodeStore
import co.edu.iub.campus_app.data.remote.error.ApiErrorMapper
import co.edu.iub.campus_app.domain.usecase.ListReportsUseCase
import co.edu.iub.campus_app.ui.state.MyReportsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyReportsViewModel @Inject constructor(
    private val listReports: ListReportsUseCase,
    private val authorCodeStore: AuthorCodeStore,
    private val errorMapper: ApiErrorMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MyReportsUiState(authorCode = authorCodeStore.getOrCreate())
    )
    val uiState = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }

            try {
                val reports = listReports(_uiState.value.authorCode)
                _uiState.update {
                    it.copy(
                        loading = false,
                        reports = reports,
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
