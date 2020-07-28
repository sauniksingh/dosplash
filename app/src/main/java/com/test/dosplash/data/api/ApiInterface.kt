package com.test.dosplash.data.api

import com.test.dosplash.model.UnsplashImage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
interface ApiInterface {
    @GET("photos")
    fun getImages(
        @Query("client_id") clientId: String?,
        @Query("page") page: Int,
        @Query("per_page") item: Int
    ): Call<ArrayList<UnsplashImage?>?>?

    @GET("photos/random?")
    fun getRandomImage(@Query("client_id") clientId: String): Call<UnsplashImage>
}