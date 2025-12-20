import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.project.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.project.mockMovie
import retrofit2.http.GET

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.http.Query

class MoviesViewModel() : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    val repository = MovieRepository(RetrofitInstance.api)

    fun loadMovies() {
        viewModelScope.launch {
            try {
                val movieList = repository.fetchMovies()
                _movies.value = movieList
            } catch (e: Exception) {
                // обработка ошибок, например, заполнение других состояний
            }
        }
    }
}

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getMovies(@Query("api_key") apiKey: String): List<Movie>
}

class MovieRepository(private val apiService: MovieApiService) {
    suspend fun fetchMovies(): List<Movie> {
        return apiService.getMovies("c455cdfe")
    }
}