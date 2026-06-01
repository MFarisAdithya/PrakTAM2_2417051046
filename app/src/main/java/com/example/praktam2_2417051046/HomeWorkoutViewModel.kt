package com.example.praktam2_2417051046

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.praktam2_2417051046.data.model.Fitness

class HomeWorkoutViewModel : ViewModel() {
    // List of favorite exercise names
    var favorites = mutableStateListOf<String>()
        private set

    // Selected category for filtering. Null means no filter (show all).
    var selectedCategory by mutableStateOf<String?>(null)
        private set

    fun toggleFavorite(fitnessName: String) {
        if (favorites.contains(fitnessName)) {
            favorites.remove(fitnessName)
        } else {
            favorites.add(fitnessName)
        }
    }

    fun isFavorite(fitnessName: String): Boolean {
        return favorites.contains(fitnessName)
    }

    fun selectCategory(category: String?) {
        selectedCategory = if (selectedCategory == category) null else category
    }
}
