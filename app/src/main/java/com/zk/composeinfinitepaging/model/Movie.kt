package com.zk.composeinfinitepaging.model

import java.io.Serializable

data class Movie(
    val id: Long,
    var title: String,
    val name: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String?,
    val vote_average: Double?,
    val genre_ids: List<Int>?,
    val overview: String,
    val adult: Boolean,
    var tagline: String?,
    val budget: Double?,
    val revenue: Double?,
    val runtime: Int?,
    val homepage: String?,
    val status: String?,
    var addedTime: Long?,
    var dominantRgb: Int = 0
) : Serializable