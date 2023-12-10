package app.capstone.rasaku.ui.screen.favoritelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.capstone.rasaku.data.Repository
import kotlinx.coroutines.launch

class FavoriteListViewModel(private val repository: Repository) : ViewModel() {

    fun deleteFavorite(id: Long) {
        viewModelScope.launch {
            repository.deleteFavorite(id)
        }
    }
}