package com.example.catapp.repository

import com.example.catapp.api.CatApiService
import com.example.catapp.api.RetrofitClient
import com.example.catapp.model.CatImage

class CatRepository(private val apiService: CatApiService = RetrofitClient.apiService) {

    suspend fun getImages(limit: Int = 10): Result<List<CatImage>> {
        return try {
            val images = apiService.getCatImages(limit)
            Result.success(images)
        } catch (e: Exception) {
            // Handle basic errors like network failure or timeouts
            Result.failure(e)
        }
    }
}
