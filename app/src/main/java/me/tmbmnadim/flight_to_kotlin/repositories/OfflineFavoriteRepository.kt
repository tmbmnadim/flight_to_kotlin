package me.tmbmnadim.flight_to_kotlin.repositories

import kotlinx.coroutines.flow.Flow
import me.tmbmnadim.flight_to_kotlin.models.Favorite
import me.tmbmnadim.flight_to_kotlin.sources.FavoriteDao

class OfflineFavoriteRepository(private val favoriteDao: FavoriteDao) : FavoriteRepository {
    override fun getFavorites(): Flow<Favorite> =
        favoriteDao.getAllFavorites()

    override suspend fun addFavorite(favorite: Favorite) =
        favoriteDao.insertFavorite(favorite)

    override suspend fun removeFavorite(dep: String, dest: String) =
        favoriteDao.deleteFavorite(dep, dest)
}