package com.jits.movietestapp.util

import com.jits.movietestapp.model.Configuration
import com.jits.movietestapp.model.Movie
import com.jits.movietestapp.model.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/3/movie/popular")
    fun getPopularMovies(@Query("page") page: Int, @Query("api_key") api_key: String): Call<Movies?>?

    @GET("/3/movie/{id}")
    fun getMovieDetail(@Path("id") id: Int, @Query("api_key") api_key: String): Call<Movie?>?

    @GET("/3/configuration")
    fun getConfiguration(@Query("api_key") api_key: String): Call<Configuration?>?
}
