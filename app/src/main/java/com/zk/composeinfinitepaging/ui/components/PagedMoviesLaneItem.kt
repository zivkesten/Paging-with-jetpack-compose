package com.zk.composeinfinitepaging.ui.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zk.composeinfinitepaging.model.Movie
import com.zk.composeinfinitepaging.networking.MovieListResponse
import com.zk.composeinfinitepaging.paging.ApiPagingSource
import com.zk.composeinfinitepaging.themeing.typography
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun PagedMoviesLaneItem(
    dataSource: (suspend (Int) -> MovieListResponse)?,
    title: String
) {
    dataSource?.let {

        // Generate the pagingSource and the pager and remember them
        val pagingSource = remember { ApiPagingSource(it) }
        val pager = remember {
            Pager(
                PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = true,
                )
            ) {
                pagingSource
            }
        }

        // This is our paged list
        val lazyPagingItems: LazyPagingItems<Movie> = pager.flow.collectAsLazyPagingItems()

        Column {
            RowTitle(title)
            PagingRow(lazyPagingItems)
        }
    }
}

@Composable
private fun RowTitle(title: String) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            style = typography.h6,
            modifier = Modifier.padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 24.dp)
        )
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
private fun PagingRow(
    lazyPagingItems: LazyPagingItems<Movie>
) {
    val context = ContextAmbient.current
    LazyRow {
        // We take care of the loading state
        // here we can also take care of the error state
        // by checking for LoadState.Error
        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                CircularProgressIndicator()
            }
        }
        items(lazyPagingItems) { movie ->
            if (movie != null) {
                CoilImage(
                    data = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                    modifier = Modifier
                        .preferredWidth(190.dp)
                        .preferredHeight(300.dp)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = { Toast.makeText(context, "Tapped ${movie.name}", Toast.LENGTH_LONG).show() }),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = "Error, no movie found",
                    color = Color.Red,
                    style = TextStyle.Default
                )
            }
        }

        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }
    }
}