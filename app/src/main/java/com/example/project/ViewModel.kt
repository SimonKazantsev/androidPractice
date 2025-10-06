import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.project.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.ui.res.stringResource
import com.example.project.R

class MoviesViewModel : ViewModel() {
    private val _movies = MutableStateFlow(
        listOf(
            Movie(1, "Побег из Шоушенка", description = "Описание фильма Побег из Шоушенка", year = 1901),
            Movie(2, "Крестный отец", "Описание фильма Крестный отец", year = 1901),
            Movie(3, "Темный рыцарь", "Описание фильма Темный рыцарь", year = 1901),
            Movie(4, "Форрест Гамп", "Описание фильма Форрест Гамп", year = 1901),
            Movie(5, "Начало", "Описание фильма Начало", year = 1901),
            Movie(6, "Интерстеллар", "Описание фильма Интерстеллар", year = 1901),
            Movie(7, "Паразиты", "Описание фильма Паразиты", year = 1901),
            Movie(8, "Джокер", "Описание фильма Джокер", year = 1901)
        )
    )
    val movies: StateFlow<List<Movie>> = _movies
}