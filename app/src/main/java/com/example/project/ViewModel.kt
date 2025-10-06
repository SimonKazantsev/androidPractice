import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.project.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.project.mockMovie

class MoviesViewModel : ViewModel() {
    private val _movies = MutableStateFlow(mockMovie)
    val movies: StateFlow<List<Movie>> = _movies
}