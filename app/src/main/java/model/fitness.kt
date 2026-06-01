package model

import com.google.gson.annotations.SerializedName

data class Fitness(
    val nama: String,
    val deskripsi: String,
    val durasi: String,
    @SerializedName("image_url") val image_url: String
)

data class GistResponse(
    val kategori: List<Fitness>,
    val latihan: List<Fitness>
)