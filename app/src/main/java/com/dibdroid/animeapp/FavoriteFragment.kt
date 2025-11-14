package com.dibdroid.animeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavoriteFragment : Fragment() {


    private lateinit var dao: FavoriteAnimeDao
    private lateinit var adapter: FavoriteAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao = AppDatabase.getInstance(requireContext()).favoriteAnimeDao()
        val recyclerView = view.findViewById<RecyclerView>(R.id.favRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = FavoriteAdapter(emptyList()) { anime ->
            lifecycleScope.launch {
                dao.removeFromFavorite(anime)
                Toast.makeText(requireContext(), "Remove From Favorites Anime", Toast.LENGTH_SHORT)
                    .show()
            }

        }


        recyclerView.adapter = adapter

        dao.getAllFavorites().observe(viewLifecycleOwner) { favorites ->
            adapter.updateFavorites(favorites)

        }


    }
}







