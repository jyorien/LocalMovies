package com.example.localmovies.datasource

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MoviesClient {
    @GET("/movies")
    fun getMovies(): Call<List<Movie>>

    @GET("/comments")
    fun getComments(): Call<List<Comment>>

    @POST("/comments")
    fun addComment(@Body requestBody: RequestBody): Call<ResponseBody>

    @DELETE("/comments/{commentId}")
    fun deleteComment(@Path("commentId") commentId: Int): Call<ResponseBody>

    @PUT("/comments/{commentId}")
    fun updateComment(@Path("commentId") commentId: Int, @Body requestBody: RequestBody): Call<ResponseBody>
}