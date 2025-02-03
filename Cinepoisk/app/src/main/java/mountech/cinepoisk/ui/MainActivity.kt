package mountech.cinepoisk.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import mountech.cinepoisk.navigation.NavGraph
import mountech.cinepoisk.ui.theme.CinepoiskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CinepoiskTheme {
                Scaffold {
                    NavGraph(navHostController = rememberNavController(), paddingValues = it)
                }
            }
        }
    }
}
