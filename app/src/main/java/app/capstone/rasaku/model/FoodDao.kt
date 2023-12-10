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
    suspend fun  getFoodByFavoriteId(favoriteId: Long): List<Food>

    @Update
    suspend fun  updateFood(food: Food)
}