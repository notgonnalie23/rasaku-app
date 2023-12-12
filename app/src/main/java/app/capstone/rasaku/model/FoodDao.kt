package app.capstone.rasaku.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
     fun getFoods(): List<Food>

    @Query("SELECT * FROM food WHERE id = :id")
     fun  getFoodById(id: Long): Food

    @Query("SELECT * FROM food WHERE favorite_id = :favoriteId")
    fun  getFoodByFavoriteId(favoriteId: Long): List<Food>

    @Query("DELETE FROM food WHERE favorite_id = :favoriteId")
    suspend fun  deleteFoodByFavoriteId(favoriteId: Long)

    @Update
    suspend fun  updateFood(food: Food)
}