package com.zk.composeinfinitepaging.networking

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface PagedMovieApi {

    @GET("trending/movie/week")
    suspend fun getPagedTrendingMovies(@Query("page") page: Int): MovieListResponse

    @GET("movie/popular")
    suspend fun getPagedPopularMovies(@Query("page") page: Int): MovieListResponse

    @GET("movie/top_rated")
    suspend fun getPagedTopRatedMovies(@Query("page") page: Int): MovieListResponse

    @GET("tv/top_rated")
    suspend fun getPagedTopRatedTvShows(@Query("page") page: Int): MovieListResponse

    @GET("trending/tv/week")
    suspend fun getPagedTrendingTvShows(@Query("page") page: Int): MovieListResponse

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        operator fun invoke(): PagedMovieApi {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request().url
                    .newBuilder()
                    .addQueryParameter("api_key", "78bc7c1c0a813f1e31044d2cc11c7b85")
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PagedMovieApi::class.java)
        }
    }
}