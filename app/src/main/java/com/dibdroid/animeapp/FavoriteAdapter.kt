package com.dibdroid.animeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class FavoriteAdapter(private var favorites : List<FavoriteAnime>, private val onRemoveClick : (FavoriteAnime) -> Unit ) :
   RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>()    {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_anime,parent,false)
        return FavViewHolder(view)

    }

    class FavViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val favImage : ImageView = itemView.findViewById<ImageView>(R.id.favImage)
        val favTitle : TextView = itemView.findViewById<TextView>(R.id.favTitle)
       val favScore : TextView = itemView.findViewById<TextView>(R.id.favScore)
        val favHeart : ImageView  = itemView.findViewById<ImageView>(R.id.favHeart)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val anime = favorites[position]
        holder.favTitle.text = anime.title
        holder.favScore.text = "⭐${anime.score  ?: "N/A"}"


        Glide.with(holder.itemView.context)
            .load(anime.imageUrl)
            .into(holder.favImage)

        holder.favHeart.setOnClickListener { onRemoveClick(anime) }
    }

    override fun getItemCount() = favorites.size

    fun updateFavorites(newList: List<FavoriteAnime>){
        favorites = newList
        notifyDataSetChanged()
    }

}