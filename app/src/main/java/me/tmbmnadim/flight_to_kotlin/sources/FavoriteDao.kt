package me.tmbmnadim.flight_to_kotlin.sources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.tmbmnadim.flight_to_kotlin.models.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<Favorite>

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE departure_code = :dep AND destination_code = :dest")
    suspend fun deleteFavorite(dep: String, dest: String)
}