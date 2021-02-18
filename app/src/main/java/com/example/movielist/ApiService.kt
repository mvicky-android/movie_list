package com.example.movielist

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("?apikey=665cb4a6")
    fun getMoviesList(
//        @Query("apikey") apiKey: String,
        @Query("i") i: String,
        @Query("s") searchText: String
    ): Call<List<MovieModel>>
}
