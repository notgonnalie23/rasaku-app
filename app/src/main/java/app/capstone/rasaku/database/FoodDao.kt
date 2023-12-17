package app.capstone.rasaku.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.capstone.rasaku.model.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM foods WHERE favorite_id = :favoriteId")
    fun getFoodByFavoriteId(favoriteId: Long): Flow<List<Food>>

    @Query("DELETE FROM foods WHERE favorite_id = :favoriteId")
    suspend fun  deleteFoodByFavoriteId(favoriteId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: Food)

    @Query("DELETE FROM foods WHERE food_id = :foodId AND favorite_id = :favoriteId")
    suspend fun delete(foodId: Int, favoriteId: Long)

}