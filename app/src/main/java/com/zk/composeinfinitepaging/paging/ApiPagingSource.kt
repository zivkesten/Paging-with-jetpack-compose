package com.zk.composeinfinitepaging.paging

import androidx.paging.PagingSource
import com.zk.composeinfinitepaging.networking.MovieListResponse
import com.zk.composeinfinitepaging.model.Movie
import retrofit2.HttpException
import java.io.IOException

private const val TV_SHOWS_STARTING_PAGE_INDEX = 1


class ApiPagingSource(
    private val pagedDataFetcher: suspend (Int) -> MovieListResponse,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: TV_SHOWS_STARTING_PAGE_INDEX
        return try {
            val response = pagedDataFetcher(position)
            val shows = response.movies
            LoadResult.Page(
                data = shows,
                prevKey = if (position == TV_SHOWS_STARTING_PAGE_INDEX) null else position,
                nextKey = if (shows.isEmpty()) null else position + 1

            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}