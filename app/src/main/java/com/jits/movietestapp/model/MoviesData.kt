package com.jits.movietestapp.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

class Movies {
    @JsonProperty("page")
    var page = 0

    @JsonProperty("results")
    var movies: List<Movie>? = null

    @JsonProperty("total_results")
    var totalResults = 0

    @JsonProperty("total_pages")
    var totalPages = 0
}


@JsonIgnoreProperties(ignoreUnknown = true)
    class Movie {
        @JsonProperty("adult")
        var adult = false

        @JsonProperty("backdrop_path")
        var backdropPath: String? = null

        @JsonProperty("belongs_to_collection")
        var belongsToCollection: BelongsToCollection? = null

        @JsonProperty("budget")
        var budget = 0

        @JsonProperty("genres")
        var genres: List<Genre>? = null

        @JsonProperty("homepage")
        var homepage: String? = null

        @JsonProperty("id")
        var id = 0

        @JsonProperty("imdb_id")
        var imdbId: String? = null

        @JsonProperty("original_language")
        var originalLanguage: String? = null

        @JsonProperty("original_title")
        var originalTitle: String? = null

        @JsonProperty("overview")
        var overview: String? = null

        @JsonProperty("popularity")
        var popularity = 0f

        @JsonProperty("poster_path")
        var posterPath: String? = null

        @JsonProperty("production_companies")
        var productionCompanies: List<ProductionCompany>? = null

        @JsonProperty("production_countries")
        var productionCountries: List<ProductionCountry>? = null

        @JsonProperty("release_date")
        var releaseDate: String? = null

        @JsonProperty("revenue")
        var revenue = 0

        @JsonProperty("runtime")
        var runtime = 0

        @JsonProperty("spoken_languages")
        var spokenLanguages: List<SpokenLanguage>? = null

        @JsonProperty("status")
        var status: String? = null

        @JsonProperty("tagline")
        var tagline: String? = null

        @JsonProperty("title")
        var title: String? = null

        @JsonProperty("video")
        var video = false

        @JsonProperty("vote_average")
        var voteAverage = 0f

        @JsonProperty("vote_count")
        var voteCount = 0
    }


class Genre {

    @JsonProperty("id")
    var id = 0

    @JsonProperty("name")
    var name: String? = null

}


class BelongsToCollection {
    @JsonProperty("id")
    var id = 0

    @JsonProperty("name")
    var name: String? = null

    @JsonProperty("poster_path")
    var posterPath: String? = null

    @JsonProperty("backdrop_path")
    var backdropPath: String? = null
}


class ProductionCompany {
    @JsonProperty("name")
    var name: String? = null

    @JsonProperty("id")
    var id = 0

    @JsonProperty("logo_path")
    var logo_path: String? = null

    @JsonProperty("origin_country")
    var originCountry: String? = null
}


class ProductionCountry {
    @JsonProperty("iso_3166_1")
    var iso31661: String? = null

    @JsonProperty("name")
    var name: String? = null
}


class SpokenLanguage {
    @JsonProperty("iso_639_1")
    var iso6391: String? = null

    @JsonProperty("name")
    var name: String? = null

    @JsonProperty("english_name")
    var english_name: String? = null
}


class Images {
    @JsonProperty("base_url")
    var baseUrl: String? = null

    @JsonProperty("secure_base_url")
    var secureBaseUrl: String? = null

    @JsonProperty("backdrop_sizes")
    var backdropSizes: List<String>? = null

    @JsonProperty("logo_sizes")
    var logoSizes: List<String>? = null

    @JsonProperty("poster_sizes")
    var posterSizes: List<String>? = null

    @JsonProperty("profile_sizes")
    var profileSizes: List<String>? = null

    @JsonProperty("still_sizes")
    var stillSizes: List<String>? = null
}

class Configuration {
    @JsonProperty("images")
    var images: Images? = null

    @JsonProperty("change_keys")
    var changeKeys: List<String>? = null
}
