package app.capstone.rasaku.ui.screen.favoritelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.capstone.rasaku.data.Repository
import app.capstone.rasaku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteListViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<FavoriteListState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteListState>>
        get() = _uiState

    fun getFoods(id: Long){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getFoodByFavoriteId(id).collect{
                _uiState.value = UiState.Success(FavoriteListState(it))
            }
        }
    }

    fun deleteFood(foodId: Int, favoriteId: Long){
        viewModelScope.launch {
            repository.deleteFood(foodId, favoriteId)
        }
    }
    fun deleteFavorite(id: Long) {
        viewModelScope.launch {
            repository.deleteFavorite(id)
        }
    }
}