package com.dibdroid.animeapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteAnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(anime: FavoriteAnime)

    @Delete
    suspend fun removeFromFavorite(anime: FavoriteAnime)

    @Query("SELECT * FROM favorite_anime")
     fun getAllFavorites() : androidx.lifecycle.LiveData<List<FavoriteAnime>>

    @Query("SELECT * FROM favorite_anime")
    suspend fun getAllFavoritesNow(): List<FavoriteAnime>


    @Query("SELECT EXISTS(SELECT 1 FROM favorite_anime WHERE maId = :id)")
    suspend fun isFavorites(id: Int): Boolean

}