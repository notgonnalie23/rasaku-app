package app.capstone.rasaku.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.capstone.rasaku.MainViewModel
import app.capstone.rasaku.data.Repository
import app.capstone.rasaku.di.Injection
import app.capstone.rasaku.ui.screen.camera.CameraViewModel
import app.capstone.rasaku.ui.screen.detail.DetailViewModel
import app.capstone.rasaku.ui.screen.favorite.FavoriteViewModel
import app.capstone.rasaku.ui.screen.favoritelist.FavoriteListViewModel
import app.capstone.rasaku.ui.screen.history.HistoryViewModel
import app.capstone.rasaku.ui.screen.home.HomeViewModel
import app.capstone.rasaku.ui.screen.login.LoginViewModel
import app.capstone.rasaku.ui.screen.register.RegisterViewModel
import app.capstone.rasaku.ui.screen.search.SearchViewModel
import app.capstone.rasaku.ui.screen.searchinput.SearchInputViewModel
import app.capstone.rasaku.ui.screen.searchresult.SearchResultViewModel

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(repository) as T
            }

            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
                CameraViewModel(repository) as T
            }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository) as T
            }

            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SearchInputViewModel::class.java) -> {
                SearchInputViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SearchResultViewModel::class.java) -> {
                SearchResultViewModel(repository) as T
            }

            modelClass.isAssignableFrom(FavoriteListViewModel::class.java) -> {
                FavoriteListViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}