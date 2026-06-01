package com.example.praktam2_2417051046

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import android.speech.tts.TextToSpeech
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.praktam2_2417051046.ui.theme.PrakTAM2_2417051046Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.praktam2_2417051046.data.model.Fitness
import com.example.praktam2_2417051046.data.model.GistResponse
import com.example.praktam2_2417051046.data.model.LatihanData
import com.example.praktam2_2417051046.data.repository.FitnessRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PrakTAM2_2417051046Theme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

enum class WorkoutTab {
    HOME, FAVORITE, PROFILE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: HomeWorkoutViewModel,
    kategori: List<Fitness>,
    listLatihan: List<Fitness>
) {
    var selectedTab by remember { mutableStateOf(WorkoutTab.HOME) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (selectedTab) {
                            WorkoutTab.HOME -> "BurnIt 🔥"
                            WorkoutTab.FAVORITE -> "Latihan Favorit ❤️"
                            WorkoutTab.PROFILE -> "Profil & BMI 👤"
                        },
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == WorkoutTab.HOME,
                    onClick = { selectedTab = WorkoutTab.HOME },
                    label = { Text("Home") },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == WorkoutTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = "Home"
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == WorkoutTab.FAVORITE,
                    onClick = { selectedTab = WorkoutTab.FAVORITE },
                    label = { Text("Favorit") },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == WorkoutTab.FAVORITE) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorit"
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == WorkoutTab.PROFILE,
                    onClick = { selectedTab = WorkoutTab.PROFILE },
                    label = { Text("Profil") },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == WorkoutTab.PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                            contentDescription = "Profil"
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (selectedTab) {
                WorkoutTab.HOME -> HomeTab(
                    navController = navController,
                    viewModel = viewModel,
                    kategori = kategori,
                    listLatihan = listLatihan
                )
                WorkoutTab.FAVORITE -> FavoriteTab(
                    navController = navController,
                    viewModel = viewModel,
                    listLatihan = listLatihan
                )
                WorkoutTab.PROFILE -> ProfileTab()
            }
        }
    }
}

// Helper extension to match categories based on descriptions/names
fun Fitness.matchesCategory(category: String): Boolean {
    val desc = this.deskripsi.lowercase()
    val name = this.nama.lowercase()
    return when (category.lowercase()) {
        "perut" -> desc.contains("perut") || desc.contains("core") || name.contains("sit up") || name.contains("plank") || name.contains("crunch")
        "dada" -> desc.contains("dada") || name.contains("push up")
        "lengan" -> desc.contains("lengan")
        "bahu & punggung" -> desc.contains("punggung") || desc.contains("bahu") || name.contains("pull up")
        "kaki" -> desc.contains("kaki") || name.contains("squat") || name.contains("lunges")
        else -> true
    }
}

