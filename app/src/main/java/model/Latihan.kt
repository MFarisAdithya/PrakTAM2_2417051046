package model

import com.example.praktam2_2417051046.R

object LatihanData {

    val daftarLatihan = listOf(
        Fitness(
            nama = "Perut",
            deskripsi = "Latihan fokus pada otot perut",
            durasi = "15 Menit",
            imageRes = R.drawable.perut
        ),
        Fitness(
            nama = "Dada",
            deskripsi = "Latihan untuk memperkuat otot dada",
            durasi = "12 Menit",
            imageRes = R.drawable.pushup
        ),
        Fitness(
            nama = "Lengan",
            deskripsi = "Latihan untuk memperkuat otot lengan",
            durasi = "10 Menit",
            imageRes = R.drawable.pullup
        ),
        Fitness(
            nama = "Bahu & Punggung",
            deskripsi = "Latihan untuk bahu dan punggung",
            durasi = "15 Menit",
            imageRes = R.drawable.plank
        ),
        Fitness(
            nama = "Kaki",
            deskripsi = "Latihan untuk memperkuat otot kaki",
            durasi = "15 Menit",
            imageRes = R.drawable.squat
        )
    )

}