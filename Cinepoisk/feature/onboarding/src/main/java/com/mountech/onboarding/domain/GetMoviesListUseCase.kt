package com.mountech.onboarding.domain

import com.mountech.onboarding.data.models.MovieDto
import com.mountech.onboarding.data.movies.MoviesList

internal class GetMoviesListUseCase {
    internal fun execute(): List<MovieDto> {
        return MoviesList.movies
    }
}