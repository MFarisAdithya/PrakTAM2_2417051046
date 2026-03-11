package com.example.praktam2_2417051046

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktam2_2417051046.ui.theme.PrakTAM2_2417051046Theme
import model.Fitness
import model.LatihanData
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM2_2417051046Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    HomeWorkoutApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeWorkoutApp(modifier: Modifier = Modifier) {

    val listLatihan = LatihanData.daftarLatihan

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "BurnIt 🔥",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(listLatihan) { latihan ->
                FitnessCard(latihan)
            }
        }
    }
}

@Composable
fun FitnessCard(latihan: Fitness) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = latihan.nama,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = latihan.deskripsi)

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = "Durasi: ${latihan.durasi}")

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { }) {
                    Text("Mulai")
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Image(
                painter = painterResource(id = latihan.imageRes),
                contentDescription = latihan.nama,
                modifier = Modifier.size(90.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    PrakTAM2_2417051046Theme {
        HomeWorkoutApp()
    }
}