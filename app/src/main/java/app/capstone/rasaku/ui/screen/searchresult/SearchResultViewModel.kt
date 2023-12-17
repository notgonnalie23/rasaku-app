package app.capstone.rasaku.ui.screen.searchresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.capstone.rasaku.data.Repository
import app.capstone.rasaku.model.FoodsItem
import app.capstone.rasaku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchResultViewModel(private val repository: Repository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<SearchResultState>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<SearchResultState>>
        get() = _uiState

    fun findFoods(q : String){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
            val foods = mutableListOf<FoodsItem>()
            val response = repository.getFoods()
            response.data?.forEach{item ->
                item?.let { food ->
                    if (food.foodName!!.contains(q) || food.descriptionFood!!.contains(q)){
                        foods.add(food)
                    }
                }
            }
            _uiState.value = UiState.Success(SearchResultState(foods))
            } catch (e : Exception){
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

}