package com.jits.movietestapp.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.jits.movietestapp.PopularMovieActivity
import com.jits.movietestapp.R
import com.jits.movietestapp.model.Images
import com.jits.movietestapp.model.Movie
import java.text.DecimalFormat

class PopularMoviesAdapter(
    movies: MutableList<Movie>,
    activity: Activity,
    images: Images?,
    itemClickListener: PopularMovieActivity
) :
    RecyclerView.Adapter<PopularMoviesAdapter.ViewHolder?>() {
    private val movies: MutableList<Movie>
    private val activity: Activity
    private var images: Images?
    private val itemClickListener: ItemClickListener
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_item, parent, false)
        return PopularMoviesAdapter.ViewHolder(itemView)
    }
    override fun onBindViewHolder(
        holder: PopularMoviesAdapter.ViewHolder,
        position: Int
    ) {
        val movie = movies[position]
        Log.d("onBindViewHolderbefore", "onBindViewHolder: ")

        val fullImageUrl = getFullImageUrl(movie)
        Log.d("onBindViewHolderafter", "onBindViewHolder: "+fullImageUrl)
        if (!fullImageUrl!!.isEmpty()) {
            Glide.with(activity)
                .load(fullImageUrl)
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView)
        }
        val popularity = getPopularityString(movie.popularity)
        holder.popularityTextView!!.setText(popularity)
        holder.tvReleaseDat!!.setText(movie.releaseDate)
        holder.titleTextView!!.setText(movie.title)
        holder.cvItemClick.setOnClickListener(View.OnClickListener {
            itemClickListener.onItemClick(
                movie.id,
                movie.title,
                fullImageUrl
            )
        })
    }


    private fun getFullImageUrl(movie: Movie): String? {
        val imagePath: String?
        imagePath = if (movie.posterPath != null && !movie.posterPath!!.isEmpty()) {
            movie.posterPath
        } else {
            movie.backdropPath
        }

        Log.d("getFullImageUrl", "onBindViewHolder: "+movie.posterPath)


        if (images != null && images!!.baseUrl != null && !images!!.baseUrl!!.isEmpty()) {
            if (images!!.posterSizes != null) {
                return if (images!!.posterSizes!!.size > 4) {
                    // usually equal to 'w500'
                    images!!.baseUrl + images!!.posterSizes!![4] + imagePath
                } else {
                    // back-off to hard-coded value
                    images!!.baseUrl.toString() + "w500" + imagePath
                }
            }
        }
        return ""
    }


    override fun getItemCount(): Int {
        return movies.size
    }

    fun clear() {
        movies.clear()
    }

    fun addAll(movies: List<Movie>?) {
        this.movies.addAll(movies!!)
    }

    fun setImages(images: Images?) {
        this.images = images
    }

     class ViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView=itemView.findViewById<ImageView>(R.id.ivMovieBanner)
        var popularityTextView=itemView.findViewById<TextView>(R.id.tvPopularityText)
        var titleTextView=itemView.findViewById<TextView>(R.id.tvMovieTitle)
        var tvReleaseDat=itemView.findViewById<TextView>(R.id.tvReleaseDat)
        var cvItemClick=itemView.findViewById<CardView>(R.id.cvItemClick)


    }

    private fun getPopularityString(popularity: Float): String {
        val decimalFormat = DecimalFormat("#.#")
        return decimalFormat.format(popularity.toDouble())
    }

    interface ItemClickListener {
        fun onItemClick(movieId: Int, title: String?, fullImageUrl: String)
    }

    init {
        this.movies = movies
        this.activity = activity
        this.images = images
        this.itemClickListener = itemClickListener
    }
}
