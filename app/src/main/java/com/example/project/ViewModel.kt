import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.project.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.project.mockMovie
import retrofit2.http.GET

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.http.Query
import kotlin.collections.emptyList


sealed class UiState {
    object Loading : UiState()
    data class Success(val movies: SearchResult) : UiState()
    data class Error(val message: String) : UiState()
}
class MoviesViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private val _movies = MutableStateFlow<SearchResult?>(null)
    val movies: StateFlow<SearchResult?> = _movies

    val repository = MovieRepository(RetrofitInstance.api)

    fun loadMovies() {
        viewModelScope.launch {
            try {
                val movieList = repository.fetchMovies()
                _movies.value = movieList
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Не удалось подключиться")
            }
        }
    }
}

interface MovieApiService {
    @GET("/")
    suspend fun getMovies(
        @Query("s") query: String,
        @Query("page") page: Int,
        @Query("apikey") apiKey: String): SearchResult
}

class MovieRepository(private val apiService: MovieApiService) {
    suspend fun fetchMovies(): SearchResult {
        return apiService.getMovies(query = "gun", page = 1, apiKey = "c455cdfe")
    }
}

data class SearchResult(
    val Search: List<MovieBrief>,
    val totalResults: String,
    val Response: String,
    val Error: String? = null
)

data class MovieBrief(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String
)