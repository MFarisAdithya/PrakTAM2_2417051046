package model

import androidx.annotation.DrawableRes

data class Fitness(
    val nama: String,
    val deskripsi: String,
    val durasi: String,
    @param:DrawableRes val imageRes: Int
)