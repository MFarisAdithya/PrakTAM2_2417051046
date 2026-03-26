package model

import com.example.praktam2_2417051046.R

object LatihanData {

    val kategoriLatihan = listOf(
        Fitness("Perut", "Latihan fokus pada otot perut", "15 Menit", R.drawable.perut),
        Fitness("Dada", "Latihan otot dada", "12 Menit", R.drawable.pushup),
        Fitness("Lengan", "Latihan otot lengan", "10 Menit", R.drawable.pullup),
        Fitness("Bahu & Punggung", "Latihan punggung", "15 Menit", R.drawable.plank),
        Fitness("Kaki", "Latihan kaki", "15 Menit", R.drawable.squat)
    )

    val daftarLatihan = listOf(
        Fitness("Sit Up", "Melatih otot perut", "15x", R.drawable.situp),
        Fitness("Plank", "Melatih core", "30 detik", R.drawable.plank),
        Fitness("Crunch", "Latihan perut atas", "12x", R.drawable.perut),

        Fitness("Push Up", "Melatih dada", "15x", R.drawable.pushup),
        Fitness("Pull Up", "Melatih punggung", "10x", R.drawable.pullup),

        Fitness("Squat", "Melatih kaki", "20x", R.drawable.squat),
        Fitness("Lunges", "Melatih kaki & keseimbangan", "12x", R.drawable.squat)
    )

}