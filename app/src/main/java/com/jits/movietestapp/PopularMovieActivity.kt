package com.jits.movietestapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jits.movietestapp.adapter.PopularMoviesAdapter
import com.jits.movietestapp.model.Configuration
import com.jits.movietestapp.model.Images
import com.jits.movietestapp.model.Movie
import com.jits.movietestapp.model.Movies
import com.jits.movietestapp.util.ApiService
import com.jits.movietestapp.util.RepositoryRetriever
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularMovieActivity : AppCompatActivity(), PopularMoviesAdapter.ItemClickListener {

    private lateinit var recyclerView : RecyclerView;
    private lateinit var textView : TextView;
    private lateinit var progressBar : ProgressBar;
    private var moviesAdapter: PopularMoviesAdapter? = null
    private var images: Images? = null
    private val page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_movie)

        progressBar=findViewById(R.id.progressBar)
        recyclerView=findViewById(R.id.rvMovieList)
        textView=findViewById(R.id.tvNetworkCheck)

        setupContentView()

    }

    private fun setupContentView() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager)
        showProgress(false)
        getConfiguration()
        getMovies()
    }

    fun showProgress(isRefresh: Boolean) {
        if (!isRefresh) {
            progressBar!!.visibility = View.VISIBLE
            recyclerView.setVisibility(View.GONE)
            textView!!.visibility = View.GONE
        }
    }

    fun showDetails(movies: List<Movie>) {
        if (moviesAdapter == null) {
            moviesAdapter = PopularMoviesAdapter(movies as MutableList<Movie>, this, images, this)
            recyclerView.setAdapter(moviesAdapter)
        } else {
            moviesAdapter!!.addAll(movies)
            moviesAdapter!!.notifyDataSetChanged()
        }

        // Delay SwipeRefreshLayout animation by 1.5 seconds

        progressBar!!.visibility = View.GONE
        recyclerView.setVisibility(View.VISIBLE)
        textView!!.visibility = View.GONE
    }

    fun showError() {
        progressBar!!.visibility = View.GONE
        recyclerView.setVisibility(View.GONE)
        textView!!.visibility = View.VISIBLE
    }

    fun onConfigurationSet(images: Images?) {
        this.images = images
        if (moviesAdapter != null) {
            moviesAdapter!!.setImages(images)
        }
    }

    override fun onItemClick(movieId: Int, movieTitle: String?, fullImageUrl: String) {
        val i = Intent(this, MovieDetailActivity::class.java)
        i.putExtra("movie_title", movieTitle)
        i.putExtra("movie_image", fullImageUrl)
        i.putExtra("movie_id", movieId)
        startActivity(i)
    }


    companion object {
        private const val TAG = "Main"
    }


    private fun getMovies() {
        RepositoryRetriever.client().create(ApiService::class.java).getPopularMovies(page, getString(R.string.api_key))!!.enqueue(object : Callback<Movies?> {

            override fun onFailure(call: Call<Movies?>?, t: Throwable) {
                showError()
                Log.d("showerror", "errror" + t.message)
            }

            override fun onResponse(call: Call<Movies?>, response: Response<Movies?>) {
                Log.d("showerror", "succcess" + response.body())
                if (response.isSuccessful()) {
                    showDetails(response.body()!!.movies!!)
                } else {
                    showError()
                }
            }
        })
    }

    private fun getConfiguration() {

        RepositoryRetriever.client().create(ApiService::class.java).getConfiguration(getString(R.string.api_key))!!.enqueue(object : Callback<Configuration?> {
            override fun onResponse(call: Call<Configuration?>, response: Response<Configuration?>) {
                if (response.isSuccessful()) {
                    onConfigurationSet(response.body()!!.images)
                }
            }

            override fun onFailure(call: Call<Configuration?>, t: Throwable) {}
        })
    }

}
