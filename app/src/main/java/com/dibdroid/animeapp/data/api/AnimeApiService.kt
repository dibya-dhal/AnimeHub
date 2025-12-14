package com.dibdroid.animeapp.data.api

import com.dibdroid.animeapp.data.model.Anime
import com.dibdroid.animeapp.data.model.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApiService {

    @GET("top/anime")
    suspend fun getTopAnime(@Query("page")page: Int) : AnimeResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetails(@Path("id")animeId : Int) : SingleAnimeResponse


    data class SingleAnimeResponse(
        val data : Anime
    )

}