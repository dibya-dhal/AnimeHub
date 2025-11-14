package com.dibdroid.animeapp

import android.provider.MediaStore
import com.google.gson.annotations.SerializedName

data class AnimeResponse(
    val data : List<Anime>
)

data class Anime (
    @SerializedName("mal_id") val malId : Int,
    val title : String,
    val type : String?,
    val source : String?,
    val score : Double?,
    val images : Images

)

data class Images(
    val jpg : Jpg
)

data class Jpg(
   @SerializedName("image_url") val imageUrl: String
)