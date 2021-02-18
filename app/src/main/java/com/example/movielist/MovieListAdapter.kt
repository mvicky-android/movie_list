package com.example.movielist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieListAdapter(
    private val movieList: ArrayList<MovieModel>,
    private val context: Context
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item_movie, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.releasedText.text = movieList[position].Released
        holder.titleText.text = movieList[position].Title
        Glide.with(context).load(movieList[position].image).into(holder.imageView)
        holder.setIsRecyclable(false)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView_picture)
        val titleText = itemView.findViewById<TextView>(R.id.textView_title)
        val releasedText = itemView.findViewById<TextView>(R.id.textView_released)
    }
}