package com.example.praktam2_2417051046.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

data class UserProfile(
    val nama: String = "",
    val umur: Int = 0,
    val jenisKelamin: String = "",
    val beratBadan: Float = 0f,
    val tinggiBadan: Float = 0f
)

class UserPreferencesRepository(private val context: Context) {

    companion object {
        val NAMA_KEY = stringPreferencesKey("nama")
        val UMUR_KEY = intPreferencesKey("umur")
        val JENIS_KELAMIN_KEY = stringPreferencesKey("jenis_kelamin")
        val BERAT_BADAN_KEY = floatPreferencesKey("berat_badan")
        val TINGGI_BADAN_KEY = floatPreferencesKey("tinggi_badan")
        val FAVORITES_KEY = stringSetPreferencesKey("favorites")
    }

    val userProfileFlow: Flow<UserProfile> = context.dataStore.data.map { preferences ->
        UserProfile(
            nama = preferences[NAMA_KEY] ?: "",
            umur = preferences[UMUR_KEY] ?: 0,
            jenisKelamin = preferences[JENIS_KELAMIN_KEY] ?: "",
            beratBadan = preferences[BERAT_BADAN_KEY] ?: 0f,
            tinggiBadan = preferences[TINGGI_BADAN_KEY] ?: 0f
        )
    }

    val favoritesFlow: Flow<Set<String>> = context.dataStore.data.map { preferences ->
        preferences[FAVORITES_KEY] ?: emptySet()
    }

    suspend fun saveUserProfile(profile: UserProfile) {
        context.dataStore.edit { preferences ->
            preferences[NAMA_KEY] = profile.nama
            preferences[UMUR_KEY] = profile.umur
            preferences[JENIS_KELAMIN_KEY] = profile.jenisKelamin
            preferences[BERAT_BADAN_KEY] = profile.beratBadan
            preferences[TINGGI_BADAN_KEY] = profile.tinggiBadan
        }
    }

    suspend fun saveFavorites(favorites: Set<String>) {
        context.dataStore.edit { preferences ->
            preferences[FAVORITES_KEY] = favorites
        }
    }
}
