package com.example.movielist

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivityViewModel(val context: Context) : ViewModel() {

    private val movieListLiveData = MutableLiveData<ArrayList<MovieModel>>()
    private val moviesPoseters = "https://image.tmdb.org/t/p/w154/bvYjhsbxOBwpm8xLE5BhdA3a8CZ.jpg"


    fun getMovieListLiveData(): MutableLiveData<ArrayList<MovieModel>> {
        return movieListLiveData
    }

    var searchText = ""
    val movieList = ArrayList<MovieModel>()

    fun getMovieList() {
        val queue = Volley.newRequestQueue(context)
        val url = "http://www.omdbapi.com/?i=tt0944947&Season=1&apikey=665cb4a6"

        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
                Log.d("TAG_movieList", "getMovieList: $response")
                updateList(response)
            }, Response.ErrorListener { error ->
                Log.d("TAG_movieList", "getMovieList: error $error")
            })

        queue.add(stringRequest)
    }

    private fun updateList(response: String?) {
        movieList.clear()
        val mainObj = JSONObject(response)
        val episodesArray = mainObj.getJSONArray("Episodes")
        for (i in 0 until episodesArray.length()) {
            val data = episodesArray[i] as JSONObject
            val movieModel = MovieModel(data.getString("Title"), data.getString("Released"),moviesPoseters)
            movieList.add(movieModel)
        }
        movieListLiveData.value = movieList
    }

}