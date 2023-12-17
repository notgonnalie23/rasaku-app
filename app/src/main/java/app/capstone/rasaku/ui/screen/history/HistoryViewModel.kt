package app.capstone.rasaku.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.capstone.rasaku.data.Repository
import app.capstone.rasaku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: Repository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<HistoryState>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<HistoryState>>
        get() = _uiState

    fun getHistories(){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getHistories().collect{
                _uiState.value = UiState.Success(HistoryState(it))
            }
        }
    }

    fun deleteHistory(id: Int){
        viewModelScope.launch {
            repository.deleteHistory(id)
        }
    }
}