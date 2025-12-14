package com.dibdroid.animeapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_anime")

data class FavoriteAnime(
    @PrimaryKey val maId  : Int,
    val title : String,
    val imageUrl : String,
    val score : Double?



)