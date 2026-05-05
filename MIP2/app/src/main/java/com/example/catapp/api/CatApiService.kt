package com.example.catapp.api

import com.example.catapp.model.CatImage
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("v1/images/search")
    suspend fun getCatImages(
        @Query("limit") limit: Int = 10
    ): List<CatImage>
}
