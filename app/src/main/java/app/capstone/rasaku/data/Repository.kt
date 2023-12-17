package app.capstone.rasaku.data

import app.capstone.rasaku.model.Favorite
import app.capstone.rasaku.model.Food
import app.capstone.rasaku.model.FoodResponse
import app.capstone.rasaku.model.FoodsResponse
import app.capstone.rasaku.model.History
import app.capstone.rasaku.network.ApiService
import app.capstone.rasaku.utils.FoodCountStatus
import kotlinx.coroutines.flow.Flow


class Repository private constructor(
    private val database: RasakuDatabase, private val apiService: ApiService
) {
    suspend fun getFoods(): FoodsResponse = apiService.getFoods()
    suspend fun getFoodsById(id: Int): FoodResponse = apiService.getFoodsById(id)

    suspend fun insertFood(food: Food) = database.foodDao().insert(food)

    suspend fun getFoodByFavoriteId(favoriteId: Long): Flow<List<Food>> =
        database.foodDao().getFoodByFavoriteId(favoriteId)

    suspend fun deleteFood(foodId: Int, favoriteId: Long) {
        database.foodDao().delete(foodId, favoriteId)
    }

    suspend fun deleteFoodByFavoriteId(favoriteId: Long){
        database.foodDao().deleteFoodByFavoriteId(favoriteId)
    }

    fun getFavorites(): Flow<List<Favorite>> = database.favoriteDao().getFavorites()

    fun getFavoriteById(id: Long): Flow<Favorite> = database.favoriteDao().getFavoriteById(id)
    suspend fun insertFavorite(favorite: Favorite) = database.favoriteDao().insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = database.favoriteDao().updateFavorite(favorite)
    suspend fun deleteFavorite(id: Long) {
        database.favoriteDao().deleteFavoriteById(id)
        database.foodDao().deleteFoodByFavoriteId(id)
    }

    suspend fun updateFoodCount(id: Long, status: FoodCountStatus) =
        database.favoriteDao().updateFoodCount(id, status.value)

    suspend fun setImageUrl(id: Long, imageUrl: String) =
        database.favoriteDao().setImageUrl(imageUrl, id)

    fun getHistories(): Flow<List<History>> = database.historyDao().getHistories()
    suspend fun insertHistory(history: History) = database.historyDao().insert(history)
    suspend fun deleteHistory(id: Int) = database.historyDao().delete(id)

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            database: RasakuDatabase,
            apiService: ApiService,
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(database, apiService)
        }.also { instance }
    }
}