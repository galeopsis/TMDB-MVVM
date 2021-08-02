package com.galeopsis.mvvmtmdbmoviefinder.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.galeopsis.mvvmtmdbmoviefinder.R
import com.galeopsis.mvvmtmdbmoviefinder.app.di.IMAGE_BASE_URL
import com.galeopsis.mvvmtmdbmoviefinder.model.entity.Movies

class RecycleViewAdapter(
    private val moviesList: ArrayList<Movies>
) :
    RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movies: Movies = moviesList[position]
        val posterPath = IMAGE_BASE_URL + movies.poster_path

        Glide.with(holder.searchFragmentPoster.context)
            .load(posterPath)
            .into(holder.searchFragmentPoster)

        holder.searchFragmentTitle.text = movies.title
        holder.searchFragmentReleaseDate.text = movies.release_date
        holder.searchFragmentRating.text = movies.vote_average.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchFragmentTitle = itemView.findViewById(R.id.searchFragmentTitle) as TextView
        val searchFragmentReleaseDate =
            itemView.findViewById(R.id.searchFragmentReleaseDate) as TextView
        val searchFragmentRating = itemView.findViewById(R.id.searchFragmentMovieRating) as TextView
        val searchFragmentPoster = itemView.findViewById(R.id.searchFragmentPoster) as ImageView
    }
}