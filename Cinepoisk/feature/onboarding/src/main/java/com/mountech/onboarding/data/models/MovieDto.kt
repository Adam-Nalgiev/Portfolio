package com.mountech.onboarding.data.models

import com.mountech.onboarding.entity.Movie

internal data class MovieDto(

    override val url: String,
    override val description: String,

) : Movie
