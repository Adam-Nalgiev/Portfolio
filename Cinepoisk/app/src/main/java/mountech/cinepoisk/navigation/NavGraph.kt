package mountech.cinepoisk.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mountech.onboarding.presentation.screen.OnboardingScreen
import com.mountech.screens.Screen

/* Annotation:
* В целом, изначально я хотел сделать несколько графов под каждый feature-модуль, как наиболее масштабируемое решение.
* Но поскольку мой каждый feature-модуль представлял отдельный экран и проект не имеет перспектив расширения,
* то пришлось сделать выбор в пользу удобства управления навигацией в одном графе и отказаться от иерархии абстракций
* для множества графов навигации. Возможно по итогу я смогу довести проект до достаточных масштабов.
*/

@Composable
internal fun NavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Onboarding,
        modifier = modifier
    ) {
        composable<Screen.Movie> {

        }
        composable<Screen.Collections> {

        }
        composable<Screen.Favorites> {

        }
        composable<Screen.Onboarding> {
            OnboardingScreen({ navigateTo -> navHostController.navigate(navigateTo) }, paddingValues)
        }
    }
}

