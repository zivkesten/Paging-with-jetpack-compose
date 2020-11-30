package com.zk.composeinfinitepaging

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zk.composeinfinitepaging.di.MovieDIGraph
import com.zk.composeinfinitepaging.networking.MovieListResponse
import com.zk.composeinfinitepaging.ui.components.PagedMoviesLaneItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun MovieScreen() {
    val statusBarHeight = 32.dp
    val listOfSections = listOf(
        "Trending this week",
        "Popular this week",
        "Top rated movies",
        "Trending TV shows",
        "Top rated TV shows",
    )
    ScrollableColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(statusBarHeight))

        listOfSections.forEach {
            DynamicSection(it)
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun DynamicSection(type: String) {

    // We need the service injected here to get the interface lambdas
    val service = MovieDIGraph.pagedMovieApi

    /*
        This will be the interface function of the api
        It takes a page number and returns a response
     */
    var dataSource: (suspend (Int) -> MovieListResponse)? = null

    when (type) {
        "Trending this week" -> {
            dataSource = service::getPagedTrendingMovies
        }
        "Popular this week" -> {
            dataSource = service::getPagedPopularMovies
        }
        "Trending TV shows" -> {
            dataSource = service::getPagedTrendingTvShows
        }
        "Top rated movies" -> {
            dataSource = service::getPagedTopRatedMovies
        }
        "Top rated TV shows" -> {
            dataSource = service::getPagedTopRatedTvShows
        }
    }
        PagedMoviesLaneItem(dataSource = dataSource, title = type)
}
