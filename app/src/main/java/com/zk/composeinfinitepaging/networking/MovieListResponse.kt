package com.zk.composeinfinitepaging.networking

import com.google.gson.annotations.SerializedName
import com.zk.composeinfinitepaging.model.Movie


data class MovieListResponse(
    @SerializedName("pages") val pages: Int,
    @SerializedName("results") val movies: List<Movie>
)