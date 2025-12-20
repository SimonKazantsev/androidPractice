import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Icon
import com.example.project.MovieDetailsScreen
import com.example.project.MoviesListScreen

sealed class Screen(val route: String) {
    object MoviesList : Screen("movies_list")
    object MovieDetails : Screen("movie_details/{imdbID}") {
        fun createRoute(imdbID: String) = "movie_details/$imdbID"
    }
}

sealed class BottomNavItem(
    val label: String,
    val icon: @Composable () -> Unit,
    val screen: Screen
) {
    object Movies : BottomNavItem(
        label = "Кино",
        icon = { Icon(Icons.Filled.Movie, contentDescription = "Профиль") },
        screen = Screen.MoviesList
    )
}

@Composable
fun MainScreen() {
    val vm: MoviesViewModel = viewModel()
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Movies) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf(BottomNavItem.Movies).forEach { item ->
                    NavigationBarItem(
                        icon = item.icon,
                        label = { Text(item.label) },
                        selected = selectedItem == item,
                        onClick = {
                            selectedItem = item
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.MoviesList.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.MoviesList.route) {
                val vm: MoviesViewModel = viewModel()
                LaunchedEffect(Unit) {
                    vm.loadMovies()
                }
                MoviesListScreen(
                    movies = vm.movies.collectAsState().value,
                    onMovieClick = { movieId ->
                        navController.navigate(Screen.MovieDetails.createRoute(movieId))
                    }
                )
            }

            composable(
                route = Screen.MovieDetails.route,
                arguments = listOf(navArgument("imdbID") { type = NavType.StringType  })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("imdbID") ?: return@composable
                val moviesList = vm.movies.collectAsState().value?.Search
                val movie = moviesList?.find { it.imdbID == movieId }
                if (movie != null) {
                    MovieDetailsScreen(movie = movie, onBack = { navController.popBackStack() })
                } else {
                    Text("Фильм не найден")
                }
            }
        }
    }
}
