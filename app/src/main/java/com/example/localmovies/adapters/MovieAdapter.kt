package com.example.localmovies.adapters

import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.localmovies.R
import com.example.localmovies.datasource.Movie
import java.net.URL

class MovieAdapter(private val onClick: (movie: Movie) -> Unit): ListAdapter<Movie, MovieVH>(MovieComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieVH(view)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bind(getItem(position), onClick)
    }

}
class MovieVH(itemView: View): RecyclerView.ViewHolder(itemView) {
    val poster = itemView.findViewById<ImageView>(R.id.movie_poster)
    val title = itemView.findViewById<TextView>(R.id.movie_title)
    val genre = itemView.findViewById<TextView>(R.id.movie_genre)
    val avail = itemView.findViewById<TextView>(R.id.movie_avail)

    fun bind(movie: Movie, onClick: (movie: Movie) -> Unit) {
        title.text = movie.title
        genre.text = movie.genre
        avail.text = movie.availability
        itemView.setOnClickListener {
            onClick(movie)
        }
    }

}
class MovieComparator: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem._id == newItem._id
    }

}