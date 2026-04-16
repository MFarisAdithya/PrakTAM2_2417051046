package com.example.praktam2_2417051046
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktam2_2417051046.ui.theme.PrakTAM2_2417051046Theme
import model.Fitness
import model.LatihanData
import androidx.compose.foundation.background
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PrakTAM2_2417051046Theme {

                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavigation(navController)
                }

            }
        }
    }
}

@Composable
fun HomeWorkoutApp(navController: NavController) {

    val kategori = LatihanData.kategoriLatihan
    val listLatihan = LatihanData.daftarLatihan

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                text = "BurnIt 🔥",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }


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
                items(kategori) { latihan ->
                    CategoryItem(latihan)
                }
            }
        }


        item {
            Text(
                text = "Daftar Latihan",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(listLatihan) { latihan ->
            FitnessCard(latihan, navController)
        }
    }
}

@Composable
fun FitnessCard(latihan: Fitness, navController: NavController) {

    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Box {

                Image(
                    painter = painterResource(id = latihan.imageRes),
                    contentDescription = latihan.nama,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )

                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (isFavorite)
                            Icons.Filled.Favorite
                        else
                            Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = latihan.nama,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(text = latihan.deskripsi)
            Text(text = "Durasi: ${latihan.durasi}")

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    navController.navigate("detail/${latihan.nama}")
                },
            ) {
                Text("Mulai", color = Color.White)
            }
        }
    }
}

@Composable
fun CategoryItem(latihan: Fitness) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = latihan.imageRes),
                contentDescription = latihan.nama,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = latihan.nama,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun DetailScreen(latihan: Fitness) {

    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Image(
                painter = painterResource(id = latihan.imageRes),
                contentDescription = latihan.nama,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(
                text = latihan.nama,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(text = latihan.deskripsi)
            Text(text = "Durasi: ${latihan.durasi}")

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        delay(2000)

                        snackbarHostState.showSnackbar(
                            "Latihan ${latihan.nama} dimulai!"
                        )

                        isLoading = false
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Memproses...")
                } else {
                    Text("Mulai Latihan")
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeWorkoutApp(navController)
        }

        composable("detail/{nama}") { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama")

            val latihan = LatihanData.daftarLatihan.find {
                it.nama == nama
            }

            if (latihan != null) {
                DetailScreen(latihan)
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