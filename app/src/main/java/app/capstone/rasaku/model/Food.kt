package app.capstone.rasaku.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "foods", primaryKeys = ["food_id", "favorite_id"])
data class Food(
    @ColumnInfo(name = "food_id") val foodId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "favorite_id") val favoriteId: Long,
)
