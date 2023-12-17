package app.capstone.rasaku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.capstone.rasaku.data.Repository
import app.capstone.rasaku.model.Favorite
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        viewModelScope.launch {
            delay(DELAY_IN_MILLIS)
            _isReady.value = true
        }
    }

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.insertFavorite(favorite)
        }
    }

    companion object {
        const val DELAY_IN_MILLIS = 500L
    }
}