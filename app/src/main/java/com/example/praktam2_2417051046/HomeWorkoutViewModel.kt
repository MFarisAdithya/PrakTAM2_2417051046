package com.example.praktam2_2417051046

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktam2_2417051046.data.model.Fitness
import com.example.praktam2_2417051046.data.model.GistResponse
import com.example.praktam2_2417051046.data.repository.FitnessRepository
import com.example.praktam2_2417051046.data.repository.UserPreferencesRepository
import com.example.praktam2_2417051046.data.repository.UserProfile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeWorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val userPrefsRepository = UserPreferencesRepository(application)
    private val fitnessRepository = FitnessRepository()

    // Favorites
    var favorites = mutableStateListOf<String>()
        private set

    // Selected category for filtering
    var selectedCategory by mutableStateOf<String?>(null)
        private set

    // Search query
    var searchQuery by mutableStateOf("")
        private set

    // User profile
    var userProfile by mutableStateOf(UserProfile())
        private set

    // API data
    var gistData by mutableStateOf<GistResponse?>(null)
        private set
    var isLoading by mutableStateOf(true)
        private set
    var isError by mutableStateOf(false)
        private set

    init {
        loadFavorites()
        loadUserProfile()
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            isLoading = true
            isError = false
            val response = fitnessRepository.getLatihan()
            if (response != null) {
                gistData = response
                isError = false
            } else {
                isError = true
            }
            isLoading = false
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            val savedFavorites = userPrefsRepository.favoritesFlow.first()
            favorites.clear()
            favorites.addAll(savedFavorites)
        }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            userProfile = userPrefsRepository.userProfileFlow.first()
        }
    }

    fun toggleFavorite(fitnessName: String) {
        if (favorites.contains(fitnessName)) {
            favorites.remove(fitnessName)
        } else {
            favorites.add(fitnessName)
        }
        viewModelScope.launch {
            userPrefsRepository.saveFavorites(favorites.toSet())
        }
    }

    fun isFavorite(fitnessName: String): Boolean {
        return favorites.contains(fitnessName)
    }

    fun selectCategory(category: String?) {
        selectedCategory = if (selectedCategory == category) null else category
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun saveUserProfile(profile: UserProfile) {
        userProfile = profile
        viewModelScope.launch {
            userPrefsRepository.saveUserProfile(profile)
        }
    }
}
