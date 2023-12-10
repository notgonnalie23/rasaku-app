package app.capstone.rasaku.data

import app.capstone.rasaku.model.Favorite
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val database: RasakuDatabase
) {
    suspend fun getFoodByFavoriteId(id: Long) = database.foodDao().getFoodByFavoriteId(id)
    fun getFavorites(): Flow<List<Favorite>> = database.favoriteDao().getFavorites()

    fun getFavoriteById(id: Long) = database.favoriteDao().getFavoriteById(id)

    suspend fun insertFavorite(favorite: Favorite) = database.favoriteDao().insertFavorite(favorite)

    suspend fun updateFavorite(favorite: Favorite) = database.favoriteDao().updateFavorite(favorite)

    suspend fun deleteFavorite(favorite: Favorite) = database.favoriteDao().deleteFavorite(favorite)

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            database: RasakuDatabase
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(database)
        }.also { instance }
    }
}