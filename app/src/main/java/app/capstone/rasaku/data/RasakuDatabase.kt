package app.capstone.rasaku.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.capstone.rasaku.model.Favorite
import app.capstone.rasaku.model.FavoriteDao
import app.capstone.rasaku.model.Food
import app.capstone.rasaku.model.FoodDao

@Database(entities = [Food::class, Favorite::class], version = 1, exportSchema = false)
abstract class RasakuDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: RasakuDatabase? = null

        fun getInstance(context: Context): RasakuDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RasakuDatabase::class.java,
                    "rasaku.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}