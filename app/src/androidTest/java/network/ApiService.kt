package network

import model.Fitness
import retrofit2.http.GET

interface ApiService {

    @GET("latihan.json")
    suspend fun getLatihan(): List<Fitness>
}