package com.zk.composeinfinitepaging.di

import com.zk.composeinfinitepaging.networking.PagedMovieApi

object MovieDIGraph {

    // create paged api interface
    val pagedMovieApi = PagedMovieApi.invoke()

}