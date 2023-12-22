package app.capstone.rasaku.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.capstone.rasaku.data.Repository
import app.capstone.rasaku.model.Favorite
import app.capstone.rasaku.model.Food
import app.capstone.rasaku.model.FoodsItem
import app.capstone.rasaku.model.History
import app.capstone.rasaku.ui.common.UiState
import app.capstone.rasaku.utils.FoodCountStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<DetailState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DetailState>>
        get() = _uiState

    private val _dialogState: MutableStateFlow<UiState<DetailState>> =
        MutableStateFlow(UiState.Loading)
    val dialogState: StateFlow<UiState<DetailState>>
        get() = _dialogState

    fun getFoodById(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = repository.getFoods()
                val foodResponse = repository.getFoodsById(id)

                val food = foodResponse.data?.data?.get(0)
                val recommendations = mutableListOf<FoodsItem>()
                response.data?.let { data ->
                    data.asSequence().shuffled().toList().map { item ->
                        item?.let {
                            if (it.city == food?.city && recommendations.size < 6) {
                                recommendations.add(it)
                            }
                        }
                    }
                }

                _uiState.value = UiState.Success(
                    DetailState(
                        food = food,
                        recommendations = recommendations,
                    )
                )
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun getFavorites() {
        viewModelScope.launch {
            _dialogState.value = UiState.Loading
            repository.getFavorites().collect {
                _dialogState.value = UiState.Success(DetailState(favorites = it))
            }
        }
    }

    fun insertFavorites(favorite: Favorite) {
        viewModelScope.launch {
            repository.insertFavorite(favorite)
        }
    }

    fun insertFood(food: Food) {
        viewModelScope.launch {
            repository.insertFood(food)
            repository.updateFoodCount(food.favoriteId, FoodCountStatus.INCREASE)
            repository.getFavoriteById(food.favoriteId).collect {
                if (it.foodCount == 1) repository.setImageUrl(it.id, food.imageUrl)
            }
        }
    }

    fun insertHistory(history: History) {
        viewModelScope.launch {
            repository.insertHistory(history)
        }
    }
}