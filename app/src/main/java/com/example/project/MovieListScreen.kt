package com.example.project
import SearchResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun MoviesListScreen(
    movies: SearchResult?,
    onMovieClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        movies?.Search?.forEach { movie ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onMovieClick(movie.imdbID) },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        AsyncImage(
                            model = movie.Poster,
                            contentDescription = movie.Title,
                            modifier = Modifier.size(100.dp)
                        )
                        Column(
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(text = movie.Title, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Год выпуска: ${movie.Year}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}