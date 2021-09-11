package com.example.localmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.localmovies.adapters.MovieAdapter
import com.example.localmovies.databinding.ActivityMainBinding
import com.example.localmovies.datasource.Movie
import com.example.localmovies.datasource.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
const val MOVIE_ID = "movieId"
const val MOVIE_NAME = "movieName"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getMovies {
            Log.d("hello","hi $it")
            binding.movieList.adapter = MovieAdapter {
                Intent(this, CommentActivity::class.java).also { intent ->
                    intent.putExtra(MOVIE_ID, it._id)
                    intent.putExtra(MOVIE_NAME, it.title)
                    startActivity(intent)
                }

            }
            (binding.movieList.adapter as MovieAdapter).submitList(it)
        }

        viewModel.getComments {
            Log.d("hello","comment $it")
        }
    }

}