package com.jits.movietestapp.util

import com.jits.movietestapp.R
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RepositoryRetriever {

    val BASE_URL = "https://api.themoviedb.org/"

    fun client(): Retrofit
    {
        return  Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build()
    }
}
