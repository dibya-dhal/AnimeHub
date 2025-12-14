package com.dibdroid.animeapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dibdroid.animeapp.data.db.FavoriteAnime
import com.dibdroid.animeapp.data.db.FavoriteAnimeDao

@Database(entities = [FavoriteAnime::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteAnimeDao() : FavoriteAnimeDao


    companion object {

        @Volatile  private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
               Room.databaseBuilder(context.applicationContext,
                   AppDatabase::class.java,
                   "anime_db"

               ).build().also { INSTANCE = it }

            }
        }

    }


}