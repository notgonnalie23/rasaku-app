package app.capstone.rasaku.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.capstone.rasaku.model.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM histories ORDER BY timestamp DESC")
    fun getHistories(): Flow<List<History>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: History)

    @Query("DELETE FROM histories WHERE id=:id")
    suspend fun delete(id: Int)
}