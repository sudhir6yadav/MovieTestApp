package com.jits.movietestapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.jits.movietestapp.model.*
import com.jits.movietestapp.util.ApiService
import com.jits.movietestapp.util.RepositoryRetriever
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity() {

    var contentView: LinearLayout?=null
    var imageView: ImageView? = null
    var overviewHeader: TextView? = null
    var overviewTextView: TextView? = null
    var genresTextView: TextView? = null
    var durationTextView: TextView? = null
    var languageTextView: TextView? = null
    var tvRevenue: TextView? = null
    var tvBudget: TextView? = null
    var errorView: TextView? = null
    var loadingView: ProgressBar? = null
    private var movieId = -1
    private var movieImage = ""
    private var images: Images? = null
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

         contentView=findViewById(R.id.container)
         imageView=findViewById(R.id.ivMovieBanner)
         overviewHeader=findViewById(R.id.tvOverviewHeader)
         overviewTextView=findViewById(R.id.overviewTextView)
         genresTextView=findViewById(R.id.tvGenres)
         durationTextView=findViewById(R.id.tvDuration)
         languageTextView=findViewById(R.id.tvLanguage)
         tvBudget=findViewById(R.id.tvBudget)
         tvRevenue=findViewById(R.id.tvRevenue)
         errorView=findViewById(R.id.tvNetworkCheck)
         loadingView=findViewById(R.id.progressBar)

        val extras: Bundle? = getIntent().getExtras()
        if (extras != null) {
            movieId = extras.getInt(MOVIE_ID)
            val movieTitle = extras.getString(MOVIE_TITLE)
            movieImage=""+extras.getString(MOVIE_IMAGE)
            setTitle(movieTitle)
        }
         showProgress();
         getMovie(movieId);
    }

    fun showProgress() {
        loadingView!!.visibility = View.VISIBLE
        showDetails(false)
        errorView!!.visibility = View.GONE
    }

    fun showDetails(movie: Movie) {
        Log.d("showContent", "showContent: inside show content")

        if (!movieImage.isEmpty()) {
            Glide.with(this)
                .load(movieImage)
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
        overviewTextView!!.text = getOverview(movie.overview!!)
        genresTextView!!.text = getGenres(movie)
        durationTextView!!.text = getDuration(movie)
        languageTextView!!.text = getLanguages(movie)
        tvBudget!!.text = ""+movie.budget
        tvRevenue!!.text = ""+movie.revenue
        loadingView!!.visibility = View.GONE
        showDetails(true)
        errorView!!.visibility = View.GONE
    }

    private fun getDuration(movie: Movie): String {
        val runtime: Int = movie.runtime
        return if (runtime <= 0) "-" else getResources().getQuantityString(
            R.plurals.time,
            runtime,
            runtime
        )
    }

    private fun getOverview(overview: String): String {
        return if (TextUtils.isEmpty(overview)) "-" else overview
    }


    private fun getGenres(movie: Movie): String {
        var genres = ""
        for (i in 0 until movie.genres!!.size) {
            val genre: Genre = movie!!.genres!!.get(i)
            genres += genre.name.toString() + ", "
        }
        genres = removeComma(genres)
        return if (genres.isEmpty()) "-" else genres
    }

    private fun getLanguages(movie: Movie): String {
        var languages = ""
        for (i in 0 until movie.spokenLanguages!!.size) {
            val language: SpokenLanguage = movie.spokenLanguages!!.get(i)
            languages += language.name.toString() + ", "
        }
        languages = removeComma(languages)
        return if (languages.isEmpty()) "-" else languages
    }


    private fun removeComma(text: String): String {
        var text = text
        text = text.trim { it <= ' ' }
        if (text.endsWith(",")) {
            text = text.substring(0, text.length - 1)
        }
        return text
    }

    fun showError() {
        loadingView!!.visibility = View.GONE
        showDetails(false)
        errorView!!.visibility = View.VISIBLE
    }


    private fun showDetails(show: Boolean) {
        val visibility = if (show) View.VISIBLE else View.INVISIBLE
        contentView!!.visibility = visibility
        overviewHeader!!.visibility = visibility
        overviewTextView!!.visibility = visibility
    }

    private fun getMovie(movieId: Int) {
        val call =   RepositoryRetriever.client().create(ApiService::class.java).getMovieDetail(movieId,getString(R.string.api_key))
        call!!.enqueue(object : Callback<Movie?> {
            override fun onResponse(call: Call<Movie?>, response: Response<Movie?>) {
                Log.d("responsebody", "onResponse: " + response.body())
                if (response.isSuccessful) {
                    showDetails(response.body()!!)
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<Movie?>, t: Throwable) {
                Log.d("responseerror", "onResponse: " + t.message)
                showError()
            }
        })
    }


    companion object {
        const val MOVIE_ID = "movie_id"
        const val MOVIE_TITLE = "movie_title"
        const val MOVIE_IMAGE = "movie_image"
    }
}
