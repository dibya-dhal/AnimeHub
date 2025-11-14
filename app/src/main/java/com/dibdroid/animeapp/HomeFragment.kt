package com.dibdroid.animeapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var animeAdapter: AnimeAdapter
    private val animeList = mutableListOf<Anime>()
    private lateinit var progressBar: ProgressBar
    private lateinit var dao : FavoriteAnimeDao


    private var currentPage = 1
    private var isLoading = false
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        progressBar = view.findViewById<ProgressBar>(R.id.loadingBar)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        dao = AppDatabase.getInstance(requireContext()).favoriteAnimeDao()
        Log.d("HomeFragment", "onCreateView called")


        animeAdapter = AnimeAdapter(animeList, onItemClick = {anime ->

            val action = HomeFragmentDirections
                .actionHomeFragmentToDetailsFragment(anime.malId)
            findNavController().navigate(action)

        },
            onFavoriteClick = { anime ->
                lifecycleScope.launch(Dispatchers.IO) {
                    val isFav = dao.isFavorites(anime.malId)
                    if (isFav) {
                        dao.removeFromFavorite(
                            FavoriteAnime(
                                maId = anime.malId,
                                title = anime.title,
                                imageUrl = anime.images.jpg.imageUrl,
                                score = anime.score

                            )
                        )
                    } else {
                        dao.addToFavorite(
                            FavoriteAnime(
                                maId = anime.malId,
                                title = anime.title,
                                imageUrl = anime.images.jpg.imageUrl,
                                score = anime.score


                            )
                        )
                    }

                    val updateIds = dao.getAllFavoritesNow().map { it.maId }.toSet()
                    lifecycleScope.launch(Dispatchers.Main){
                        animeAdapter.updateFavIds(updateIds)
                    }

                    }
                },
            favoriteIds = setOf()

        )





     recyclerView.adapter = animeAdapter

        fetchTopAnime(currentPage)

       recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
           override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
               super.onScrolled(recyclerView, dx, dy)


               val layoutManager = recyclerView.layoutManager as GridLayoutManager
               val visibleItemCount = layoutManager.childCount
               val totalItemCount = layoutManager.itemCount
               val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()


               if (!isLoading && (visibleItemCount + firstVisibleItemPosition >= totalItemCount ))
                   currentPage++
               fetchTopAnime(currentPage)
           }




       })

        return view
    }

    private fun fetchTopAnime(page: Int) {
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            try {
                val response = RetrofitInstance.api.getTopAnime(page)
                Log.d("HomeFragment","page : $page,API Response size:  ${response.data.size}")


                if (response.data.isNotEmpty()){
                    animeList.addAll(response.data)
                    animeAdapter.notifyDataSetChanged()
                }else{
                    Log.d("HomeFragment","No more anime found")
                }
                isLoading = false


            }catch (e: Exception){
                Log.e("HomeFragment","APi Error : ${e.message}")
                isLoading = false
            }finally {
                progressBar.visibility = View.GONE
            }
        }


    }


}






