package com.example.praktam2_2417051046.data.repository

import com.example.praktam2_2417051046.data.api.RetrofitClient
import com.example.praktam2_2417051046.data.model.GistResponse

class FitnessRepository {
    suspend fun getLatihan(): GistResponse? {
        return try {
            RetrofitClient.instance.getLatihan()
        } catch (e: Exception) {
            null
        }
    }
}
