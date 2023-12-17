package app.capstone.rasaku.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.capstone.rasaku.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorites WHERE id = :id")
    fun getFavoriteById(id: Long): Flow<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteFavoriteById(id: Long)

    @Query("UPDATE favorites SET food_count = food_count+:status WHERE id = :id")
    suspend fun updateFoodCount(id: Long, status: Int)

    @Query("UPDATE favorites SET image_url = :imageUrl WHERE id = :id")
    suspend fun setImageUrl(imageUrl: String, id: Long)
}