@Composable
fun HomeTab(
    navController: NavController,
    viewModel: HomeWorkoutViewModel,
    kategori: List<Fitness>,
    listLatihan: List<Fitness>
) {
    // Filter workouts if a category is selected
    val filteredLatihan = remember(viewModel.selectedCategory, listLatihan) {
        val category = viewModel.selectedCategory
        if (category == null) {
            listLatihan
        } else {
            listLatihan.filter { it.matchesCategory(category) }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Kategori Latihan",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(kategori) { itemKategori ->
                    val isSelected = viewModel.selectedCategory == itemKategori.nama
                    Card(
                        modifier = Modifier
                            .clickable { viewModel.selectCategory(itemKategori.nama) }
                            .width(100.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = itemKategori.image_url,
                                contentDescription = itemKategori.nama,
                                placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                                error = painterResource(android.R.drawable.ic_menu_report_image),
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = itemKategori.nama,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (viewModel.selectedCategory != null) "Latihan: ${viewModel.selectedCategory}" else "Semua Latihan",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (viewModel.selectedCategory != null) {
                    Text(
                        text = "Reset",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clickable { viewModel.selectCategory(null) }
                            .padding(4.dp)
                    )
                }
            }
        }

        if (filteredLatihan.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada latihan untuk kategori ini.")
                }
            }
        } else {
            items(filteredLatihan) { latihan ->
                FitnessWorkoutCard(latihan, navController, viewModel)
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FavoriteTab(
    navController: NavController,
    viewModel: HomeWorkoutViewModel,
    listLatihan: List<Fitness>
) {
    val favoriteWorkouts = remember(viewModel.favorites.size, listLatihan) {
        listLatihan.filter { viewModel.isFavorite(it.nama) }
    }

    if (favoriteWorkouts.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "No Favorites",
                modifier = Modifier.size(72.dp),
                tint = Color.Gray.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Belum Ada Favorit",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Daftar latihan yang Anda sukai akan muncul di sini. Ketuk ikon hati pada latihan untuk menambahkannya.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp),
                lineHeight = 20.sp
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(favoriteWorkouts) { latihan ->
                FitnessWorkoutCard(latihan, navController, viewModel)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ProfileTab() {
    var weightInput by remember { mutableStateOf("") }
    var heightInput by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf<Double?>(null) }
    var bmiStatus by remember { mutableStateOf("") }
    var bmiColor by remember { mutableStateOf(Color.Gray) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "M",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "Mahasiswa Praktikum",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "NPM: 2417051046",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = "Kelas: Praktikum TAM",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Kalkulator BMI",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(
                        value = weightInput,
                        onValueChange = { weightInput = it },
                        label = { Text("Berat Badan (kg)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = heightInput,
                        onValueChange = { heightInput = it },
                        label = { Text("Tinggi Badan (cm)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            val w = weightInput.toDoubleOrNull()
                            val hCm = heightInput.toDoubleOrNull()
                            if (w != null && hCm != null && hCm > 0) {
                                val hM = hCm / 100.0
                                val bmi = w / (hM * hM)
                                bmiResult = bmi
                                when {
                                    bmi < 18.5 -> {
                                        bmiStatus = "Kurus (Underweight)"
                                        bmiColor = Color(0xFFFFB300) // Orange/Yellow
                                    }
                                    bmi < 25.0 -> {
                                        bmiStatus = "Normal (Healthy)"
                                        bmiColor = Color(0xFF2E7D32) // Green
                                    }
                                    bmi < 30.0 -> {
                                        bmiStatus = "Kelebihan Berat Badan (Overweight)"
                                        bmiColor = Color(0xFFF57C00) // Orange
                                    }
                                    else -> {
                                        bmiStatus = "Obesitas (Obese)"
                                        bmiColor = Color(0xFFD32F2F) // Red
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Hitung BMI", color = Color.White)
                    }

                    bmiResult?.let { result ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(bmiColor.copy(alpha = 0.1f))
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "BMI Anda: %.1f".format(result),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = bmiColor
                                )
                                Text(
                                    text = bmiStatus,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = bmiColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FitnessWorkoutCard(
    latihan: Fitness,
    navController: NavController,
    viewModel: HomeWorkoutViewModel
) {
    val isFav = viewModel.isFavorite(latihan.nama)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box {
                AsyncImage(
                    model = latihan.image_url,
                    contentDescription = latihan.nama,
                    placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                    error = painterResource(android.R.drawable.ic_menu_report_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = { viewModel.toggleFavorite(latihan.nama) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.4f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFav) Color.Red else Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = latihan.nama,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = latihan.deskripsi,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Durasi/Target: ${latihan.durasi}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Button(
                    onClick = {
                        navController.navigate("detail/${latihan.nama}")
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Mulai", color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(latihan: Fitness, exercises: List<Fitness> = emptyList(), navController: NavController) {
    if (exercises.isNotEmpty()) {
        RoutineRunnerScreen(routine = latihan, exercises = exercises, navController = navController)
    } else {
        SingleExerciseScreen(latihan = latihan, navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleExerciseScreen(latihan: Fitness, navController: NavController) {
    val durationText = latihan.durasi.lowercase()
    val isTimer = durationText.contains("detik") || durationText.contains("menit")

    val initialSeconds = remember(latihan.durasi) {
        if (durationText.contains("detik")) {
            durationText.replace("detik", "").trim().toIntOrNull() ?: 30
        } else if (durationText.contains("menit")) {
            (durationText.replace("menit", "").trim().toIntOrNull() ?: 15) * 60
        } else {
            0
        }
    }

    var timeLeft by remember { mutableStateOf(initialSeconds) }
    var isRunning by remember { mutableStateOf(false) }
    var repCount by remember { mutableIntStateOf(0) }
    var isCompleted by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(isRunning, timeLeft) {
        if (isRunning && timeLeft > 0) {
            delay(1000L)
            timeLeft--
        } else if (timeLeft == 0 && isRunning) {
            isRunning = false
            isCompleted = true
            snackbarHostState.showSnackbar("Selamat! Anda menyelesaikan Latihan ${latihan.nama}!")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(latihan.nama, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = latihan.image_url,
                contentDescription = latihan.nama,
                placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                error = painterResource(android.R.drawable.ic_menu_report_image),
                modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Deskripsi", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(latihan.deskripsi, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            if (isCompleted) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                ) {
                    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Latihan Selesai! 🎉", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Kerja bagus! Tubuh Anda akan berterima kasih.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { timeLeft = initialSeconds; repCount = 0; isCompleted = false }) { Text("Ulangi Latihan", color = Color.White) }
                    }
                }
            } else if (isTimer) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60), style = MaterialTheme.typography.displayLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(
                            onClick = { isRunning = !isRunning },
                            colors = ButtonDefaults.buttonColors(containerColor = if (isRunning) Color.Red else MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(imageVector = if (isRunning) Icons.Filled.Warning else Icons.Filled.PlayArrow, contentDescription = if (isRunning) "Jeda" else "Mulai")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (isRunning) "Jeda" else "Mulai", color = Color.White)
                        }
                        IconButton(
                            onClick = { isRunning = false; timeLeft = initialSeconds },
                            modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.surface, shape = CircleShape)
                        ) { Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Reset", tint = MaterialTheme.colorScheme.primary) }
                    }
                }
            } else {
                val targetReps = remember(latihan.durasi) { durationText.replace("x", "").trim().toIntOrNull() ?: 10 }
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(text = "$repCount / $targetReps Reps", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(onClick = { if (repCount < targetReps) repCount++ }, enabled = repCount < targetReps) { Text("+1 Rep", color = Color.White) }
                        Button(
                            onClick = { isCompleted = true; coroutineScope.launch { snackbarHostState.showSnackbar("Selamat! Anda menyelesaikan Latihan ${latihan.nama}!") } },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) { Text("Selesai", color = Color.White) }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineRunnerScreen(routine: Fitness, exercises: List<Fitness>, navController: NavController) {
    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    
    DisposableEffect(context) {
        val textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Initialized successfully
            }
        }
        tts = textToSpeech
        onDispose {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var isResting by remember { mutableStateOf(false) }
    var isCompleted by remember { mutableStateOf(false) }

    var exerciseTimeLeft by remember { mutableIntStateOf(0) }
    var isExerciseRunning by remember { mutableStateOf(false) }
    var repCount by remember { mutableIntStateOf(0) }

    var restTimeLeft by remember { mutableIntStateOf(30) }
    var isRestRunning by remember { mutableStateOf(false) }
    var showStartRoutine by remember { mutableStateOf(true) }

    val currentExercise = exercises.getOrNull(currentIndex)
    val nextExercise = if (currentIndex < exercises.size - 1) exercises[currentIndex + 1] else null

    LaunchedEffect(currentIndex, isResting, showStartRoutine) {
        if (!showStartRoutine && !isResting && currentExercise != null) {
            val durationText = currentExercise.durasi.lowercase()
            val initialSeconds = if (durationText.contains("detik")) {
                durationText.replace("detik", "").trim().toIntOrNull() ?: 30
            } else if (durationText.contains("menit")) {
                (durationText.replace("menit", "").trim().toIntOrNull() ?: 1) * 60
            } else {
                0
            }
            exerciseTimeLeft = initialSeconds
            isExerciseRunning = false
            repCount = 0
            tts?.speak("Mulai latihan: ${currentExercise.nama}. ${currentExercise.durasi}.", TextToSpeech.QUEUE_FLUSH, null, null)
        } else if (!showStartRoutine && isResting) {
            restTimeLeft = 30
            isRestRunning = true
            val nextText = if (nextExercise != null) "Latihan selanjutnya: ${nextExercise.nama}." else ""
            tts?.speak("Istirahat. $nextText", TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    LaunchedEffect(isExerciseRunning, exerciseTimeLeft) {
        if (isExerciseRunning && exerciseTimeLeft > 0) {
            delay(1000L)
            exerciseTimeLeft--
        } else if (exerciseTimeLeft == 0 && isExerciseRunning) {
            isExerciseRunning = false
            if (currentIndex < exercises.size - 1) {
                isResting = true
            } else {
                isCompleted = true
                tts?.speak("Latihan selesai! Kerja bagus.", TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    LaunchedEffect(isRestRunning, restTimeLeft) {
        if (isRestRunning && restTimeLeft > 0) {
            delay(1000L)
            restTimeLeft--
        } else if (restTimeLeft == 0 && isRestRunning) {
            isRestRunning = false
            isResting = false
            currentIndex++
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    val titleText = if (showStartRoutine) routine.nama else if (isCompleted) "Selesai" else if (isResting) "Istirahat" else "Latihan ${currentIndex + 1}/${exercises.size}"
                    Text(titleText, fontWeight = FontWeight.Bold) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showStartRoutine) {
                AsyncImage(
                    model = routine.image_url,
                    contentDescription = routine.nama,
                    modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Daftar Gerakan (${exercises.size})", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(exercises) { ex ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), elevation = CardDefaults.cardElevation(2.dp)) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text("• ${ex.nama}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(ex.durasi, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
                Button(onClick = { showStartRoutine = false }, modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                    Text("Mulai Latihan Pertama")
                }
            } else if (isCompleted) {
                Text("Latihan Selesai! 🎉", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Luar biasa! Rutinitas ${routine.nama} telah terselesaikan.", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Selesai")
                }
            } else if (isResting) {
                Text("ISTIRAHAT", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = String.format("%02d:%02d", restTimeLeft / 60, restTimeLeft % 60),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = { restTimeLeft += 20 }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                        Text("+20 Detik")
                    }
                    Button(
                        onClick = {
                            isRestRunning = false
                            isResting = false
                            currentIndex++
                        }
                    ) {
                        Text("Lewati")
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
                if (nextExercise != null) {
                    Text("Selanjutnya:", color = Color.Gray, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(nextExercise.nama, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                }
            } else if (currentExercise != null) {
                val durationText = currentExercise.durasi.lowercase()
                val isTimer = durationText.contains("detik") || durationText.contains("menit")
                val initialSeconds = if (durationText.contains("detik")) {
                    durationText.replace("detik", "").trim().toIntOrNull() ?: 30
                } else if (durationText.contains("menit")) {
                    (durationText.replace("menit", "").trim().toIntOrNull() ?: 1) * 60
                } else {
                    0
                }

                AsyncImage(
                    model = currentExercise.image_url,
                    contentDescription = currentExercise.nama,
                    modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(currentExercise.nama, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(currentExercise.deskripsi, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Spacer(modifier = Modifier.height(32.dp))
                
                if (isTimer) {
                    Text(
                        text = String.format("%02d:%02d", exerciseTimeLeft / 60, exerciseTimeLeft % 60),
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(
                            onClick = { isExerciseRunning = !isExerciseRunning },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isExerciseRunning) Color.Red else MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                imageVector = if (isExerciseRunning) Icons.Filled.Warning else Icons.Filled.PlayArrow,
                                contentDescription = if (isExerciseRunning) "Jeda" else "Mulai"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (isExerciseRunning) "Jeda" else "Mulai", color = Color.White)
                        }
                        
                        if (isExerciseRunning || exerciseTimeLeft < initialSeconds) {
                            Button(
                                onClick = { 
                                    isExerciseRunning = false
                                    if (currentIndex < exercises.size - 1) {
                                        isResting = true
                                    } else {
                                        isCompleted = true
                                        tts?.speak("Latihan selesai! Kerja bagus.", TextToSpeech.QUEUE_FLUSH, null, null)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                            ) {
                                Text("Selesai")
                            }
                        }
                    }
                } else {
                    val targetReps = durationText.replace("x", "").trim().toIntOrNull() ?: 10
                    Text(
                        text = "$repCount / $targetReps",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(onClick = { 
                            if (repCount < targetReps) {
                                repCount++ 
                                if (repCount == targetReps) {
                                    if (currentIndex < exercises.size - 1) {
                                        isResting = true
                                    } else {
                                        isCompleted = true
                                        tts?.speak("Latihan selesai! Kerja bagus.", TextToSpeech.QUEUE_FLUSH, null, null)
                                    }
                                }
                            }
                        }, enabled = repCount < targetReps) {
                            Text("+1 Rep")
                        }
                        Button(
                            onClick = { 
                                if (currentIndex < exercises.size - 1) {
                                    isResting = true
                                } else {
                                    isCompleted = true
                                    tts?.speak("Latihan selesai! Kerja bagus.", TextToSpeech.QUEUE_FLUSH, null, null)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Text("Selesai")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    var gistData by remember { mutableStateOf<GistResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    var retryCount by remember { mutableIntStateOf(0) }
    val repository = remember { FitnessRepository() }
    val workoutViewModel: HomeWorkoutViewModel = viewModel()

    val routines = remember(gistData) {
        gistData?.kategori?.flatMap { kat ->
            listOf("Pemula", "Menengah", "Lanjutan").map { level ->
                val durasi = when(level) {
                    "Pemula" -> "10 Menit"
                    "Menengah" -> "15 Menit"
                    else -> "20 Menit"
                }
                Fitness(
                    nama = "${kat.nama} $level",
                    deskripsi = "Program latihan ${kat.nama.lowercase()} khusus untuk tingkat $level.",
                    durasi = durasi,
                    image_url = kat.image_url
                )
            }
        } ?: emptyList()
    }

    LaunchedEffect(retryCount) {
        isLoading = true
        isError = false
        val response = repository.getLatihan()
        if (response != null) {
            gistData = response
            isError = false
        } else {
            isError = true
        }
        isLoading = false
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (isError) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "Error",
                        modifier = Modifier.size(64.dp),
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Gagal memuat data, periksa koneksi internet",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { retryCount++ }) {
                        Text("Coba Lagi", color = Color.White)
                    }
                }
            } else {
                MainScreen(
                    navController = navController,
                    viewModel = workoutViewModel,
                    kategori = gistData?.kategori ?: emptyList(),
                    listLatihan = routines
                )
            }
        }

        composable("detail/{nama}") { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama")
            val routine = routines.find { it.nama == nama }

            if (routine != null) {
                val categoryName = routine.nama.replace(" Pemula", "").replace(" Menengah", "").replace(" Lanjutan", "").trim()
                val exercises = gistData?.latihan?.filter { it.matchesCategory(categoryName) } ?: emptyList()
                DetailScreen(routine, exercises, navController)
            } else {
                val latihan = gistData?.latihan?.find { it.nama == nama }
                    ?: gistData?.kategori?.find { it.nama == nama }
                    ?: LatihanData.daftarLatihan.find { it.nama == nama }

                if (latihan != null) {
                    DetailScreen(latihan, emptyList(), navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    PrakTAM2_2417051046Theme {
        val navController = rememberNavController()
        AppNavigation(navController)
    }
}
