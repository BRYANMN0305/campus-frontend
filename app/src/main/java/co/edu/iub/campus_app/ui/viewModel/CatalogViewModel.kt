package co.edu.iub.campus_app.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.iub.campus_app.data.remote.error.ApiErrorMapper
import co.edu.iub.campus_app.domain.model.enums.ServiceCategory
import co.edu.iub.campus_app.domain.usecase.ListServicesUseCase
import co.edu.iub.campus_app.ui.state.CatalogUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val listServices: ListServicesUseCase,
    private val errorMapper: ApiErrorMapper
) : ViewModel() {

    private val initialCategory: ServiceCategory? = savedStateHandle.get<String>("category")
        ?.takeIf { it != ALL_CATEGORY }
        ?.let { ServiceCategory.from(it) }

    private val _uiState = MutableStateFlow(CatalogUiState(category = initialCategory))
    val uiState = _uiState.asStateFlow()

    private var loadJob: Job? = null

    init {
        load()
    }

    fun onQueryChange(value: String) {
        _uiState.update { it.copy(query = value) }
        load(debounce = true)
    }

    fun onCategoryChange(category: ServiceCategory?) {
        _uiState.update { it.copy(category = category) }
        load()
    }

    fun clearFilters() {
        _uiState.update { it.copy(query = "", category = null) }
        load()
    }

    fun load(debounce: Boolean = false) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            if (debounce) delay(300)

            _uiState.update { it.copy(loading = true, error = null) }

            try {
                val current = _uiState.value
                val services = listServices(
                    current.query.trim().takeIf { it.isNotBlank() },
                    current.category
                )
                _uiState.update {
                    it.copy(
                        loading = false,
                        services = services,
                        error = null
                    )
                }
            } catch (exception: CancellationException) {
                throw exception
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

    private companion object {
        const val ALL_CATEGORY = "ALL"
    }
}
