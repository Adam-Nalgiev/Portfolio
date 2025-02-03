package com.mountech.onboarding.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.mountech.screens.Screen

@Composable
fun OnboardingScreen(
    onNavigateTo: (Screen) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {


}

@Composable
private fun GradientOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent),
                    startY = 0f,
                    endY = 500f
                )
            )
    )
}

fun calculatePageOffset(state: PagerState, currentPage: Int): Float {
    return (state.currentPage + state.currentPageOffsetFraction - currentPage).coerceIn(-1f, 1f)
}

