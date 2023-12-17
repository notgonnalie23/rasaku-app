package app.capstone.rasaku.ui.screen.searchinput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.capstone.rasaku.data.Repository
import app.capstone.rasaku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchInputViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<SearchInputState>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<SearchInputState>>
        get() = _uiState

    fun getHistories() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getHistories().collect { data ->
                _uiState.value = UiState.Success(SearchInputState(histories = data))
            }
        }
    }

    fun deleteHistory(id: Int) {
        viewModelScope.launch {
            repository.deleteHistory(id)
        }
    }
}