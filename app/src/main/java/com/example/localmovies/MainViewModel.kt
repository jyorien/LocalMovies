package com.example.localmovies

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.localmovies.datasource.Comment
import com.example.localmovies.datasource.Movie
import com.example.localmovies.datasource.Repository
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {
    private val client = Repository.getClient()

    fun getMovies(callback: (movies: List<Movie>) -> Unit) {
        client.getMovies().enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                response.body()?.let { movies ->
                    callback(movies)
                }

            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Log.d("hello", "hello ${t.message}")
            }

        })
    }

    fun getComments(callback: (comments: List<Comment>) -> Unit) {
        client.getComments().enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                response.body()?.let { comments ->
                    callback(comments)
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d("hello", "hello ${t.message}")
            }

        })
    }

    fun addComment(username: String, body: String, rating: Int, movieId: Int, movieName: String, callback: () -> Unit) {
        val json = JSONObject()
        json.put("movieId", movieId)
        json.put("movie", movieName)
        json.put("username", username)
        json.put("review", body)
        json.put("rating", rating)
        Log.d("hello", "request ${json}")

        val request = RequestBody.create(MediaType.parse("application/json"), json.toString())

        client.addComment(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("hello",response.message())
                Log.d("hello","${response.raw()}")
                callback()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }

        })
    }

    fun deleteComment(id: Int, callback: ()-> Unit) {
        client.deleteComment(id).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("hello",response.message())
                Log.d("hello","${response.raw()}")
                callback()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("hello", t.message.toString())
            }

        })

    }

    fun updateComment(id: Int, body: String, rating: Int, callback: () -> Unit) {
        val json = JSONObject()
        json.put("review", body)
        json.put("rating", rating)
        val mediaType = MediaType.parse("application/json")
        val request = RequestBody.create(mediaType, json.toString())
        client.updateComment(id, request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                callback()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("hello",t.message.toString())
            }

        })
    }
}