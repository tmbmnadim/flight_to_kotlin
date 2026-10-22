package me.tmbmnadim.flight_to_kotlin.repositories

import kotlinx.coroutines.flow.Flow
import me.tmbmnadim.flight_to_kotlin.models.Favorite

interface FavoriteRepository {
    fun getFavorites(): Flow<Favorite>
    suspend fun addFavorite(favorite: Favorite)
    suspend fun removeFavorite(dep: String, dest: String)
}