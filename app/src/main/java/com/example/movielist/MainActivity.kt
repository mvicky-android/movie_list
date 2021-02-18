package com.example.movielist

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var movieListAdapter: MovieListAdapter;
    private var movieList = ArrayList<MovieModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityViewModel = ViewModelProvider(
            this,
            MainActivityViewModelFactory(this)
        ).get(MainActivityViewModel::class.java)

        recyclerViewMovies = findViewById(R.id.recyclerView_movies)
        setAdapter()
        mainActivityViewModel.getMovieList()

        mainActivityViewModel.getMovieListLiveData().observe(this, Observer {
            Log.d("TAG_observer", "onCreate: ${it.size}")
            movieList = it
//            listAdapter.notifyDataSetChanged()
            setAdapter()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchView: SearchView
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                try {
                    searchView = menu.findItem(R.id.search).actionView as SearchView
                    searchView.apply {
                        setSearchableInfo(searchManager.getSearchableInfo(componentName))
                    }
                    searchView.isIconifiedByDefault = true;
                    searchView.isFocusable = true;
                    searchView.isIconified = false;
                    searchView.requestFocusFromTouch()
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            setFilteredList(p0)
                            return false
                        }

                        override fun onQueryTextChange(p0: String?): Boolean {
                            setFilteredList(p0)
                            return false
                        }

                    })
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, 500)

        return true
    }

    private fun setFilteredList(query: String?) {
        Log.d("TAG_filter ", "setFilteredList: $query")
        if (null != query) {
            val tempMovieList = ArrayList<MovieModel>()
            mainActivityViewModel.getMovieListLiveData().value!!.forEach { movieModel ->
                Log.d("TAG_filter", "setFilteredList: ${movieModel.Title}")
                if (movieModel.Title.toLowerCase().contains(query.toLowerCase(),false)) {
                    tempMovieList.add(movieModel)
                }
            }
            movieList = tempMovieList
            setAdapter()
        }

    }


    private fun setAdapter() {
        movieListAdapter = MovieListAdapter(movieList, this)
        recyclerViewMovies.adapter = movieListAdapter
        recyclerViewMovies.layoutManager = LinearLayoutManager(this)
    }

}