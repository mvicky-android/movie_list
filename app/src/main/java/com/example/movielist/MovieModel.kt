package com.example.movielist

data class MoviesResponse(val Episodes : List<MovieModel>)
data class MovieModel(val Title: String, val Released: String, val image: String)