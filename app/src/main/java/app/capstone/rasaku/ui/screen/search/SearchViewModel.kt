package app.capstone.rasaku.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.capstone.rasaku.data.Repository
import app.capstone.rasaku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<SearchState>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<SearchState>>
        get() = _uiState

    fun getFoods() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val foodsResponse = repository.getFoods()
                _uiState.value = UiState.Success(SearchState(foodsResponse.data))
            }
            catch (e : Exception){
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}