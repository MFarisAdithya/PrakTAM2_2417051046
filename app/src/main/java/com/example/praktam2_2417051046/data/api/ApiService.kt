package com.example.praktam2_2417051046.data.api

import com.example.praktam2_2417051046.data.model.GistResponse
import retrofit2.http.GET

interface ApiService {
    @GET("MFarisAdithya/7b31a156bd9a42fa3a431b3f45cd6db4/raw/")
    suspend fun getLatihan(): GistResponse
}