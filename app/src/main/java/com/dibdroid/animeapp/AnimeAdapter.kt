package com.dibdroid.animeapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AnimeAdapter(private val animeList : List<Anime>,
                   private val onItemClick: (Anime) -> Unit,
                   private val onFavoriteClick : (Anime) -> Unit,private var favoriteIds : Set<Int>):
   RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>()   {




        class AnimeViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {



            val imageAnime: ImageView = itemView.findViewById(R.id.imageAnime)
            val title: TextView = itemView.findViewById<TextView>(R.id.textTitle)
            val score: TextView = itemView.findViewById<TextView>(R.id.textScore)
            val heartIcon : ImageView = itemView.findViewById<ImageView>(R.id.favIcon)

        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime,parent,false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = animeList[position]
        holder.title.text = anime.title
        holder.score.text = "⭐${anime.score  ?: "N/A"}"

        Glide.with(holder.itemView.context)
            .load(anime.images.jpg.imageUrl)
            .into(holder.imageAnime)

        val isFav = favoriteIds.contains(anime.malId)
            holder.heartIcon.setImageResource(
                if (isFav) R.drawable.fav_icon else R.drawable.fav2_icon
            )


        holder.itemView.setOnClickListener {
            onItemClick(anime)

        }
        holder.heartIcon.setOnClickListener {
            onFavoriteClick(anime)
        }

    }


    override fun getItemCount() =animeList.size

    fun updateFavIds (ids : Set<Int>){
        favoriteIds = ids
        notifyDataSetChanged()
    }

        }

