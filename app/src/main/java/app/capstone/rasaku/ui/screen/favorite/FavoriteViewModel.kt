package app.capstone.rasaku.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.capstone.rasaku.data.Repository
import app.capstone.rasaku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<FavoriteState>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteState>>
        get() = _uiState

    fun getFavorites() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getFavorites().collect { data ->
                _uiState.value = UiState.Success(FavoriteState(data))
            }
        }
    }
}
