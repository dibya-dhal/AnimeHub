package com.dibdroid.animeapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dibdroid.animeapp.R
import com.dibdroid.animeapp.data.api.RetrofitInstance
import kotlinx.coroutines.launch
import kotlin.getValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters



    private var param1: String? = null
    private var param2: String? = null

    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var imageAnime : ImageView
    private lateinit var textTitle : TextView
    private lateinit var textType : TextView
    private lateinit var textSource : TextView
    private lateinit var textScore : TextView
    private lateinit var progressBar : ProgressBar

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
        val view =inflater.inflate(R.layout.fragment_details, container, false)

        imageAnime = view.findViewById<ImageView>(R.id.imagePoster)
        textTitle = view.findViewById<TextView>(R.id.textTitle)
        textType = view.findViewById<TextView>(R.id.textType)
        textSource = view.findViewById<TextView>(R.id.textSource)
        textScore = view.findViewById<TextView>(R.id.textScore)
        progressBar = view.findViewById<ProgressBar>(R.id.progressBar)



        fetchAnime(args.animeId)

        return view


    }

    private fun fetchAnime(animeId : Int){
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            try {
                val response = RetrofitInstance.api.getAnimeDetails(animeId)
                val anime = response.data

                textTitle.text = anime.title
                textType.text = getString(R.string.type_text, anime.type ?: "N/A")
                textSource.text = getString(R.string.source_text, anime.source ?: "N/A")
                textScore.text = getString(
                    R.string.score_text ,
                    getString(R.string.star_symbol),
                    anime.score.toString()
                    )

                Glide.with(requireContext())
                    .load(anime.images.jpg.imageUrl)
                    .into(imageAnime)

            }catch (e: Exception) {
                // Handle error
                Log.e("DetailsFragment", "Error : ${e.message}")
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

            }

