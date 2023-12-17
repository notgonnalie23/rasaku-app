package app.capstone.rasaku.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.capstone.rasaku.data.Repository
import app.capstone.rasaku.model.FoodsItem
import app.capstone.rasaku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<HomeState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<HomeState>>
        get() = _uiState

    fun getFoods() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getFoods()
                val foods = mutableListOf<FoodsItem?>()
                val carousel = mutableListOf<FoodsItem>()
                response.data?.let { foodList ->
                    foods.addAll(foodList)
                    foodList.asSequence().shuffled().take(4).toList().map { item ->
                        item?.let {
                            carousel.add(it)
                        }
                    }
                }
                _uiState.value = UiState.Success(HomeState(foods, carousel))
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}